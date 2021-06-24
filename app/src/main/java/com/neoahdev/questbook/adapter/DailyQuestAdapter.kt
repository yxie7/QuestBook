package com.neoahdev.questbook.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.neoahdev.questbook.R
import com.neoahdev.questbook.dummy.DummyContent.DummyItem
import com.neoahdev.questbook.model.DailyQuest
import kotlinx.android.synthetic.main.quest_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class DailyQuestAdapter(
    private val questList: MutableList<DailyQuest>
) : RecyclerView.Adapter<DailyQuestAdapter.ViewHolder>() {

    // Create reference to the recycler view to be used later on
    lateinit var rv: RecyclerView;
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_quest_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = questList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: CardView = itemView.mcv
        val cardCL: ConstraintLayout = itemView.cl
        var txtQuestName: TextView = itemView.txtQuestName

        //expand/collapse
        val details: View = itemView.clQuestMain
        val txtDescription: TextView = itemView.txtDescription
        val txtEstimatedTime: TextView = itemView.txtEstimatedTime

        fun bind(position: Int) {
            val quest: DailyQuest = questList[position]

            var completed = quest.completed
            txtQuestName.text = quest.name
            if (quest.description.isBlank())
                txtDescription.text = "No Description Available..."
            else txtDescription.text = questList[position].description
            when {
                quest.estimatedCompletionTime == 1 -> txtEstimatedTime.text =
                    quest.estimatedCompletionTime.toString() + " Minute"
                quest.estimatedCompletionTime > 1 -> txtEstimatedTime.text =
                    quest.estimatedCompletionTime.toString() + " Minutes"
                quest.estimatedCompletionTime < 1 -> txtEstimatedTime.visibility =
                    View.GONE
            }
        }

    }
}