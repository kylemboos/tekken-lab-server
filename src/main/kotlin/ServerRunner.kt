import spark.kotlin.*

class ServerRunner {

    fun run() {
        println("Im Working!")

        get("/") {
            "Welcome to the Tekken Lab"
        }
    }

}