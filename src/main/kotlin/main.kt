import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import react.dom.render
import styled.injectGlobal

fun main() {
    window.addEventListener("load", { event ->
        console.log("load event listener")

        /*

            window.navigator.serviceWorker.register("/sw.js").then(
                onFulfilled = {
                    console.log("ServiceWorker registration successful with scope: ", it.scope)

                }
                , onRejected = {
                    console.log("ServiceWorker registration failed: ", it.message);
                }
            )

         */
    })


    val styles = CssBuilder().apply {
        html {
            width = LinearDimension("100%")
            height = LinearDimension("100%")
            backgroundColor = Color("#223d57")
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
            fontFamily = "sans-serif"
        }
    }

    injectGlobal(styles.toString())

    render(document.getElementById("root")) {
        child(App::class) {}
    }
}