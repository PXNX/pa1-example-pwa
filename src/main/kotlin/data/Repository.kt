package data

import kotlinx.browser.window
import kotlinx.coroutines.await
import model.ArticleDetail
import model.ArticlePreview

object Repository{
    private const val baseUrl = "https://pa1-server.herokuapp.com/"

    suspend fun fetchArticles(): List<ArticlePreview> {
        val response = window
            .fetch(baseUrl+"previews")
            .await()
            .json()
            .await()
        console.log(">>>>>>>>> RESP:: $response")
        return response as List<ArticlePreview>
    }

    suspend fun fetchArticle(id:Int): ArticleDetail {
        val response = window
            .fetch(baseUrl+"detail/${id}")
            .await()
            .json()
            .await()
        return response as ArticleDetail
    }
}