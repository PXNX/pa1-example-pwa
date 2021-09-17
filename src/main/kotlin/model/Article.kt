package model

data class ArticlePreview(val id: Int, val title: String, val imageUrl: String)

data class ArticleDetail(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val publishTimestamp: Long,
    val text: String
)