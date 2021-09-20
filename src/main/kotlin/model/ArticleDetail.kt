package model

data class KotlinArticleDetail(
    override val id: Int,
    override val title: String,
    /*   override val imageUrl: String,
       override val publishTimestamp: Long,
       override val text: String

     */
) : ArticleDetail

external interface ArticleDetail {
    val id: Int
    val title: String
    /*   val imageUrl: String
       val publishTimestamp: Long
       val text: String

     */
}