package dev.bwolf.cards.volei

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlacarTempoRealActivity : AppCompatActivity() {

    private lateinit var tvSetProgress: TextView
    private lateinit var tvSetsScore: TextView
    private lateinit var tvServing: TextView
    private lateinit var tvTeamAName: TextView
    private lateinit var tvTeamAPoints: TextView
    private lateinit var tvTeamBName: TextView
    private lateinit var tvTeamBPoints: TextView

    private val scoreHistory = mutableListOf<Pair<Int, Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_placar_tempo_real)

        val mainView = findViewById<android.view.View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        tvSetProgress = findViewById(R.id.tvSetProgress)
        tvSetsScore = findViewById(R.id.tvSetsScore)
        tvServing = findViewById(R.id.tvServing)
        tvTeamAName = findViewById(R.id.tvTeamAName)
        tvTeamAPoints = findViewById(R.id.tvTeamAPoints)
        tvTeamBName = findViewById(R.id.tvTeamBName)
        tvTeamBPoints = findViewById(R.id.tvTeamBPoints)

        val btnPointTeamA = findViewById<Button>(R.id.btnPointTeamA)
        val btnPointTeamB = findViewById<Button>(R.id.btnPointTeamB)
        val btnUndo = findViewById<Button>(R.id.btnUndo)
        val btnEscalacao = findViewById<Button>(R.id.btnEscalacao)
        val btnHistoricoSets = findViewById<Button>(R.id.btnHistoricoSets)
        val btnRegras = findViewById<Button>(R.id.btnRegras)

        btnPointTeamA.setOnClickListener { addPointToTeamA() }
        btnPointTeamB.setOnClickListener { addPointToTeamB() }
        btnUndo.setOnClickListener { undoPoint() }

        btnEscalacao.setOnClickListener {
            startActivity(Intent(this, EscalacaoRotacaoActivity::class.java))
        }
        btnHistoricoSets.setOnClickListener {
            startActivity(Intent(this, HistoricoSetsActivity::class.java))
        }
        btnRegras.setOnClickListener {
            startActivity(Intent(this, RegrasConfiguracoesActivity::class.java))
        }

        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun addPointToTeamA() {
        scoreHistory.add(Pair(VoleiMatchState.teamAPointsCurrentSet, VoleiMatchState.teamBPointsCurrentSet))
        VoleiMatchState.teamAPointsCurrentSet++
        if (VoleiMatchState.currentServingTeam != VoleiMatchState.teamAName) {
            VoleiMatchState.currentServingTeam = VoleiMatchState.teamAName
            VoleiMatchState.rotateTeamA()
        }
        checkSetWinner()
        updateUI()
    }

    private fun addPointToTeamB() {
        scoreHistory.add(Pair(VoleiMatchState.teamAPointsCurrentSet, VoleiMatchState.teamBPointsCurrentSet))
        VoleiMatchState.teamBPointsCurrentSet++
        if (VoleiMatchState.currentServingTeam != VoleiMatchState.teamBName) {
            VoleiMatchState.currentServingTeam = VoleiMatchState.teamBName
            VoleiMatchState.rotateTeamB()
        }
        checkSetWinner()
        updateUI()
    }

    private fun undoPoint() {
        if (scoreHistory.isNotEmpty()) {
            val last = scoreHistory.removeAt(scoreHistory.size - 1)
            VoleiMatchState.teamAPointsCurrentSet = last.first
            VoleiMatchState.teamBPointsCurrentSet = last.second
            updateUI()
        }
    }

    private fun checkSetWinner() {
        val cfg = VoleiMatchState.config
        val isTieBreak = VoleiMatchState.currentSetNumber == cfg.maxSets
        val targetPoints = if (isTieBreak) cfg.tieBreakPoints else cfg.maxPointsPerSet

        val ptsA = VoleiMatchState.teamAPointsCurrentSet
        val ptsB = VoleiMatchState.teamBPointsCurrentSet
        val diff = kotlin.math.abs(ptsA - ptsB)

        if ((ptsA >= targetPoints || ptsB >= targetPoints) && diff >= cfg.minPointDifference) {
            val winner = if (ptsA > ptsB) VoleiMatchState.teamAName else VoleiMatchState.teamBName
            if (winner == VoleiMatchState.teamAName) {
                VoleiMatchState.teamASetsWon++
            } else {
                VoleiMatchState.teamBSetsWon++
            }

            VoleiMatchState.setHistory.add(
                VoleiSetResult(
                    setNumber = VoleiMatchState.currentSetNumber,
                    teamAName = VoleiMatchState.teamAName,
                    teamAPoints = ptsA,
                    teamBName = VoleiMatchState.teamBName,
                    teamBPoints = ptsB,
                    winnerName = winner
                )
            )

            val neededSets = (cfg.maxSets / 2) + 1
            if (VoleiMatchState.teamASetsWon >= neededSets || VoleiMatchState.teamBSetsWon >= neededSets) {
                val matchWinner = if (VoleiMatchState.teamASetsWon > VoleiMatchState.teamBSetsWon) VoleiMatchState.teamAName else VoleiMatchState.teamBName
                Toast.makeText(
                    this,
                    getString(R.string.match_winner_toast, matchWinner, VoleiMatchState.teamASetsWon, VoleiMatchState.teamBSetsWon),
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(this, HistoricoSetsActivity::class.java))
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.set_winner_toast, VoleiMatchState.currentSetNumber, winner, ptsA, ptsB),
                    Toast.LENGTH_SHORT
                ).show()
                VoleiMatchState.currentSetNumber++
                VoleiMatchState.teamAPointsCurrentSet = 0
                VoleiMatchState.teamBPointsCurrentSet = 0
                scoreHistory.clear()
            }
        }
    }

    private fun updateUI() {
        tvTeamAName.text = VoleiMatchState.teamAName
        tvTeamBName.text = VoleiMatchState.teamBName
        tvTeamAPoints.text = VoleiMatchState.teamAPointsCurrentSet.toString()
        tvTeamBPoints.text = VoleiMatchState.teamBPointsCurrentSet.toString()

        tvSetProgress.text = getString(R.string.set_count_format, VoleiMatchState.currentSetNumber, VoleiMatchState.config.maxSets)
        tvSetsScore.text = getString(R.string.sets_score_format, VoleiMatchState.teamASetsWon, VoleiMatchState.teamBSetsWon)
        tvServing.text = getString(R.string.serving_indicator, VoleiMatchState.currentServingTeam)
    }
}
