package framedata.parsers

import framedata.util.BASE_URL
import framedata.util.FrameDataDocumentFetcher.getCharacterHtmlDocument
import framedata.util.FrameDataDocumentFetcher.getCharacterListHtmlDocument
import framedata.models.FrameData
import org.jsoup.nodes.Document

private const val NUMBER_OF_NON_CHARACTER_HTML_LINKS = 3

class CharacterListPageParser(private val characterListDocument: Document = getCharacterListHtmlDocument()) {

    fun getAllCharacterFrameData(): FrameData.AllCharacters {
        val characterUrls = getCharacterUrlList()

        return FrameData.AllCharacters(
                characters = characterUrls.map { characterUrl ->
                    val characterDocument = getCharacterHtmlDocument(characterUrl)
                    val characterParser = CharacterPageParser(
                            characterName = getCharacterNameFromUrl(characterUrl),
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

    private fun getCharacterNameFromUrl(characterUrl: String): String {
        val fullName = characterUrl
                .removePrefix(BASE_URL)
                .replaceAfter("t7", "")
                .removeSuffix("t7")
                .replace("-", " ")
                .removeSuffix(" ")
        return fullName.split(" ").map { it.capitalize() }.joinToString(" ")
    }
}