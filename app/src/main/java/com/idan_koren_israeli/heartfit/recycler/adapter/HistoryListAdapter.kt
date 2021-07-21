package com.idan_koren_israeli.heartfit.recycler.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummary
import com.idan_koren_israeli.heartfit.mvvm.model.Workout
import java.text.SimpleDateFormat
import java.util.*

class HistoryListAdapter(private var historyList : List<WorkoutSummary>,  private val onWorkoutLongClick: (summary: WorkoutSummary) -> Unit) : RecyclerView.Adapter<HistoryListAdapter.HistoryListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.holder_history_list, parent, false)

        return HistoryListHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryListHolder, position: Int) {
        val workoutSummary = historyList[position]




        holder.textHeartsCollected.text = workoutSummary.heartsCollected.toString()
        holder.textCaloriesBurned.text = workoutSummary.caloriesBurned.toString()
        holder.textCaloriesBurned.text = workoutSummary.caloriesBurned.toString()
        holder.textTimestamp.text = convertLongToTime(workoutSummary.timestamp)
        holder.textTotalTime.text = formatSecondsToTime(workoutSummary.totalDurationSeconds)
        holder.textDifficulty.text = workoutSummary.difficulty.substring(0, minOf(6, workoutSummary.difficulty.length))
        holder.textName.text = workoutSummary.name
        holder.layoutParent.setOnLongClickListener {
            onWorkoutLongClick.invoke(workoutSummary)
            true
        }
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
        val textHeartsCollected : TextView = itemView.findViewById(R.id.historyList_LBL_heartsCollected)
        val textCaloriesBurned : TextView = itemView.findViewById(R.id.historyList_LBL_caloriesBurned)
        val textTotalTime : TextView = itemView.findViewById(R.id.historyList_LBL_totalTime)
        val textDifficulty : TextView = itemView.findViewById(R.id.historyList_LBL_difficulty_muscles)
        val textTimestamp : TextView = itemView.findViewById(R.id.historyList_LBL_timestamp)
        val textName: TextView = itemView.findViewById(R.id.historyList_LBL_name)
        val layoutParent: ViewGroup = itemView.findViewById(R.id.historyList_LAY_parent)
    }



}