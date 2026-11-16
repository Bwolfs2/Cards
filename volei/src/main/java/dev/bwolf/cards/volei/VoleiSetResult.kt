package dev.bwolf.cards.volei

import java.io.Serializable

data class VoleiSetResult(
    val setNumber: Int,
    val teamAName: String,
    val teamAPoints: Int,
    val teamBName: String,
    val teamBPoints: Int,
    val winnerName: String
) : Serializable
