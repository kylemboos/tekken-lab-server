import spark.Spark
import spark.kotlin.*

class ServerRunner {

    fun run() {
        setEnvironmentPort()
        getHome()
    }

    private fun setEnvironmentPort() {
        System.getenv("PORT")?.let {
            Spark.port(it.toInt())
        }
    }

    private fun getHome() {
        get("/") {
            "Welcome to the Tekken Lab"
        }
    }

}