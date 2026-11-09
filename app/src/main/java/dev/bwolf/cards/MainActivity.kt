package dev.bwolf.cards

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val player1View = findViewById<View>(R.id.playerCounter1)
        val player2View = findViewById<View>(R.id.playerCounter2)

        setupPlayerCounter(player1View, "Bwolf")
        setupPlayerCounter(player2View, "Rodrigo")
    }

    private fun setupPlayerCounter(containerView: View, playerName: String) {
        var score = 0

        val tvPlayerName = containerView.findViewById<TextView>(R.id.textViewPlayer)
        val tvPlayerScore = containerView.findViewById<TextView>(R.id.textViewPlayerScore)

        tvPlayerName.text = playerName
        tvPlayerScore.text = "0"

        fun addPoints(points: Int) {
            score += points
            tvPlayerScore.text = score.toString()
        }

        containerView.findViewById<Button>(R.id.btnMaisUm).setOnClickListener { addPoints(1) }
        containerView.findViewById<Button>(R.id.btnMaisTres).setOnClickListener { addPoints(3) }
        containerView.findViewById<Button>(R.id.btnMaisSeis).setOnClickListener { addPoints(6) }
        containerView.findViewById<Button>(R.id.btnMaisNove).setOnClickListener { addPoints(9) }
        containerView.findViewById<Button>(R.id.btnMaisDoze).setOnClickListener { addPoints(12) }
    }
}