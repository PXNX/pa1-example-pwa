package util

import kotlinx.coroutines.MainScope
import org.khronos.webgl.ArrayBuffer
import org.w3c.workers.ServiceWorkerRegistration
import kotlin.js.Promise

internal val scope = MainScope()

/**
 * Stores the service worker state
 */
sealed class ServiceWorkerState {
    /**
     * Indicates that the JavaScript service worker [register](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerContainer/register) method has resolved with a [ServiceWorkerRegistration] object
     */
    data class Registered(val swRegistration: ServiceWorkerRegistration): ServiceWorkerState()
    /**
     * Indicates that the JavaScript service worker [register](https://developer.mozilla.org/en-US/docs/Web/API/ServiceWorkerContainer/register) method has failed unexpectedly
     */
    data class Failed(val exception: Exception): ServiceWorkerState()
    /**
     * The initial state of the service worker
     */
    object Loading: ServiceWorkerState()
}

/**
 * Stores the [PushManager] state
 */
sealed class PushManagerState {
    /**
     * Indicates that the user is subscribed to the push service
     */
    data class Subscribed(val pushManager: PushManager): PushManagerState()
    /**
     * Indicates that the [PushManager] exists and is loaded, but the user is not subscribed to the push service
     */
    data class NotSubscribed(val pushManager: PushManager): PushManagerState()
    /**
     * Indicates that the [PushManager] has not been loaded yet
     */
    object NotLoaded: PushManagerState()
    /**
     * Indicates that the [PushManager] is currently retrieving an existing push subscription
     */
    object Loading: PushManagerState()
    /**
     * Indicates that the service worker does not expose the [PushManager] interface
     */
    object NotSupported: PushManagerState()
}

/**
 * Exposes the [options](https://developer.mozilla.org/en-US/docs/Web/API/PushManager/subscribe#parameters) parameter from JavaScript [PushManager.subscribe()](https://developer.mozilla.org/en-US/docs/Web/API/PushSubscription) method
 */
data class PushSubscriptionOptions(val userVisibleOnly: Boolean, val applicationServerKey: Any)

/**
 * Exposes the JavaScript [PushSubscription](https://developer.mozilla.org/en-US/docs/Web/API/PushSubscription)
 */
external class PushSubscription {
    val endpoint: String
    val expirationTime: dynamic
    val options: PushSubscriptionOptions

    fun getKey(name: String): ArrayBuffer
    fun toJSON(): JSON
    fun unsubscribe(): Promise<Boolean>
}

internal inline val ServiceWorkerRegistration.pushManager: PushManager?
    get() = asDynamic().pushManager.unsafeCast<PushManager?>()

/**
 * Exposes the JavaScript [PushManager](https://developer.mozilla.org/en-US/docs/Web/API/PushManager)
 */
external interface PushManager {
    fun getSubscription(): Promise<PushSubscription?>
    fun permissionState(): Promise<String>
    fun subscribe(options: PushSubscriptionOptions): Promise<PushSubscription>
}