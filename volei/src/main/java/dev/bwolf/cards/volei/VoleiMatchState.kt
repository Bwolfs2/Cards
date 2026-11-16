package dev.bwolf.cards.volei

object VoleiMatchState {
    var teamAName: String = "Time A"
    var teamBName: String = "Time B"

    var teamASetsWon: Int = 0
    var teamBSetsWon: Int = 0

    var currentSetNumber: Int = 1
    var teamAPointsCurrentSet: Int = 0
    var teamBPointsCurrentSet: Int = 0

    var currentServingTeam: String = "Time A"

    val setHistory = mutableListOf<VoleiSetResult>()
    val scoreLog = mutableListOf<String>()

    var config = VoleiMatchConfig()

    var teamALineup = mutableListOf(
        "A1 (Sacador)",
        "A2 (Rede Dir)",
        "A3 (Rede Meio)",
        "A4 (Rede Esq)",
        "A5 (Fundo Esq)",
        "A6 (Fundo Meio)"
    )

    var teamBLineup = mutableListOf(
        "B1 (Sacador)",
        "B2 (Rede Dir)",
        "B3 (Rede Meio)",
        "B4 (Rede Esq)",
        "B5 (Fundo Esq)",
        "B6 (Fundo Meio)"
    )

    fun rotateTeamA() {
        if (teamALineup.size == 6) {
            val last = teamALineup.removeAt(5)
            teamALineup.add(0, last)
        }
    }

    fun rotateTeamB() {
        if (teamBLineup.size == 6) {
            val last = teamBLineup.removeAt(5)
            teamBLineup.add(0, last)
        }
    }

    fun resetMatch() {
        teamASetsWon = 0
        teamBSetsWon = 0
        currentSetNumber = 1
        teamAPointsCurrentSet = 0
        teamBPointsCurrentSet = 0
        currentServingTeam = teamAName
        setHistory.clear()
        scoreLog.clear()
    }
}
