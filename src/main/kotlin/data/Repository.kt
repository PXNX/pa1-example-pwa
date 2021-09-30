package data

import kotlinx.browser.window
import kotlinx.coroutines.coroutineScope
import model.ArticlePreview
import model.KotlinArticlePreview
import org.w3c.fetch.RequestInit
import util.Status
import kotlin.js.Promise
import kotlin.js.json

object Repository {
    private const val baseUrl = "https://pa1-server.herokuapp.com/"

    suspend fun fetchArticlePreviews(): Status<Any> = coroutineScope {
        return@coroutineScope try {
            Status.Success(
                getAndParseResult(
                    "${baseUrl}previews",
                    null,
                    ::parseToPreviews
                )
            )
        } catch (e: Exception) {
            console.log("fetchArticles() >> $e")
            Status.Failure(e)
        }
    }

    private fun parseToPreviews(json: dynamic): List<ArticlePreview> {
        val result: MutableList<ArticlePreview> = mutableListOf()
        for (item in json) {
            result.add(parsePreview(item))
        }
        return result
    }

    /*
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


     */


    private fun parsePreview(json: dynamic) = KotlinArticlePreview(
        json.id as Int,
        json.title as String,
        json.imageUrl as String
    )

    private fun <T> getAndParseResult(url: String, body: dynamic, parse: (dynamic) -> T): Promise<T> =
        requestAndParseResult("GET", url, body, parse)

    private fun <T> requestAndParseResult(
        method: String,
        url: String,
        body: dynamic,
        parse: (dynamic) -> T
    ): Promise<T> {
        val response = window.fetch(url, object : RequestInit {
            override var method: String? = method
            override var body: dynamic = body
            override var headers: dynamic = json("Accept" to "application/json")
        })

        return response
            .then { it.json() }
            .then { parse(it) }
    }
}