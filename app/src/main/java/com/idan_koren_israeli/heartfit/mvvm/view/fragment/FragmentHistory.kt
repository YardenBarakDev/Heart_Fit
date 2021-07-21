package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.common.CommonUtils
import com.idan_koren_israeli.heartfit.databinding.FragmentHistoryBinding
import com.idan_koren_israeli.heartfit.db.room_db.WorkoutSummary
import com.idan_koren_israeli.heartfit.mvvm.view_model.HistoryViewModel
import com.idan_koren_israeli.heartfit.enums.SortType
import com.idan_koren_israeli.heartfit.recycler.adapter.HistoryListAdapter

class FragmentHistory : Fragment(R.layout.fragment_history) {

    private lateinit var historyListAdapter: HistoryListAdapter
    private lateinit var binding : FragmentHistoryBinding
    private var historyViewModel: HistoryViewModel = HistoryViewModel()
    private lateinit var workoutSummary: List<WorkoutSummary>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHistoryBinding.bind(view)
        initSpinner()
        initRecycleView()
        observerList()
    }

    private fun initRecycleView() {
        workoutSummary = mutableListOf()
        historyListAdapter = HistoryListAdapter(workoutSummary){

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete Workout " + it.name)
                .setMessage(R.string.workout_delete_message)
                .setPositiveButton(R.string.workout_delete_positive) { _, _ ->
                    historyViewModel.deleteWorkoutSummaryFromDB(it)
                    CommonUtils.getInstance().showToast("Workout " + it.name + " was deleted successfully." )
                }
                .setNegativeButton(R.string.workout_delete_negative, null)
                .setOnDismissListener {
                }
                .show()
        }
        binding.fragmentHistoryRecyclerView.adapter = historyListAdapter
        binding.fragmentHistoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.fragmentHistoryRecyclerView.setHasFixedSize(true)
    }

    private fun initSpinner() {
        binding.fragmentHistorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                historyViewModel.sortRuns(SortType.DATE)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> historyViewModel.sortRuns(SortType.DATE)
                    1 -> historyViewModel.sortRuns(SortType.TOTAL_TIME)
                    2 -> historyViewModel.sortRuns(SortType.MAX_HEARTS)
                    3 -> historyViewModel.sortRuns(SortType.DIFFICULTY)
                    4 -> historyViewModel.sortRuns(SortType.CALORIES_BURNED)
                    5 -> historyViewModel.sortRuns(SortType.PAST_SEVEN_DAYS)
                    6 -> historyViewModel.sortRuns(SortType.PAST_MONTH)
                }
            }
        }
    }

    private fun observerList() {
        historyViewModel.exerciseSummary.observe(viewLifecycleOwner, {
            historyListAdapter.updateList(it)
            historyListAdapter.notifyDataSetChanged()
        })
    }
}