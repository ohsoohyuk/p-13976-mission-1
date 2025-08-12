package com.ll.wiseSaying

import wiseSaying.Rq

class WiseSayingController(
    private val wiseSayingService: WiseSayingService
) {
    fun enroll() {
        print("명언 : ")
        val wise = readlnOrNull()!!.trim()
        print("작가 : ")
        val author = readlnOrNull()!!.trim()

        val wiseSaying = WiseSaying(AppConfig.number+1, wise, author)
        wiseSayingService.enroll(wiseSaying)

        AppConfig.fileIO.setNextId(++AppConfig.number)
        println("${AppConfig.number}번 명언이 등록되었습니다.")
    }

    fun list(order: String?) {
        val (keywordType, keyword, page) = Rq().split(order)

        if (keywordType != null) { // keyword로 목록 페이징 출력
            println("----------------------")
            println("검색타입 : $keywordType")
            println("검색어 : $keyword")
            println("----------------------")
        }

        println("번호 / 작가 / 명언")
        println("----------------------")

        val pair = wiseSayingService.list(keywordType, keyword, page)
        pair.first.forEach { println("${it.id} / ${it.content} / ${it.author}") }

        println("----------------------")
        print("페이지 : ")
        for (i in 1..pair.second) {
            if (i == page.toInt()) print("[${i}]") else print("${i}")
            if (i != pair.second) print(" / ")
        }
        println()
    }

    fun remove(order: String) {
        val id = order.split("=")[1].toInt()

        if (wiseSayingService.remove(id)) println("${id}번 명언이 삭제되었습니다.") else println("${id}번 명언은 존재하지 않습니다.")
    }

    fun update(order: String) {
        val id = order.split("=")[1].toInt()

        val wiseSaying = AppConfig.fileIO.findById(id)

        if (wiseSaying != null) {
            println("명언(기존) : ${wiseSaying.content}")
            print("명언 : ")
            val wise = readlnOrNull()!!.trim()

            println("작가(기존) : ${wiseSaying.author}")
            print("작가 : ")
            val author = readlnOrNull()!!.trim()

            wiseSayingService.update(WiseSaying(id, wise, author))
        } else {
            println("${id}번 명언은 존재하지 않습니다.")
        }
    }

    fun build() {
        wiseSayingService.build()
        println("data.json 파일의 내용이 갱신되었습니다.")
    }
}