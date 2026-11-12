package dev.bwolf.cards

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val historyList = ArrayList<HistoryItem>()
    private var moveCounter = 0

    private var player1Name = "Bwolf"
    private var player2Name = "Rodrigo"

    private lateinit var tvPlayer1Name: TextView
    private lateinit var tvPlayer2Name: TextView

    private val editNamesLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val newP1 = result.data?.getStringExtra(EditNamesActivity.EXTRA_PLAYER1_NAME)
            val newP2 = result.data?.getStringExtra(EditNamesActivity.EXTRA_PLAYER2_NAME)

            if (!newP1.isNullOrEmpty()) {
                player1Name = newP1
                tvPlayer1Name.text = newP1
            }
            if (!newP2.isNullOrEmpty()) {
                player2Name = newP2
                tvPlayer2Name.text = newP2
            }
            Toast.makeText(this, R.string.names_updated_toast, Toast.LENGTH_SHORT).show()
        }
    }

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

        tvPlayer1Name = player1View.findViewById(R.id.textViewPlayer)
        tvPlayer2Name = player2View.findViewById(R.id.textViewPlayer)

        val resetPlayer1 = setupPlayerCounter(player1View) { player1Name }
        val resetPlayer2 = setupPlayerCounter(player2View) { player2Name }

        // Botões de Ação
        val btnHistorico = findViewById<Button>(R.id.btnHistorico)
        val btnZerarHistorico = findViewById<Button>(R.id.btnZerarHistorico)
        val btnInfNomes = findViewById<Button>(R.id.btnInfNomes)

        btnHistorico.setOnClickListener {
            val intent = Intent(this, HistoricoActivity::class.java).apply {
                putExtra(HistoricoActivity.EXTRA_HISTORY_LIST, historyList)
            }
            startActivity(intent)
        }

        btnZerarHistorico.setOnClickListener {
            historyList.clear()
            moveCounter = 0
            resetPlayer1()
            resetPlayer2()
            Toast.makeText(this, R.string.history_cleared_toast, Toast.LENGTH_SHORT).show()
        }

        btnInfNomes.setOnClickListener {
            val intent = Intent(this, EditNamesActivity::class.java).apply {
                putExtra(EditNamesActivity.EXTRA_PLAYER1_NAME, player1Name)
                putExtra(EditNamesActivity.EXTRA_PLAYER2_NAME, player2Name)
            }
            editNamesLauncher.launch(intent)
        }
    }

    private fun setupPlayerCounter(containerView: View, getPlayerName: () -> String): () -> Unit {
        var score = 0

        val tvPlayerName = containerView.findViewById<TextView>(R.id.textViewPlayer)
        val tvPlayerScore = containerView.findViewById<TextView>(R.id.textViewPlayerScore)

        tvPlayerName.text = getPlayerName()
        tvPlayerScore.text = "0"

        fun addPoints(points: Int) {
            score += points
            tvPlayerScore.text = score.toString()
            moveCounter++
            val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            historyList.add(HistoryItem(moveCounter, getPlayerName(), points, time))
        }

        containerView.findViewById<Button>(R.id.btnMaisUm).setOnClickListener { addPoints(1) }
        containerView.findViewById<Button>(R.id.btnMaisTres).setOnClickListener { addPoints(3) }
        containerView.findViewById<Button>(R.id.btnMaisSeis).setOnClickListener { addPoints(6) }
        containerView.findViewById<Button>(R.id.btnMaisNove).setOnClickListener { addPoints(9) }
        containerView.findViewById<Button>(R.id.btnMaisDoze).setOnClickListener { addPoints(12) }

        return {
            score = 0
            tvPlayerScore.text = "0"
        }
    }
}