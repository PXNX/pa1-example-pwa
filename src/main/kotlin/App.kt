import component.loadingComponent
import data.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.ArticleDetail
import model.ArticlePreview
import react.*
import react.dom.h1
import util.ServiceWorkerState
import util.UsePushManager
import util.usePushManager
import util.useServiceWorker

external interface AppState : State {
  /*  var serviceWorkerState: ServiceWorkerState
   var pushManager: UsePushManager

   */
   var previews: List<ArticleDetail>
}

class App : RComponent<Props, AppState>() {
    override fun AppState.init() {
/*     serviceWorkerState = useServiceWorker()
     pushManager = usePushManager(
         serviceWorkerState = serviceWorkerState,
         publicKey = "BLceSSynHW5gDWDz-SK5mmQgUSAOzs_yXMPtDO0AmNsRjUllTZsdmDU4_gKvTr_q1hA8ZX19xLbGe28Bkyvwm3E"
     )

 */
     previews = listOf()

     val mainScope = MainScope()
     mainScope.launch {
      val listtt =    Repository.fetchArticles()

         console.log(">>> articlePreviews ::: listtt $listtt")

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
 /*    when (state.serviceWorkerState) {
         is ServiceWorkerState.Registered -> {
             child(Header::class) {
                 attrs {
                     name = "React!"
                 }
             }

  */


             feed {
                 previews = emptyList()
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


   /*      }
         is ServiceWorkerState.Failed -> h1 {
             +"Error in registering service worker: ${(state.serviceWorkerState as ServiceWorkerState.Failed).exception.message}"
         }
         ServiceWorkerState.Loading -> loadingComponent()
     }*/
 }


}