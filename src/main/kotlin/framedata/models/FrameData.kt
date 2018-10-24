package framedata.models

object FrameData {
    data class AllCharacters(val characters: List<CharacterFrameData>)
    data class CharacterFrameData(
            val name: String,
            val moveList: MoveList
    )

    data class MoveList(
            val basicMoveList: List<MoveFrameData>,
            val specialMoveList: List<MoveFrameData>? = null
    )

    data class MoveFrameData(
            val command: String,
            val hitType: String,
            val damage: String,
            val startUpFrame: String,
            val onBlock: String,
            val onHit: String,
            val onCounterHit: String,
            val notes: String
    )
}