package wiseSaying

import com.ll.wiseSaying.WiseSaying
import java.io.File

class FileIO(
    private val baseDir: String = "db/wiseSaying"
) {
    private val lastIdFile = File(baseDir, "lastId.txt")

    init {
        val dir = File(baseDir)
        if (!dir.exists()) dir.mkdirs()
        if (!lastIdFile.exists()) lastIdFile.writeText("0")
    }

    fun getNowId(): Int {
        return lastIdFile.readText().toInt()
    }

    fun setNextId(id: Int) {
        lastIdFile.writeText(id.toString())
    }

    fun wiseSayingToJson(ws: WiseSaying): String {
        return """
        {
          "id": ${ws.id},
          "content": "${ws.content}",
          "author": "${ws.author}"
        }
        """.trimIndent()
    }

    fun jsonToWiseSaying(json: String): WiseSaying {
        val idValue = json.substringAfter("\"id\":").substringBefore(",").trim().toInt()
        val contentValue = json.substringAfter("\"content\":").substringBefore(",").trim().trim('"')
        val authorValue = json.substringAfter("\"author\":").substringBefore("}").trim().trim('"')

        return WiseSaying(idValue, contentValue, authorValue)
    }

    fun save(wiseSaying: WiseSaying) {
        File(baseDir, "${wiseSaying.id}.json").writeText(wiseSayingToJson(wiseSaying))
    }

    fun findById(id: Int): WiseSaying? {
        val file = File(baseDir, "$id.json")
        return if (file.exists()) jsonToWiseSaying(file.readText()) else null
    }

    fun update(wiseSaying : WiseSaying) {
        val file = File(baseDir, "${wiseSaying.id}.json")
        file.writeText(wiseSayingToJson(wiseSaying))
    }

    fun delete(id: Int): Boolean {
        val file = File(baseDir, "$id.json")
        return file.delete()
    }

    fun findAll(): List<WiseSaying> {
        val files = File(baseDir).listFiles { f ->
            f.extension == "json" && f.nameWithoutExtension != "data"
        }

        return files
                    ?.map { jsonToWiseSaying(it.readText()) }
                    ?.sortedBy{ it.id }
                    ?: emptyList()
    }

    fun buildDataJson() {
        val all = findAll()
        val jsonArray = all.joinToString(",\n", "[\n", "\n]") { wiseSayingToJson(it) }
        File(baseDir, "data.json").writeText(jsonArray)
    }
}