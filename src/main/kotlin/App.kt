import component.loadingComponent
import react.Props
import react.RBuilder
import react.RHandler
import react.dom.h1
import react.functionComponent
import util.ServiceWorkerState
import util.UsePushManager
import util.usePushManager
import util.useServiceWorker


lateinit var serviceWorkerState: ServiceWorkerState
lateinit var pushManager: UsePushManager

val App = functionComponent<Props> {

    try {
        serviceWorkerState = useServiceWorker()
    } catch (e: Exception) {
        console.log("--- EXX ::: $e")
    }
    pushManager = usePushManager(
        serviceWorkerState = serviceWorkerState,
        publicKey = "BLceSSynHW5gDWDz-SK5mmQgUSAOzs_yXMPtDO0AmNsRjUllTZsdmDU4_gKvTr_q1hA8ZX19xLbGe28Bkyvwm3E"
    )


    when (serviceWorkerState) {
        is ServiceWorkerState.Registered -> {
            child(Header::class) {
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