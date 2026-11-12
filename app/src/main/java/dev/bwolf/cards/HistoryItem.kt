package dev.bwolf.cards

import java.io.Serializable

data class HistoryItem(
    val moveNumber: Int,
    val playerName: String,
    val points: Int,
    val timestamp: String
) : Serializable
