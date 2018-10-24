import com.google.gson.GsonBuilder
import framedata.parsers.CharacterListPageParser
import spark.Spark
import spark.kotlin.*

class ServerRunner {

    private val allCharacterFrameData = CharacterListPageParser().getAllCharacterFrameData()

    fun run() {
        setEnvironmentPort()
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
        }
    }

}