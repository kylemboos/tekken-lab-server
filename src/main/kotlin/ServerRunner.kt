import com.google.gson.GsonBuilder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import spark.Spark
import spark.kotlin.*

class ServerRunner {

    private lateinit var allCharacterFrameData: FrameData.AllCharacters

    fun run() {
        setEnvironmentPort()
        allCharacterFrameData = getAllCharacterFrameData()
        setupHomeRoute()
        setupAllCharactersJsonRoute()
    }

    private fun setEnvironmentPort() {
        System.getenv("PORT")?.let {
            Spark.port(it.toInt())
        }
    }

    private fun setupHomeRoute() {
        get("/") {
            "Welcome to the Tekken Lab"
        }
    }

    private fun setupAllCharactersJsonRoute() {
        get("/all") {
            val gson = GsonBuilder().setPrettyPrinting().create()
            gson.toJson(allCharacterFrameData)
            //val allCharacterFrameData = GsonBuilder().create().fromJson(TEST_ALL_CHARACTERS_JSON, FrameData.AllCharacters::class.java) //from json to data classes
        }
    }

    private fun getAllCharacterFrameData(): FrameData.AllCharacters {
        val characterDocument = getCharacterPageDocument()
        val allLinks = getCharacterUrls(characterDocument)
        val characterLinks = appendBaseUrlToCharacterUrls(allLinks.drop(3))

        return FrameData.AllCharacters(
                characters = characterLinks.map {
                    getCharacterFrameData(
                            characterName = getCharacterNameFromUrl(it),
                            characterUrl = it)
                })
    }

    private fun getCharacterNameFromUrl(characterUrl: String): String {
        val fullName = characterUrl
                .removePrefix(URLS.BASE_URL)
                .replaceAfter("t7", "")
                .removeSuffix("t7")
                .replace("-", " ")
                .removeSuffix(" ")
        return fullName.split(" ").map { it.capitalize() }.joinToString(" ")
    }

    private fun getCharacterPageDocument(): Document {
        return Jsoup.connect(URLS.CHARACTERS).get()
    }

    private fun getCharacterUrls(document: Document): List<String> {
        val characterParentElement = document.getElementsByClass("entry clearfix").first()
        val characterElementList = characterParentElement.getElementsByTag("p")
        val characterLinks = mutableListOf<String>()
        characterElementList.forEach { characterElement ->
            characterElement.getElementsByTag("a").forEach { characterLink ->
                characterLinks.add(characterLink.attr("href"))
            }
        }
        return characterLinks
    }

    private fun appendBaseUrlToCharacterUrls(characterLinks: List<String>): List<String> {
        return characterLinks.map { characterLink ->
            if (characterLink.startsWith(URLS.BASE_URL)) {
                characterLink
            } else {
                URLS.BASE_URL + characterLink
            }
        }
    }

    private fun getCharacterFrameData(characterName: String, characterUrl: String): FrameData.CharacterFrameData {
        val document = Jsoup.connect(characterUrl).get()
        val moveTables = document.select("div.entry table")

        val moveTypeFrameData = FrameData.MoveList(
                basicMoveList = createMoveListFrameData(moveTables[0].getElementsByTag("tr")),
                specialMoveList = if (isRegularCharacter(characterName)) createMoveListFrameData(moveTables[1].getElementsByTag("tr")) else null)

        return FrameData.CharacterFrameData(
                name = characterName,
                moveList = moveTypeFrameData)
    }

    private fun isRegularCharacter(characterName: String): Boolean {
        return when {
            characterName.contains("Anna", true) -> false
            characterName.contains("Lei", true) -> false
            characterName.contains("Noctis", true) -> false

            else -> true
        }
    }

    private fun createMoveListFrameData(moveElements: List<Element>): List<FrameData.MoveFrameData> {
        return moveElements.map { createMoveFrameData(it) }
    }

    private fun createMoveFrameData(moveRowElement: Element): FrameData.MoveFrameData {
        val frameValues = moveRowElement.select("td")
                .map {
                    it.textNodes()
                    it.text()
                }
        return FrameData.MoveFrameData(
                command = frameValues[0],
                hitType = frameValues[1],
                damage = frameValues[2],
                startUpFrame = frameValues[3],
                onBlock = frameValues[4],
                onHit = frameValues[5],
                onCounterHit = frameValues[6],
                notes = if (frameValues.size > 7) frameValues[7] else ""
        )
    }

}