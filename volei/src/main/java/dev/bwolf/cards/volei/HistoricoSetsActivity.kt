package dev.bwolf.cards.volei

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class HistoricoSetsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historico_sets)

        val mainView = findViewById<View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener { finish() }

        val rvSets = findViewById<RecyclerView>(R.id.rvSets)
        val tvEmptySets = findViewById<TextView>(R.id.tvEmptySets)
        val btnNewMatch = findViewById<Button>(R.id.btnNewMatch)

        val sets = VoleiMatchState.setHistory

        if (sets.isEmpty()) {
            rvSets.visibility = View.GONE
            tvEmptySets.visibility = View.VISIBLE
        } else {
            rvSets.visibility = View.VISIBLE
            tvEmptySets.visibility = View.GONE
            rvSets.adapter = SetAdapter(sets)
        }

        btnNewMatch.setOnClickListener {
            VoleiMatchState.resetMatch()
            finish()
        }
    }
}
