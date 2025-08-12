package com.ll.wiseSaying

import wiseSaying.FileIO

object AppConfig {
    val wiseSayingRepository = WiseSayingRepository()
    val wiseSayingService = WiseSayingService(wiseSayingRepository)
    val wiseSayingController = WiseSayingController(wiseSayingService)
    val fileIO = FileIO()

    var number: Int = fileIO.getNowId()
}

fun App() {
    println("== 명언 엡 ==")

    while (true) {
        print("명령) ")
        val order = readlnOrNull()!!.trim().split("?")

        when (order[0]) {
            "종료" -> break
            "등록" -> AppConfig.wiseSayingController.enroll()
            "목록" -> AppConfig.wiseSayingController.list(order.getOrNull(1))
            "삭제" -> AppConfig.wiseSayingController.remove(order[1])
            "수정" -> AppConfig.wiseSayingController.update(order[1])
            "빌드" -> AppConfig.wiseSayingController.build()
        }
    }
}