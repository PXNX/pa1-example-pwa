import kotlinext.js.jsObject
import kotlinx.browser.document
import kotlinx.css.*
import react.dom.render
import styled.injectGlobal

fun main(){
    installServiceWorker()

    val styles = CssBuilder().apply {
       html {
            width = LinearDimension("100%")
            height = LinearDimension("100%")
           backgroundColor=Color("#223d57")
        }
        body {
            margin = "0"
            padding = "0"
            width = LinearDimension("100%")
            height = LinearDimension("100%")
        }
        "#root" {
            width = LinearDimension("100%")
            height = LinearDimension("100%")
            textAlign = TextAlign.center
        }
    }

    injectGlobal(styles.toString())

    render(document.getElementById("root")) {
        App(props = jsObject(), handler = {})
    }
}