import kotlinx.coroutines.MainScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import org.w3c.fetch.Response
import org.w3c.notifications.NotificationEvent
import org.w3c.notifications.NotificationOptions
import org.w3c.workers.FetchEvent
import org.w3c.workers.InstallEvent
import org.w3c.workers.ServiceWorkerGlobalScope

external val self: ServiceWorkerGlobalScope
val scope = MainScope()

const val MAIN_CACHE = "mainCache"


fun installServiceWorker() {
    val offlineContent = arrayOf(
        "/index.html",
        //   "/pa1-example-pwa.js",
        "/logo/logo-192.png",
        "/logo/logo-512.png"
    )

    self.addEventListener("install", { event ->
        event as InstallEvent
        console.log("I am installed.")
        scope.async {
            val cache = self.caches.open(MAIN_CACHE).await()
            cache.addAll(offlineContent).await()
            console.log("Offline cache loaded.")
        }.let {
            event.waitUntil(it.asPromise())
        }
    })

    // using the "Network falling back to cache" strategy (https://developers.google.com/web/ilt/pwa/caching-files-with-service-worker#network_falling_back_to_the_cache)
    self.addEventListener("fetch", { event ->
        event as FetchEvent
        if (event.request.url.contains("http").not()) return@addEventListener

        scope.async {
            val cache = self.caches.open(MAIN_CACHE).await()
            try {
                val response = self.fetch(event.request).await()
                cache.put(event.request, response.clone()).await()
                return@async response
            } catch (e: Throwable) {
                return@async self.caches.match(event.request).await().unsafeCast<Response>()
            }
        }.let {
            event.respondWith(it.asPromise())
        }
    });

    self.addEventListener("push", { event ->
        event as PushEvent
        console.log("Push received.")
        val payloadString = event.data.text()

        event.waitUntil(
            self.registration.showNotification(
                title = "Notification",
                options = NotificationOptions(
                    tag = "tag",
                    body = payloadString,
                    icon = "/logo/logo-192.png",
                    badge = "/logo/logo-192.png"
                )
            )
        )
    })

    self.addEventListener("notificationclick", { event ->
        event as NotificationEvent
        console.log("Notification click received.")
        event.notification.close()
        event.waitUntil(
            self.clients.openWindow("localhost:8080")
        )
    })
}
