package client

import Title
import client.component.loadingComponent
import kotlinx.coroutines.MainScope
import react.Props
import react.RBuilder
import react.RHandler
import react.dom.h1
import react.functionComponent
import client.util.ServiceWorkerState
import client.util.UsePushManager
import client.util.usePushManager
import client.util.useServiceWorker

val scope = MainScope()
lateinit var serviceWorkerState: ServiceWorkerState
lateinit var pushManager: UsePushManager

val App = functionComponent<Props> {

    serviceWorkerState = useServiceWorker()
    pushManager = usePushManager(
        serviceWorkerState = serviceWorkerState,
        publicKey = "BLceSSynHW5gDWDz-SK5mmQgUSAOzs_yXMPtDO0AmNsRjUllTZsdmDU4_gKvTr_q1hA8ZX19xLbGe28Bkyvwm3E"
    )


    when (serviceWorkerState) {
        is ServiceWorkerState.Registered -> {
            h1 {
                +"Successfully registered a service worker!"
            }

            child(Title::class) {
                attrs {
                    name = "React!"
                }
            }

            child(Feed::class) {
                attrs {
                    pushManagerState = pushManager.pushManagerState
                }
            }




        }
        is ServiceWorkerState.Failed -> h1 {
            +"Error in registering service worker: ${(serviceWorkerState as ServiceWorkerState.Failed).exception.message}"
        }
        ServiceWorkerState.Loading -> loadingComponent()
    }
}

fun RBuilder.App(props: Props, handler: RHandler<Props>) = child(App, props, handler)