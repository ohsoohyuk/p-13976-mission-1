package com.ll.wiseSaying

class WiseSayingRepository {
    fun save(wiseSaying: WiseSaying) {
        AppConfig.fileIO.save(wiseSaying)
    }

    fun remove(id: Int) : Boolean {
        return AppConfig.fileIO.delete(id)
    }

    fun update(wiseSaying: WiseSaying) {
        AppConfig.fileIO.update(wiseSaying)
    }

    fun getList(keywordType: String?, keyword: String?, page: String) : Pair<List<WiseSaying>, Int> {
        if (keywordType == "content") {
            return AppConfig.fileIO.findAll()
                .filter { it.content?.contains(keyword.orEmpty()) == true }
                .reversed()
                .let { filtered ->
                    val totalPages = (filtered.size + 5 - 1) / 5
                    filtered.drop((page.toInt() - 1) * 5)
                        .take(5) to totalPages
                }
        }

        return AppConfig.fileIO.findAll()
            .filter { it.author?.contains(keyword.orEmpty()) == true }
            .reversed()
            .let { filtered ->
                val totalPages = (filtered.size + 5 - 1) / 5
                filtered.drop((page.toInt() - 1) * 5)
                    .take(5) to totalPages
            }
    }

    fun build() {
        AppConfig.fileIO.buildDataJson()
    }
}