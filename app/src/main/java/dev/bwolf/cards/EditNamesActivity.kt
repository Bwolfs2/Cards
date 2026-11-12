package dev.bwolf.cards

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

class EditNamesActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PLAYER1_NAME = "extra_player1_name"
        const val EXTRA_PLAYER2_NAME = "extra_player2_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_names)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            finish()
        }

        val etPlayer1 = findViewById<TextInputEditText>(R.id.etPlayer1)
        val etPlayer2 = findViewById<TextInputEditText>(R.id.etPlayer2)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarNomes)

        val currentP1 = intent.getStringExtra(EXTRA_PLAYER1_NAME) ?: ""
        val currentP2 = intent.getStringExtra(EXTRA_PLAYER2_NAME) ?: ""

        etPlayer1.setText(currentP1)
        etPlayer2.setText(currentP2)

        btnSalvar.setOnClickListener {
            val defaultP1 = getString(R.string.default_player_name)
            val name1 = etPlayer1.text?.toString()?.trim().orEmpty().ifEmpty { currentP1.ifEmpty { defaultP1 } }
            val name2 = etPlayer2.text?.toString()?.trim().orEmpty().ifEmpty { currentP2.ifEmpty { defaultP1 } }

            val resultIntent = Intent().apply {
                putExtra(EXTRA_PLAYER1_NAME, name1)
                putExtra(EXTRA_PLAYER2_NAME, name2)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}