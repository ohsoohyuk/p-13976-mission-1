package com.ll.wiseSaying

import com.ll.wiseSaying.AppConfig.wiseSayingService

class WiseSayingService(
    private val wiseSayingRepository: WiseSayingRepository
) {
    fun enroll(wiseSaying: WiseSaying) {
        wiseSayingRepository.save(wiseSaying)
    }

    fun remove(id: Int) : Boolean {
        return wiseSayingRepository.remove(id)
    }

    fun update(wiseSaying: WiseSaying) {
        wiseSayingRepository.update(wiseSaying)
    }

    fun list(keywordType: String?, keyword: String?, page: String) : List<WiseSaying> {
        return wiseSayingRepository.getList(keywordType, keyword, page)
    }

    fun build() {
        wiseSayingRepository.build()
    }
}