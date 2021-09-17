import component.iconButton
import data.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import model.ArticleDetail
import model.ArticlePreview
import react.*
import react.dom.attrs
import styled.*
import util.PushManagerState
import util.isLandscape
import util.isPortrait


external interface FeedProps : Props {
 //   var pushManagerState: PushManagerState //Unit??
    var previews:List<ArticleDetail>
}

class Feed : RComponent<FeedProps, State>() {

    override fun RBuilder.render() {

       // console.log(">>>>>>>>>FEED :: RENDER")

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

              if (props.previews.isNotEmpty()) {

                  props.previews.forEachIndexed { index, article ->

                        /*
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
                         */

                        styledDiv {
                            css {
                                backgroundColor = Color("#12273d")
                                width = LinearDimension.fillAvailable
                                borderRadius = LinearDimension("16px")
                                marginBottom = LinearDimension("1rem")
                            }

                          /*  styledImg(src = article.imageUrl) {
                                attrs {
                                    width = "100%"
                                    height = "120px"
                                }

                                css {
                                    borderTopRightRadius = LinearDimension("16px")
                                    borderTopLeftRadius = LinearDimension("16px")
                                }
                            }

                           */

                            styledDiv {
                                css {
                                    padding = "0.75rem"
                                }

                                styledP {
                                    +article.title

                                    css {
                                        fontWeight = FontWeight.bold
                                        color = Color("#fff")
                                    }
                                }

                                /*  styledP {
                                      +article.tags

                                      css {
                                          color = Color("#fff")
                                      }
                                  }

                                 */
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
              } else {
                    styledP {
                        +"Keine Meldungen verfügbar."

                        css {
                            fontWeight = FontWeight.bold
                            color = Color("#fff")
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

fun RBuilder.feed(handler: FeedProps.() -> Unit) = child(Feed::class) { this.attrs(handler) }