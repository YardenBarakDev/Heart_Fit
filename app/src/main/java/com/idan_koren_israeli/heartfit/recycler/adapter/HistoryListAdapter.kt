package com.idan_koren_israeli.heartfit.recycler.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummary

class HistoryListAdapter(private var historyList : List<WorkoutSummary>) : RecyclerView.Adapter<HistoryListAdapter.HistoryListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.holder_history_list, parent, false)

        return HistoryListHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryListHolder, position: Int) {
        val exerciseSummary = historyList[position]

        holder.historyList_LBL_heartsCollected.text = "Heart collected: " + exerciseSummary.heartsCollected
        holder.historyList_LBL_caloriesBurned.text = "Calories burned: " + exerciseSummary.caloriesBurned
        holder.historyList_LBL_totalTime.text = "Total time: " + exerciseSummary.totalDurationSeconds
        holder.historyList_LBL_difficulty.text = "Difficulty: " + exerciseSummary.difficulty
        holder.historyList_LBL_muscles.text = "Muscles: " + exerciseSummary.muscles
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun updateList(historyList : List<WorkoutSummary>){
        this.historyList = historyList
    }

    class HistoryListHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val historyList_LBL_heartsCollected : TextView = itemView.findViewById(R.id.historyList_LBL_heartsCollected)
        val historyList_LBL_caloriesBurned : TextView = itemView.findViewById(R.id.historyList_LBL_caloriesBurned)
        val historyList_LBL_totalTime : TextView = itemView.findViewById(R.id.historyList_LBL_totalTime)
        val historyList_LBL_difficulty : TextView = itemView.findViewById(R.id.historyList_LBL_difficulty)
        val historyList_LBL_muscles : TextView = itemView.findViewById(R.id.historyList_LBL_muscles)
    }

}