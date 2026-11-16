package dev.bwolf.cards.volei

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

class RegrasConfiguracoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regras_configuracoes)

        val mainView = findViewById<android.view.View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener { finish() }

        val etTeamAName = findViewById<TextInputEditText>(R.id.etTeamAName)
        val etTeamBName = findViewById<TextInputEditText>(R.id.etTeamBName)
        val etMaxPoints = findViewById<TextInputEditText>(R.id.etMaxPoints)
        val etTieBreakPoints = findViewById<TextInputEditText>(R.id.etTieBreakPoints)
        val etMinDifference = findViewById<TextInputEditText>(R.id.etMinDifference)
        val etMaxSets = findViewById<TextInputEditText>(R.id.etMaxSets)
        val btnSaveConfig = findViewById<Button>(R.id.btnSaveConfig)

        etTeamAName.setText(VoleiMatchState.teamAName)
        etTeamBName.setText(VoleiMatchState.teamBName)
        etMaxPoints.setText(VoleiMatchState.config.maxPointsPerSet.toString())
        etTieBreakPoints.setText(VoleiMatchState.config.tieBreakPoints.toString())
        etMinDifference.setText(VoleiMatchState.config.minPointDifference.toString())
        etMaxSets.setText(VoleiMatchState.config.maxSets.toString())

        btnSaveConfig.setOnClickListener {
            val nameA = etTeamAName.text?.toString()?.trim().orEmpty().ifEmpty { "Time A" }
            val nameB = etTeamBName.text?.toString()?.trim().orEmpty().ifEmpty { "Time B" }
            val maxPts = etMaxPoints.text?.toString()?.toIntOrNull() ?: 25
            val tiePts = etTieBreakPoints.text?.toString()?.toIntOrNull() ?: 15
            val minDiff = etMinDifference.text?.toString()?.toIntOrNull() ?: 2
            val maxSets = etMaxSets.text?.toString()?.toIntOrNull() ?: 3

            VoleiMatchState.teamAName = nameA
            VoleiMatchState.teamBName = nameB
            VoleiMatchState.config = VoleiMatchConfig(
                maxPointsPerSet = maxPts,
                tieBreakPoints = tiePts,
                minPointDifference = minDiff,
                maxSets = maxSets
            )

            Toast.makeText(this, R.string.config_saved_toast, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
