import component.iconButton
import kotlinx.css.*
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.attrs
import styled.css
import styled.styledDiv
import styled.styledHeader
import styled.styledImg
import util.isLandscape

external interface TitleProps : Props {
    var name: String
}

data class TitleState(val name: String) : State

@OptIn(ExperimentalJsExport::class)
@JsExport
class Title(var titleProps: TitleProps) : RComponent<TitleProps, TitleState>(titleProps) {

    override fun RBuilder.render() {
        styledHeader {
            css {
                backgroundColor = Color("#0c2b49")
                position = Position.fixed
                display = Display.block
                width = LinearDimension("100%")
                paddingLeft = 10.px
                paddingRight = 10.px
                top=0.px
                left=0.px
                right=0.px
            }

            styledImg(src = "logo/logo-192.png", alt = "Militaernews") {
                attrs {
                    width = "45"
                    height = "45"
                }

                css {
                    float = Float.left
                    padding = "0.5rem"
                }


            }


            if (isLandscape) {
                styledDiv {
                    css {


                        backgroundColor = Color("#4455aa")

                        float = Float.right
                        //  display = Display.inlineFlex
                        margin(right = 50.px)
                        //  alignItems=Align.center
                    }

                    iconButton("https://t.me/militaernews", "telegram")

                    iconButton("https://twitter.com/MilitaerNews?s=09", "twitter")

                    //   iconButton("mailto:militaernews@protonmail.com", "icon-mail")
                }
            }
        }


    }
}