package util

import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import react.useEffect
import react.useState

/**
 * Used to store the outputs of the [usePushManager] hook
 *
 * @property[subscribeUser] Wrapper for the [PushManager.subscribe()](https://developer.mozilla.org/en-US/docs/Web/API/PushSubscription) method; A lambda can be passed to perform an action upon successful subscription (send subscription data to server)
 * @property[unsubscribeUser] Wrapper for the [PushManager.unsubscribe()](https://developer.mozilla.org/en-US/docs/Web/API/PushSubscription/unsubscribe) method
 */
data class UsePushManager(
    val pushManagerState: PushManagerState,
    val subscribeUser : suspend (
        notSubscribedState: PushManagerState.NotSubscribed,
        handleSubscription: (suspend (subscription: PushSubscription) -> Unit)?
    ) -> Unit,
    val unsubscribeUser: suspend (subscribedState: PushManagerState.Subscribed) -> Unit
)

/**
 * A hook that handles push subscriptions via [PushManager]
 *
 * @param[serviceWorkerState] Service worker state from the [useServiceWorker] hook
 * @param[publicKey] [Application server key](https://developer.mozilla.org/en-US/docs/Web/API/PushManager/subscribe#parameters) used to authenticate your application server
 *
 */
fun usePushManager(serviceWorkerState: ServiceWorkerState, publicKey: String): UsePushManager {

    val (pushManagerState, setPushManagerState) = useState<PushManagerState>(PushManagerState.NotLoaded)

    suspend fun loadPushManagerState(pushManager: PushManager?) {
        if (pushManager != null) {
            setPushManagerState(PushManagerState.Loading)
            pushManager.getSubscription().await().let {
                setPushManagerState(
                    if (it != null) {
                        PushManagerState.Subscribed(pushManager = pushManager)
                    } else {
                        PushManagerState.NotSubscribed(pushManager = pushManager)
                    }
                )
            }
        } else {
            setPushManagerState(PushManagerState.NotSupported)
        }
    }

    useEffect {
        scope.launch {
            if (serviceWorkerState is ServiceWorkerState.Registered && pushManagerState is PushManagerState.NotLoaded) {
                loadPushManagerState(serviceWorkerState.swRegistration.pushManager)
            }
        }
    }

    useEffect( serviceWorkerState) {
        scope.launch {
            if (serviceWorkerState is ServiceWorkerState.Registered && pushManagerState is PushManagerState.NotLoaded) {
                loadPushManagerState(serviceWorkerState.swRegistration.pushManager)
            }
        }
    }

    suspend fun subscribeUser(
        pushManager: PushManager,
        handleSubscription: (suspend (subscription: PushSubscription) -> Unit)?
    ) {
        val subscription = try {
            pushManager.subscribe(
                PushSubscriptionOptions(userVisibleOnly = true, applicationServerKey = publicKey)
            ).await()
        } catch (e: Exception) {
            console.warn("Subscription denied - ${e.message}")
            return
        }

        handleSubscription?.let { it(subscription) }
        setPushManagerState(PushManagerState.Subscribed(pushManager))

    }

    suspend fun unsubscribeUser(pushManager: PushManager) {
        val subscription = pushManager.getSubscription().await()
        if (subscription != null) {
            try {
                subscription.unsubscribe().await()
                setPushManagerState(PushManagerState.NotSubscribed(pushManager))
            } catch (e: Exception) {
                console.error("User unsubscription failed: ${e.message}")
            }
        }
    }

    return UsePushManager(
        pushManagerState = pushManagerState,
        subscribeUser = { notSubscribedState, handleSubscription ->
            subscribeUser(pushManager = notSubscribedState.pushManager, handleSubscription = handleSubscription)
        },
        unsubscribeUser = { subscribedState ->
            unsubscribeUser(pushManager = subscribedState.pushManager)
        }
    )
}