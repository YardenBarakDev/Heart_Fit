package com.idan_koren_israeli.heartfit.recycler.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummary
import java.text.SimpleDateFormat
import java.util.*

class HistoryListAdapter(private var historyList : List<WorkoutSummary>) : RecyclerView.Adapter<HistoryListAdapter.HistoryListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.holder_history_list, parent, false)

        return HistoryListHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryListHolder, position: Int) {
        val exerciseSummary = historyList[position]



        holder.historyList_LBL_heartsCollected.text = exerciseSummary.heartsCollected.toString()
        holder.historyList_LBL_caloriesBurned.text = exerciseSummary.caloriesBurned.toString()
        holder.historyList_LBL_caloriesBurned.text = exerciseSummary.caloriesBurned.toString()
        holder.historyList_LBL_timestamp.text = convertLongToTime(exerciseSummary.timestamp)
        holder.historyList_LBL_totalTime.text = formatSecondsToTime(exerciseSummary.totalDurationSeconds)
        holder.historyList_LBL_difficulty_muscles.text = exerciseSummary.difficulty
        holder.historyList_LBL_name.text = exerciseSummary.name
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US)
        return format.format(date)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun updateList(historyList : List<WorkoutSummary>){
        this.historyList = historyList
    }

    fun String.capitalizeWords(): String = split(" ").joinToString(" ")
    { it.replaceFirstChar { it -> if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }

    fun formatSecondsToTime(seconds: Int) :String = String.format("%02d:%02d:%02d", seconds / 3600,
        (seconds % 3600) / 60, (seconds % 60));

    class HistoryListHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val historyList_LBL_heartsCollected : TextView = itemView.findViewById(R.id.historyList_LBL_heartsCollected)
        val historyList_LBL_caloriesBurned : TextView = itemView.findViewById(R.id.historyList_LBL_caloriesBurned)
        val historyList_LBL_totalTime : TextView = itemView.findViewById(R.id.historyList_LBL_totalTime)
        val historyList_LBL_difficulty_muscles : TextView = itemView.findViewById(R.id.historyList_LBL_difficulty_muscles)
        val historyList_LBL_timestamp : TextView = itemView.findViewById(R.id.historyList_LBL_timestamp)
        val historyList_LBL_name: TextView = itemView.findViewById(R.id.historyList_LBL_name)
    }



}