package com.idan_koren_israeli.heartfit.mvvm.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.db.room_db.ExerciseSummary
import com.idan_koren_israeli.heartfit.mvvm.view_model.HistoryViewModel
import com.idan_koren_israeli.heartfit.enums.SortType
import com.idan_koren_israeli.heartfit.recycler.adapter.HistoryListAdapter


class FragmentHistory : Fragment() {

    private lateinit var historyListAdapter : HistoryListAdapter
    private var historyViewModel: HistoryViewModel = HistoryViewModel()
    private var viewFragmentHistory : View? = null
    private lateinit var fragmentHistory_Spinner : Spinner
    private lateinit var fragmentHistory_RecyclerView : RecyclerView
    private lateinit var exerciseSummary: List<ExerciseSummary>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewFragmentHistory == null)
            viewFragmentHistory = inflater.inflate(R.layout.fragment_history, container, false)

        findViews()

        when(historyViewModel.sortType) {
            SortType.DATE -> fragmentHistory_Spinner.setSelection(0)
            SortType.TOTAL_TIME -> fragmentHistory_Spinner.setSelection(1)
            SortType.MAX_HEARTS -> fragmentHistory_Spinner.setSelection(2)
            SortType.DIFFICULTY -> fragmentHistory_Spinner.setSelection(3)
            SortType.CALORIES_BURNED -> fragmentHistory_Spinner.setSelection(4)
        }
        spinnerOnClick()
        initRecycleView()
        observerList()

        return viewFragmentHistory
    }

    private fun initRecycleView() {
        exerciseSummary = mutableListOf()
        historyListAdapter = HistoryListAdapter(exerciseSummary)
        fragmentHistory_RecyclerView.adapter = historyListAdapter
        fragmentHistory_RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        fragmentHistory_RecyclerView.setHasFixedSize(true)
    }

    private fun spinnerOnClick() {
        fragmentHistory_Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0 -> historyViewModel.sortRuns(SortType.DATE)
                    1 -> historyViewModel.sortRuns(SortType.TOTAL_TIME)
                    2 -> historyViewModel.sortRuns(SortType.MAX_HEARTS)
                    3 -> historyViewModel.sortRuns(SortType.DIFFICULTY)
                    4 -> historyViewModel.sortRuns(SortType.CALORIES_BURNED)
                }
            }
        }
    }

    private fun observerList() {
        historyViewModel.exerciseSummary.observe(viewLifecycleOwner, Observer {
            historyListAdapter.updateList(it)
            historyListAdapter.notifyDataSetChanged()
        })
    }


    private fun findViews() {
        fragmentHistory_Spinner = viewFragmentHistory!!.findViewById(R.id.fragmentHistory_Spinner)
        fragmentHistory_RecyclerView = viewFragmentHistory!!.findViewById(R.id.fragmentHistory_RecyclerView)
    }

}