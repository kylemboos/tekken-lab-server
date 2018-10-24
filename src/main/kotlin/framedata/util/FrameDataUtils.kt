package framedata.util

const val BASE_URL = "http://rbnorway.org/"
const val CHARACTERS = "http://rbnorway.org/T7-frame-data/"

fun getCharacterNameFromUrl(characterUrl: String): String {
    val fullName = characterUrl
            .removeBaseUrl()
            .replace("-", " ")
            .removeSuffix(" ")
    return fullName.split(" ").map { it.capitalize() }.joinToString(" ")
}

fun String.removeBaseUrl(): String {
    return  removePrefix(BASE_URL)
            .replaceAfter("t7", "")
            .removeSuffix("t7")
}