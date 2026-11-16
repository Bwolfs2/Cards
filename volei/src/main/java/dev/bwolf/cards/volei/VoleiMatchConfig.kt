package dev.bwolf.cards.volei

import java.io.Serializable

data class VoleiMatchConfig(
    var maxPointsPerSet: Int = 25,
    var tieBreakPoints: Int = 15,
    var minPointDifference: Int = 2,
    var maxSets: Int = 3
) : Serializable
