import spark.Spark
import spark.kotlin.*

class ServerRunner {

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
            """
                {
	"characters": [{
			"heihachi": [{
					"basicMoves": [{
							"command": "1",
							"hitType": "high",
							"damage": "7",
							"startUpFrame": "10",
							"onBlock": "+1",
							"onHit": "+8",
							"onCounterHit": "+8",
							"notes": ""
						},
						{
							"command": "d/f+1",
							"hitType": "mid",
							"damage": "13",
							"startUpFrame": "13~14",
							"onBlock": "-1~0",
							"onHit": "+9~+10",
							"onCounterHit": "+9~+10",
							"notes": ""
						}
					]
				},
				{
					"specialMoves": [{
						"command": "in rage d+1+2",
						"hitType": "mid",
						"damage": "55",
						"startUpFrame": "20 pc8~17",
						"onBlock": "-22",
						"onHit": "KND",
						"onCounterHit": "KND",
						"notes": "Rage art"
					}]
				}
			]
		},
		{
			"kazuya": [{
					"basicMoves": [{
							"command": "1",
							"hitType": "high",
							"damage": "7",
							"startUpFrame": "10",
							"onBlock": "+1",
							"onHit": "+8",
							"onCounterHit": "+8",
							"notes": ""
						},
						{
							"command": "d/f+1",
							"hitType": "mid",
							"damage": "13",
							"startUpFrame": "13~14",
							"onBlock": "-1~0",
							"onHit": "+9~+10",
							"onCounterHit": "+9~+10",
							"notes": ""
						}
					]
				},
				{
					"specialMoves": [{
						"command": "in rage d+1+2",
						"hitType": "mid",
						"damage": "55",
						"startUpFrame": "20 pc8~17",
						"onBlock": "-22",
						"onHit": "KND",
						"onCounterHit": "KND",
						"notes": "Rage art"
					}]
				}
			]
		}
	]
}
            """.trimIndent()
        }
    }

}