import component.loadingComponent
import react.Props
import react.RBuilder
import react.RHandler
import react.dom.h1
import react.fc
import react.router.dom.BrowserRouter
import react.router.dom.Route
import react.router.dom.Switch
import react.router.dom.useRouteMatch
import util.ServiceWorkerState
import util.usePushManager
import util.useServiceWorker

val App = fc<Props> {

    val serviceWorkerState = useServiceWorker()
    val pushManager = usePushManager(
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

            val match = useRouteMatch()

            BrowserRouter {
                Switch {
                    Route {
                        attrs {
                            path = arrayOf("/post/:id")
                            component = feed {

                            }
                        }
                    }

                }
            }


            /*   child(Feed::class) {
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


             */


        }
        is ServiceWorkerState.Failed -> h1 {
            +"Error in registering service worker: ${serviceWorkerState.exception.message}"
        }
        ServiceWorkerState.Loading -> loadingComponent()
    }

}

fun RBuilder.app(props: Props, handler: RHandler<Props>) = child(App, props, handler)