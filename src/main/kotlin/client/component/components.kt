package client.component

import client.component.loading.LoadingSpinner
import kotlinx.css.*
import kotlinx.html.DIV
import react.RBuilder
import react.dom.attrs
import styled.*

fun RBuilder.loadingComponent() = styledDiv{
    css{
        backgroundColor=Color("#fff444")
        width=LinearDimension.fillAvailable
        height= LinearDimension.fillAvailable
        alignContent= Align.center
        justifyContent=JustifyContent.center
    }


    LoadingSpinner {
    attrs {
        type="Oval"
        color="white"
        height=100
        width=100
    }
}
}

fun StyledDOMBuilder<DIV>.iconButton(url:String, iconName:String) = styledA(url) {
    styledImg(src = "icon/$iconName.svg", alt = iconName) {
        attrs {
            width = "35"
            height = "35"
        }
    }

    css {
        padding="50px"
    }
}