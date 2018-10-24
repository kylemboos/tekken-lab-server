const val TEST_ALL_CHARACTERS_JSON = """
                {
	"characters": [{
			"name": "heihachi",
			"moveList": [{
					"basicMoveList": [{
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
					"specialMoveList": [{
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
			"name": "heihachi",
			"moveList": [{
					"basicMoveList": [{
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
					"specialMoveList": [{
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
            """

object URLS {
    val BASE_URL = "http://rbnorway.org/"
    val CHARACTERS = "http://rbnorway.org/T7-frame-data/"
}