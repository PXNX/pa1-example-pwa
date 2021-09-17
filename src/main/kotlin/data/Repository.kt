package data

import io.ktor.client.features.*
import io.ktor.client.features.get
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinext.js.asJsObject
import kotlinx.browser.window
import kotlinx.coroutines.*
import model.ArticleDetail
import model.ArticlePreview
import model.KotlinArticlePreview
import org.w3c.fetch.NO_CORS
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode

object Repository{
    private const val baseUrl = "https://pa1-server.herokuapp.com/"

    suspend fun fetchArticles(): List<ArticlePreview> = coroutineScope {
       /* (0..14).map{id->
            async {
                fetchArticle(id)
            }
        }.awaitAll()

        */


      //  var articles:List<KotlinArticlePreview> = listOf()




       val response = window.fetch("${baseUrl}previews", RequestInit(mode = RequestMode.NO_CORS)).await()

        console.log("RESP::: head ${response.headers.asJsObject().valueOf()} -- body ${response.body}")

       val jsss = response.json().await()

        console.log("JSSS::: $jsss")

        return@coroutineScope jsss       as List<ArticlePreview>

     /*   return@coroutineScope httpClient.get<List<ArticlePreview>>("previews"){

           /* headers {
                append(HttpHeaders.Accept, "application/json")
                append(HttpHeaders.AccessControlAllowOrigin,"*")
            }

            */
        }

      */



/*
    window


        .fetch("http://localhost:8084/previews")





        val response = window
       //     .fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos")

            .fetch("http://localhost:8084/previews")

            .await()
         /*   .json()
            .await()

          */
        console.log(">> fetchArticle(id:Int) >> response: $response --- CAST: ${ response as List<ArticlePreview>}")
        return response as List<ArticlePreview>
         */

    }

    suspend fun fetchArticle(id:Int): ArticleDetail {
        console.log(">> fetchArticle(id:Int) >> id: $id")
        val response = window
            .fetch(baseUrl+"detail/$id")
            .await()
            .json()
            .await()
        console.log(">> fetchArticle(id:Int) >> response: $response --- CAST: ${ response as ArticleDetail}")
        return response as ArticleDetail
    }
}