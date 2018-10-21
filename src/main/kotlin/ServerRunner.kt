import spark.kotlin.*

class ServerRunner {

    fun run() {
        println("Im Working Again!")

        get("/") {
            "Welcome to the Tekken Lab"
        }
    }

}