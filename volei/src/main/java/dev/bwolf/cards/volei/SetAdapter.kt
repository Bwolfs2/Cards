package dev.bwolf.cards.volei

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SetAdapter(
    private val sets: List<VoleiSetResult>
) : RecyclerView.Adapter<SetAdapter.SetViewHolder>() {

    class SetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSetTitle: TextView = itemView.findViewById(R.id.tvSetTitle)
        val tvSetScore: TextView = itemView.findViewById(R.id.tvSetScore)
        val tvSetWinner: TextView = itemView.findViewById(R.id.tvSetWinner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_set_result, parent, false)
        return SetViewHolder(view)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        val item = sets[position]
        holder.tvSetTitle.text = "Set ${item.setNumber}"
        holder.tvSetScore.text = "${item.teamAName}  ${item.teamAPoints}  x  ${item.teamBPoints}  ${item.teamBName}"
        holder.tvSetWinner.text = "Vencedor: ${item.winnerName}"
    }

    override fun getItemCount(): Int = sets.size
}
