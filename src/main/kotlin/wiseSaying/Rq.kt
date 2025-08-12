package wiseSaying

class Rq() {
    fun split(order: String?): Triple<String?, String?, String> {
        if (order == null) return Triple(null, null, "1")

        var search: Map<String, String> = order.split("&") // "keywordType=author", "keyword=작자", "page=1"
            .map { it.split("=", limit = 2) }
            .associate { it[0] to it[1] }

        val keywordType = search["keywordType"]
        val keyword = search["keyword"]
        val page = search["page"] ?: "1"

        return Triple(keywordType, keyword, page)
    }
}