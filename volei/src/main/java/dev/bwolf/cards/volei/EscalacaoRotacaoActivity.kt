package dev.bwolf.cards.volei

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButtonToggleGroup

class EscalacaoRotacaoActivity : AppCompatActivity() {

    private lateinit var tvPos1: TextView
    private lateinit var tvPos2: TextView
    private lateinit var tvPos3: TextView
    private lateinit var tvPos4: TextView
    private lateinit var tvPos5: TextView
    private lateinit var tvPos6: TextView

    private var selectedTeamA = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_escalacao_rotacao)

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

        tvPos1 = findViewById(R.id.tvPos1)
        tvPos2 = findViewById(R.id.tvPos2)
        tvPos3 = findViewById(R.id.tvPos3)
        tvPos4 = findViewById(R.id.tvPos4)
        tvPos5 = findViewById(R.id.tvPos5)
        tvPos6 = findViewById(R.id.tvPos6)

        val toggleTeam = findViewById<MaterialButtonToggleGroup>(R.id.toggleTeam)
        val btnSelectTeamA = findViewById<Button>(R.id.btnSelectTeamA)
        val btnSelectTeamB = findViewById<Button>(R.id.btnSelectTeamB)
        val btnRotate = findViewById<Button>(R.id.btnRotate)

        btnSelectTeamA.text = VoleiMatchState.teamAName
        btnSelectTeamB.text = VoleiMatchState.teamBName

        toggleTeam.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                selectedTeamA = checkedId == R.id.btnSelectTeamA
                updateCourtDisplay()
            }
        }

        btnRotate.setOnClickListener {
            if (selectedTeamA) {
                VoleiMatchState.rotateTeamA()
            } else {
                VoleiMatchState.rotateTeamB()
            }
            updateCourtDisplay()
        }

        updateCourtDisplay()
    }

    private fun updateCourtDisplay() {
        val lineup = if (selectedTeamA) VoleiMatchState.teamALineup else VoleiMatchState.teamBLineup
        if (lineup.size >= 6) {
            tvPos1.text = "Pos 1 (Sacador)\n${lineup[0]}"
            tvPos2.text = "Pos 2 (Rede Dir)\n${lineup[1]}"
            tvPos3.text = "Pos 3 (Rede Meio)\n${lineup[2]}"
            tvPos4.text = "Pos 4 (Rede Esq)\n${lineup[3]}"
            tvPos5.text = "Pos 5 (Fundo Esq)\n${lineup[4]}"
            tvPos6.text = "Pos 6 (Fundo Meio)\n${lineup[5]}"
        }
    }
}
