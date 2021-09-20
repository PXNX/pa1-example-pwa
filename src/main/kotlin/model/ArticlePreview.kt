package model

data class KotlinArticlePreview(
    override val id: Int,
    override val title: String,
    override val imageUrl: String
) : ArticlePreview

external interface ArticlePreview {
    val id: Int
    val title: String
    val imageUrl: String
}