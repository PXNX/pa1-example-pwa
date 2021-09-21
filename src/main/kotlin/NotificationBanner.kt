import com.bnorm.react.RFunction
import component.loadingComponent
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import react.Props
import react.RBuilder
import react.RHandler
import react.dom.attrs
import react.dom.button
import react.dom.h2
import react.fc
import util.PushManagerState
import util.ServiceWorkerState
import util.UsePushManager
import util.usePushManager

@RFunction
fun RBuilder.NotificationBanner(pushManager: UsePushManager){



    when (pushManager.pushManagerState) {
        is PushManagerState.NotSubscribed -> {
            button {
                attrs {
                    onClickFunction = {
                        scope.launch {
                            pushManager.subscribeUser(pushManager.pushManagerState) {
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
                        scope.launch {
                            pushManager.unsubscribeUser(pushManager.pushManagerState)
                        }
                    }
                }
                +"Click here to unsubscribe"
            }
        }
        PushManagerState.NotSupported ->   h2 {
            +"Push API is not supported on this browser"
        }
        PushManagerState.Loading, PushManagerState.NotLoaded -> loadingComponent()
    }
}