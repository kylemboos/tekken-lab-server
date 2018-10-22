object FrameData {
    data class AllCharacters(val characters: List<CharacterFrameData>)
    data class CharacterFrameData(
            val name: String,
            val moveTypes: List<MoveTypes>
    )

    data class MoveTypes(
            val basicMoves: List<MoveFrameData>,
            val specialMoves: List<MoveFrameData>
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