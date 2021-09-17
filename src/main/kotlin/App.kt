import component.loadingComponent
import data.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.h1
import util.ServiceWorkerState
import util.UsePushManager
import util.usePushManager
import util.useServiceWorker


lateinit var serviceWorkerState: ServiceWorkerState
lateinit var pushManager: UsePushManager

val App = functionComponent<Props> {

    val scope = MainScope()

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

                    console.log(">>>>>>>>> STATE --- INIT")





            scope.launch {
                val res =  Repository.fetchArticle(4)
                previews =  Repository.fetchArticles()
                console.log(">>> DETAIL-4 ::::: $res")


                console.log(">>> PREVIEWS ::::: $previews")
            }


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