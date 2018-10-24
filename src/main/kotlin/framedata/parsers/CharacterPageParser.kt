package framedata.parsers

import framedata.models.FrameData
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class CharacterPageParser(private val characterName: String, private val characterDocument: Document) {

    fun getCharacterFrameData(): FrameData.CharacterFrameData {
        val moveTables = characterDocument.select("div.entry table")

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