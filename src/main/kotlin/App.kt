import com.bnorm.react.RFunction
import component.loadingComponent
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import react.Props
import react.RBuilder
import react.RHandler
import react.dom.attrs
import react.dom.button
import react.dom.h1
import react.dom.h2
import react.fc
import util.PushManagerState
import util.ServiceWorkerState
import util.usePushManager
import util.useServiceWorker

@RFunction
fun RBuilder.App(appProps: Props){

    val serviceWorkerState = useServiceWorker()

    val usePushManager  = usePushManager(
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

            //  val match = useRouteMatch()

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