import component.loadingComponent
import react.*
import react.dom.h1
import util.ServiceWorkerState
import util.usePushManager
import util.useServiceWorker

val App = fc<Props> {

    val serviceWorkerState = useServiceWorker()

    /*         val usePm = usePushManager(
                 serviceWorkerState = serviceWorkerState,
                 publicKey = "BLceSSynHW5gDWDz-SK5mmQgUSAOzs_yXMPtDO0AmNsRjUllTZsdmDU4_gKvTr_q1hA8ZX19xLbGe28Bkyvwm3E"
             )

     */


        when (serviceWorkerState) {
            is ServiceWorkerState.Registered -> {
              // Header()

                //  val match = useRouteMatch()

               child(Feed::class) {}

                feed { }

                /*   feed {



            }

          */



                //    NotificationBanner(usePushManager)

                /*    BrowserRouter {
                    Switch {
                        Route {
                            attrs {
                                path = arrayOf("/post/:id")
                                component = Feed
                            }
                        }

                    }
                }

             */


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