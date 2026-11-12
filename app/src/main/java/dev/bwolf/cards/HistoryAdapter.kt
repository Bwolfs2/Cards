package dev.bwolf.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(
    private val items: List<HistoryItem>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMoveNumber: TextView = itemView.findViewById(R.id.tvMoveNumber)
        val tvPlayerName: TextView = itemView.findViewById(R.id.tvPlayerName)
        val tvPoints: TextView = itemView.findViewById(R.id.tvPoints)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        holder.tvMoveNumber.text = context.getString(R.string.history_move_number, item.moveNumber)
        holder.tvPlayerName.text = item.playerName
        holder.tvPoints.text = context.getString(R.string.history_points, item.points)
        holder.tvTimestamp.text = item.timestamp
    }

    override fun getItemCount(): Int = items.size
}
