import component.iconButton
import component.loadingComponent
import data.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import model.ArticlePreview
import react.*
import react.dom.*
import styled.*
import util.*

external interface FeedProps : Props {
    // var pushManagerState: PushManagerState //Unit??
    var previews: List<ArticlePreview>
    var pushAction: Unit
}

external interface FeedState : State {
    // var pushManagerState: PushManagerState //Unit??
    var previews: Status<*>
    var pushAction: Unit
}

class Feed : RComponent<FeedProps, FeedState>() {



   override fun FeedState.init(){
        val scope= MainScope()
        scope.launch {
           val articlePreviews= Repository.fetchArticlePreviews()
            setState {
                previews = articlePreviews
            }
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                justifyContent = JustifyContent.center
                marginTop = 80.px
                padding = "8px"
            }

            styledDiv {
                css {
                    width = if (isLandscape) LinearDimension("50%") else LinearDimension.fillAvailable
                }

    when (val result = state.previews) {
        is Status.Loading -> loadingComponent()

        is Status.Success<*> -> showFeed(result.data as List<ArticlePreview>)

        is Status.Failure -> {
            h1 {
                +"Feed could not be loaded."
            }
            p {
                +result.exception.toString()
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


    private fun RBuilder.showFeed(articlePreviews: List<ArticlePreview>) = if (articlePreviews.isNotEmpty()) {


        styledDiv {
            css {
                backgroundColor = Color("#016b6b")
                width = LinearDimension.fillAvailable
                borderRadius = LinearDimension("16px")
                padding = "1.5rem"
                marginBottom = LinearDimension("1rem")
            }




            styledP {
                css {
                    fontWeight = FontWeight.bold
                }

                +"Auf dem Laufenden bleiben"
            }

            +"Nachrichten rund um Milit??r- und Protest-Aktionen weltweit und brandaktuell ????"






            styledButton {
                +"Benachrichtungen aktivieren"



                attrs {

/*
onClick={
   state.pushManagerState = PushManagerState.Subscribed()
}

 */
                }



                css {

                }

            }
        }


        articlePreviews.forEachIndexed { index, article ->

     //       console.log("index: $index")

            if (index+3 % 15 == 0) {

            }


            /*    styledP {
                                  +article.tags

                                  css {
                                      color = Color("#8797ff")
                                  }
                              }

                         */
            styledDiv {
                css {
                    backgroundColor = Color("#12273d")
                    width = LinearDimension.fillAvailable
                    borderRadius = LinearDimension("10px")
                    marginBottom = LinearDimension("1rem")
                }



                styledImg(src = article.imageUrl) {
                    attrs {
                        width = "100%"
                        height = "150px"
                    }

                    css {
                        borderTopRightRadius = LinearDimension("10px")
                        borderTopLeftRadius = LinearDimension("10px")
                        objectFit = ObjectFit.cover
                    }
                }



                styledDiv {
                    css {
                        padding = "0.75rem"
                    }

                    styledP {
                        +article.title

                        css {
                            fontWeight = FontWeight.bold
                        }
                    }


                    /*    styledP {
                              +article.tags

                              css {
                                  color = Color("#8797ff")
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


            +" Nachrichten rund um Milit??r- und Protest-Aktionen weltweit und brandaktuell ????"





            styledButton {
                +"Auf dem Laufenden bleiben (push notification)"

                attrs {

                }
            }

        }
    } else {
        styledP {
            +"Keine Meldungen verf??gbar."

            css {
                fontWeight = FontWeight.bold
                color = Color("#fff")
            }
        }

    }
}

fun RBuilder.feed(handler: FeedProps.() -> Unit) = child(Feed::class) { this.attrs(handler) }