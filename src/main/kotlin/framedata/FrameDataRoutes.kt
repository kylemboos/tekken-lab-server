package framedata

import com.google.gson.GsonBuilder
import framedata.parsers.CharacterListPageParser
import framedata.parsers.CharacterPageParser
import framedata.util.FrameDataDocumentFetcher.getCharacterHtmlDocument
import framedata.util.getCharacterNameFromUrl
import spark.kotlin.get

class FrameDataRoutes {

    private val characterListParser = CharacterListPageParser()
    private val characterNameToUrlMap: Map<String, String> = characterListParser.characterUrlMap
    private val allCharacterFrameData = characterListParser.getAllCharacterFrameData()

    fun createFrameDataRoutes() {
        createAllCharactersJsonRoute()
        createCharacterRoutes()
    }

    private fun createAllCharactersJsonRoute() {
        get("/all") {
            val gson = GsonBuilder().setPrettyPrinting().create()
            gson.toJson(allCharacterFrameData)
        }
    }

    private fun createCharacterRoutes() {
        characterNameToUrlMap.entries.forEach { character ->
            val characterRouteName = "/" + character.key
            val characterUrl = character.value
            val characterFullName = getCharacterNameFromUrl(characterUrl)

            get(characterRouteName) {
                val gson = GsonBuilder().setPrettyPrinting().create()
                val characterPageParser = CharacterPageParser(characterFullName, getCharacterHtmlDocument(characterUrl))
                val characterFrameData = characterPageParser.getCharacterFrameData()
                gson.toJson(characterFrameData)
            }
        }
    }
}