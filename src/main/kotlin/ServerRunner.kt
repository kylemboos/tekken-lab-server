import framedata.FrameDataRoutes
import spark.Spark
import spark.kotlin.*

class ServerRunner {

    fun run() {
        setEnvironmentPort()
        setupHomeRoute()
        FrameDataRoutes().createFrameDataRoutes()
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

}