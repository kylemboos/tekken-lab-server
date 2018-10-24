package framedata.parsers

import framedata.util.BASE_URL
import framedata.util.FrameDataDocumentFetcher.getCharacterHtmlDocument
import framedata.util.FrameDataDocumentFetcher.getCharacterListHtmlDocument
import framedata.models.FrameData
import framedata.util.getCharacterNameFromUrl
import framedata.util.removeBaseUrl
import org.jsoup.nodes.Document

private const val NUMBER_OF_NON_CHARACTER_HTML_LINKS = 3

class CharacterListPageParser(private val characterListDocument: Document = getCharacterListHtmlDocument()) {

    val characterUrlMap = createCharacterUrlMap()

    private fun createCharacterUrlMap(): Map<String, String> {
        val characterUrls = getCharacterUrlList()
        val characterUrlMap = mutableMapOf<String, String>()
        characterUrls.forEach { url ->
            characterUrlMap[getCharacterRouteKey(url)] = url
        }
        return characterUrlMap
    }

    private fun getCharacterRouteKey(characterUrl: String): String {
        return characterUrl
                .removeBaseUrl()
                .replace("-", "")
                .toLowerCase()
    }

    fun getAllCharacterFrameData(): FrameData.AllCharacters {
        return FrameData.AllCharacters(
                characters = characterUrlMap.entries.map { characterUrlEntry ->
                    val characterDocument = getCharacterHtmlDocument(characterUrlEntry.value)
                    val characterParser = CharacterPageParser(
                            characterName = getCharacterNameFromUrl(characterUrlEntry.value),
                            characterDocument = characterDocument)

                    characterParser.getCharacterFrameData()
                })
    }

    private fun getCharacterUrlList(): List<String> {
        return getCharacterUrls().drop(NUMBER_OF_NON_CHARACTER_HTML_LINKS).appendBaseUrlToCharacterUrls()
    }

    private fun getCharacterUrls(): List<String> {
        val characterParentElement = characterListDocument.getElementsByClass("entry clearfix").first()
        val characterElementList = characterParentElement.getElementsByTag("p")
        val characterLinks = mutableListOf<String>()
        characterElementList.forEach { characterElement ->
            characterElement.getElementsByTag("a").forEach { characterLink ->
                characterLinks.add(characterLink.attr("href"))
            }
        }
        return characterLinks
    }

    private fun List<String>.appendBaseUrlToCharacterUrls(): List<String> {
        return map { characterLink ->
            if (characterLink.startsWith(BASE_URL)) {
                characterLink
            } else {
                BASE_URL + characterLink
            }
        }
    }
}