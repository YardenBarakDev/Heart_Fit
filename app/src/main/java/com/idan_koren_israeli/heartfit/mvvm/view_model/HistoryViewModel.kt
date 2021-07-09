package com.idan_koren_israeli.heartfit.mvvm.view_model

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.idan_koren_israeli.heartfit.db.room_db.ExerciseSummary
import com.idan_koren_israeli.heartfit.mvvm.repository.HistoryRepository
import com.idan_koren_israeli.heartfit.other.SortType

class HistoryViewModel : ViewModel() {

    private var historyRepository = HistoryRepository()

    private val exerciseSummaryDate = historyRepository.getAllExerciseSummarySortedByDate()
    private val exerciseSummaryCaloriesBurned = historyRepository.getAllExerciseSummarySortedByCaloriesBurned()
    private val exerciseSummaryDifficulty = historyRepository.getAllExerciseSummarySortedByDifficulty()
    private val exerciseSummaryMaXHearts = historyRepository.getAllExerciseSummarySortedByMaXHearts()
    private val exerciseSummaryTotalTime = historyRepository.getAllExerciseSummarySortedByTotalTime()

    val exerciseSummary = MediatorLiveData<List<ExerciseSummary>>()
    var sortType = SortType.DATE

    init {
        exerciseSummary.addSource(exerciseSummaryDate) { result ->
            if(sortType == SortType.DATE) {
                result?.let { exerciseSummary.value = it }
            }
        }
        exerciseSummary.addSource(exerciseSummaryCaloriesBurned) { result ->
            if(sortType == SortType.CALORIES_BURNED) {
                result?.let { exerciseSummary.value = it }
            }
        }
        exerciseSummary.addSource(exerciseSummaryDifficulty) { result ->
            if(sortType == SortType.DIFFICULTY) {
                result?.let { exerciseSummary.value = it }
            }
        }
        exerciseSummary.addSource(exerciseSummaryMaXHearts) { result ->
            if(sortType == SortType.MAX_HEARTS) {
                result?.let { exerciseSummary.value = it }
            }
        }
        exerciseSummary.addSource(exerciseSummaryTotalTime) { result ->
            if(sortType == SortType.TOTAL_TIME) {
                result?.let { exerciseSummary.value = it }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when(sortType) {
        SortType.DATE -> exerciseSummaryDate.value?.let { exerciseSummary.value = it }
        SortType.CALORIES_BURNED -> exerciseSummaryCaloriesBurned.value?.let { exerciseSummary.value = it }
        SortType.DIFFICULTY -> exerciseSummaryDifficulty.value?.let { exerciseSummary.value = it }
        SortType.MAX_HEARTS -> exerciseSummaryMaXHearts.value?.let { exerciseSummary.value = it }
        SortType.TOTAL_TIME -> exerciseSummaryTotalTime.value?.let { exerciseSummary.value = it }
    }.also {
        this.sortType = sortType
    }
}