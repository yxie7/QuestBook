package com.neoahdev.questbook.adapter

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.neoahdev.questbook.R
import com.neoahdev.questbook.adapter.DailyQuestRecyclerAdapter.rvViewHolder
import com.neoahdev.questbook.model.DailyQuest
import com.neoahdev.questbook.model.QuestLists
import com.neoahdev.questbook.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_daily_list.*
import kotlinx.android.synthetic.main.quest_item.view.*

class DailyQuestRecyclerAdapter(var questList: MutableList<DailyQuest>) : RecyclerView.Adapter<rvViewHolder>() {

    // Create reference to the recycler view to be used later on
    lateinit var rv: RecyclerView;

    override fun getItemCount() = questList.count()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rv = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rvViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.quest_item, parent, false)
        return rvViewHolder(v)
    }

    override fun onBindViewHolder(holder: rvViewHolder, position: Int) {
        holder.bind(position)
    }

    fun updateData(){
        questList=MainActivity.questLists.dailyQuests
    }

    fun refreshDailyQuestList(){
        notifyDataSetChanged()
    }

    // ViewHolder for each quest
    inner class rvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.mcv
        val cardCL: ConstraintLayout = itemView.cl

        var txtQuestName: TextView = itemView.txtQuestName

        //expand/collapse
        val details: View = itemView.clQuestMain
        val txtDescription: TextView = itemView.txtDescription
        val txtEstimatedTime: TextView = itemView.txtEstimatedTime

        fun bind(position: Int){
            val quest:DailyQuest = questList[position]

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