package com.ll

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import java.io.File
import kotlin.test.Test
import kotlin.test.assertContains

class wiseSayingControllerTest {
    companion object {
        @JvmStatic
        @AfterAll
        fun cleanup() {
            val dbDir = File("db")
            if (dbDir.exists()) {
                dbDir.deleteRecursively()
                println("db 폴더 삭제 완료")
            }
        }
    }

    @Test
    @DisplayName("작성")
    fun t1() {
        val result = TestRunner.run(
            """
                등록
                과거에 집착
                작가1
            """
        )

        println(result)

        assertContains(result, "명언 : ")
        assertContains(result, "작가 : ")
        assertContains(result, "1번 명언이 등록되었습니다.")
    }

    @Test
    @DisplayName("목록")
    fun t2() {
        val result = TestRunner.run(
            """
                목록
            """
        )

        val expected = """
                    1 / 과거에 집착 / 작가1
                """.trimIndent()

        println(result)

        assertTrue(result.contains("1 / 과거에 집착 / 작가1"))
    }

    @Test
    @DisplayName("수정")
    fun t3() {
        val result = TestRunner.run(
            """
                수정?id=1
                미래를 향해
                작가100
            """
        )

        println(result)

        assertContains(result, "명언(기존) : 과거에 집착")
        assertContains(result, "명언 : ")
        assertContains(result, "작가(기존) : 작가1")
        assertContains(result, "작가 : ")
    }

    @Test
    @DisplayName("삭제")
    fun t4() {
        val result = TestRunner.run(
            """
                삭제?id=1
            """
        )

        println(result)

        assertContains(result, "1번 명언이 삭제되었습니다.")
    }

    @Test
    @DisplayName("빌드")
    fun t5() {
        val result = TestRunner.run(
            """
                빌드
            """
        )

        println(result)

        assertContains(result, "data.json 파일의 내용이 갱신되었습니다.")
    }
}