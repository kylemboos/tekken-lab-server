package framedata.util

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

object FrameDataDocumentFetcher {

    fun getCharacterListHtmlDocument(): Document {
        return Jsoup.connect(CHARACTERS).get()
    }

    fun getCharacterHtmlDocument(characterUrl: String): Document {
        return Jsoup.connect(characterUrl).get()
    }
}