import component.loadingComponent
import data.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import model.ArticlePreview
import org.w3c.workers.ServiceWorkerRegistration
import react.*
import react.dom.attrs
import react.dom.button
import react.dom.h1
import react.dom.h2
import util.*

external interface AppState : State {
    var serviceWorkerState: ServiceWorkerState
    var pushManager: UsePushManager
    var previews: List<ArticlePreview>
}

class App : RComponent<Props, AppState>() {
    override fun AppState.init() {


       serviceWorkerState = ServiceWorkerState.Loading

   /* serviceWorkerState = useServiceWorker()
    pushManager = usePushManager(
        serviceWorkerState = serviceWorkerState,
        publicKey = "BLceSSynHW5gDWDz-SK5mmQgUSAOzs_yXMPtDO0AmNsRjUllTZsdmDU4_gKvTr_q1hA8ZX19xLbGe28Bkyvwm3E"
    )

    */

        setState {
            serviceWorkerState = ServiceWorkerState.Loading//useServiceWorker()
            console.log("STATE::: $serviceWorkerState")
        }

        previews = listOf()

        val mainScope = MainScope()
        mainScope.launch {
            val listtt = Repository.fetchArticles()

            console.log(">>> articlePreviews ::: listtt $listtt")

            setState {
                previews = listtt
            }

            /*  val articlePreviews = Repository.fetchArticle(4)



              console.log(">>> articlePreviews ::: $articlePreviews")

             */
            /*   setState {
                   previews = articlePreviews
               }

             */
        }
    }

    override fun RBuilder.render() {



        when (state.serviceWorkerState) {
            is ServiceWorkerState.Registered -> {
                child(Header::class) {
                    attrs {
                        name = "React!"
                    }
                }

                feed {
                    previews = state.previews

                    /*
        pushAction =     when (state.pushManager.pushManagerState) {
        is PushManagerState.NotSubscribed -> {
        button {
            attrs {
                onClickFunction = {
                    util.scope.launch {
                        state.pushManager.subscribeUser(state.pushManager.pushManagerState as PushManagerState.NotSubscribed) {
                            console.log("Sending subscription to server...")
                        }
                    }
                }
            }
            +"Click here to subscribe to push notifications"
        }
        }
        is PushManagerState.Subscribed -> {
        h2 {
            +"User is subscribed to Push API"
        }
        button {
            attrs {
                onClickFunction = {
                    util.scope.launch {
                        state.pushManager.unsubscribeUser(state.pushManager.pushManagerState as PushManagerState.Subscribed)
                    }
                }
            }
            +"Click here to unsubscribe"
        }
        }
        PushManagerState.NotSupported -> h2 {
        +"Push API is not supported on this browser"
        }
        PushManagerState.Loading, PushManagerState.NotLoaded -> loadingComponent()
        }

                     */
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
                +"Error in registering service worker: ${(state.serviceWorkerState as ServiceWorkerState.Failed).exception.message}"
            }
            ServiceWorkerState.Loading -> loadingComponent()
        }


    }


}