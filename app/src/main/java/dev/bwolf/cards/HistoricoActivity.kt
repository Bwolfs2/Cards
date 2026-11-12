package dev.bwolf.cards

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class HistoricoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HISTORY_LIST = "extra_history_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historico)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            finish()
        }

        val historyList: List<HistoryItem> = getHistoryListFromIntent()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewHistorico)
        val tvEmptyHistory = findViewById<TextView>(R.id.tvEmptyHistory)

        if (historyList.isEmpty()) {
            recyclerView.visibility = View.GONE
            tvEmptyHistory.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            tvEmptyHistory.visibility = View.GONE
            recyclerView.adapter = HistoryAdapter(historyList)
        }
    }

    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    private fun getHistoryListFromIntent(): List<HistoryItem> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_HISTORY_LIST, ArrayList::class.java) as? ArrayList<HistoryItem>
        } else {
            intent.getSerializableExtra(EXTRA_HISTORY_LIST) as? ArrayList<HistoryItem>
        } ?: emptyList()
    }
}