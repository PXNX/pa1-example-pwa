package data

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.coroutineScope
import model.ArticleDetail
import model.ArticlePreview
import util.Status

object Repository {
    private const val baseUrl = "https://pa1-server.herokuapp.com/"

    suspend fun fetchArticles(): Status<Any> = coroutineScope {
        return@coroutineScope try {
            Status.Success((window.fetch("${baseUrl}previews").await().json().await() as Array<ArticlePreview>))
        } catch (e: Exception) {
            console.log("fetchArticles() >> $e")
            Status.Failure(e)
        }
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