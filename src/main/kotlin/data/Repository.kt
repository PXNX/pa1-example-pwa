package data

import kotlinext.js.asJsObject
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.ArticleDetail
import model.ArticlePreview
import model.KotlinArticlePreview
import org.w3c.fetch.NO_CORS
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode

object Repository {
    private const val baseUrl = "https://pa1-server.herokuapp.com/"

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun fetchArticles(): List<ArticlePreview> = coroutineScope {
        /* (0..14).map{id->
             async {
                 fetchArticle(id)
             }
         }.awaitAll()

         */


        //  var articles:List<KotlinArticlePreview> = listOf()


        val response = window.fetch("${baseUrl}detail/3").await().json().await()

        val jsss:ArticleDetail = response as ArticleDetail

        console.log("JSSS::: ${JSON.stringify(jsss)} -- RES:: ${JSON.stringify(response)}")

        console.log(
            "RESP::: head ${
                response.asJsObject().valueOf()
            } -- body $response -- "//${Json.encodeToString(response)}"
        )

        val response2 = window.fetch("${baseUrl}previews").await().json().await()

        val jsss2:List<ArticlePreview> = (response2 as Array<ArticlePreview>).toList()

        console.log("JSSS::: ${JSON.stringify(jsss2)} -- RES:: ${JSON.stringify(response2)}")


        return@coroutineScope jsss2 as List<ArticlePreview>

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

    suspend fun fetchArticle(id: Int): ArticleDetail {
        console.log(">> fetchArticle(id:Int) >> id: $id")
        val response = window
            .fetch(baseUrl + "detail/$id")
            .await()
            .json()
            .await()
        console.log(">> fetchArticle(id:Int) >> response: $response --- CAST: ${response as ArticleDetail}")
        return response as ArticleDetail
    }
}