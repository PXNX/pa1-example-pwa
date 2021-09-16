import component.iconButton
import component.loadingComponent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.attrs
import react.dom.button
import react.dom.h2
import styled.*
import util.PushManagerState
import util.isLandscape
import util.isPortrait


external interface FeedProps : Props {
    var pushManagerState: PushManagerState
}

class Feed(var feedProps: FeedProps) : RComponent<FeedProps, State>(feedProps) {

    init{
        state.init(feedProps)
    }

    private val scope = MainScope()

    override fun RBuilder.render() {

        val paths = emptyList<String>().toMutableList()

        for (i in 0..30) {
            paths.addAll(listOf("wallpaper1.webp", "wallpaper2.jpg", "wallpaper3.png"))
        }

        styledDiv {
            css {
                display = Display.flex
                justifyContent = JustifyContent.center
                marginTop = 80.px
            }

            styledDiv {
                css {
                    width = if (isLandscape) LinearDimension("50%") else LinearDimension.fillAvailable
                }

                paths.forEachIndexed { index, path ->

                    if (index % 12 == 0) {
                        styledDiv {
                            css {
                                backgroundColor = Color("#016b6b")
                                width = LinearDimension.fillAvailable
                                borderRadius = LinearDimension("16px")
                                padding = "1.5rem"
                                marginBottom = LinearDimension("1rem")
                            }


                            +" Nachrichten rund um Militär- und Protest-Aktionen weltweit und brandaktuell 🔰"



                            when (feedProps.pushManagerState) {
                                is PushManagerState.NotSubscribed -> {
                                    button {
                                        attrs {
                                            onClickFunction = {
                                                scope.launch {
                                                    pushManager.subscribeUser(feedProps.pushManagerState as PushManagerState.NotSubscribed) {
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
                                                    pushManager.unsubscribeUser(feedProps.pushManagerState as PushManagerState.Subscribed)
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



                            styledButton {
                                +"Auf dem Laufenden bleiben (push notification)"

                                attrs {

                                }
                            }
                        }
                    }


                    styledDiv {
                        css {
                            backgroundColor = Color("#12273d")
                            width = LinearDimension.fillAvailable
                            borderRadius = LinearDimension("16px")
                            marginBottom = LinearDimension("1rem")
                        }

                        styledImg(src = path) {
                            attrs {
                                width = "100%"
                                height = "120px"
                            }

                            css {
                                borderTopRightRadius = LinearDimension("16px")
                                borderTopLeftRadius = LinearDimension("16px")
                                //  overflow = Overflow.hidden
                            }
                        }

                        styledDiv {
                            css {
                                padding = "0.75rem"
                            }

                            styledP {
                                +"Titel des Artikels"

                                css {
                                    fontWeight = FontWeight.bold
                                    fontFamily = "sans-serif"
                                    color = Color("#fff")
                                }
                            }

                            styledP {
                                +"Hier passierte etwas. Lesen Sie hier weiter und hier kommt noch Text..."

                                css {
                                    color = Color("#fff")
                                }
                            }
                        }
                    }
                }

                styledDiv {
                    css {
                        backgroundColor = Color("#016b6b")
                        width = LinearDimension.fillAvailable
                        borderRadius = LinearDimension("16px")
                        marginBottom = LinearDimension("12rem")
                        padding = "1.5rem"
                    }


                    +" Nachrichten rund um Militär- und Protest-Aktionen weltweit und brandaktuell 🔰"





                    styledButton {
                        +"Auf dem Laufenden bleiben (push notification)"

                        attrs {

                        }
                    }

                }
            }
        }

        if (isPortrait) {
            styledDiv {
                css {


                    backgroundColor = Color("#0c2b49")

                    float = Float.right
                    display = Display.block
                    bottom = 0.px
                    left = 0.px
                    right = 0.px
                    position = Position.fixed
                    //margin(right=50.px)
                    //  alignItems=Align.center
                }

                iconButton("https://t.me/militaernews", "telegram")

                iconButton("https://twitter.com/MilitaerNews?s=09", "twitter")

                //   iconButton("mailto:militaernews@protonmail.com", "icon-mail")
            }
        }
    }
}