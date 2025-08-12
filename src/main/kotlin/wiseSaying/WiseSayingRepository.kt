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

    fun getList(keywordType: String?, keyword: String?, page: String) : List<WiseSaying> {
        return AppConfig.fileIO.findAll()
            .filter { it.content?.contains(keyword.orEmpty()) == true } // null 안전 처리
            .reversed()
            .drop((page.toInt() - 1) * 5) // 이전 페이지 건너뛰기
            .take(5) // 5개 가져오기
    }

    fun build() {
        AppConfig.fileIO.buildDataJson()
    }
}