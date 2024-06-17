package com.example.appmusickotlin.test.viewmodel_livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewViewModel : ViewModel() {
    private val scoreB = MutableLiveData(0)
    val scoreA : LiveData<Int> get() =  scoreB

    fun incremantScore(add : Int) {
        scoreB.value = add + (scoreB.value ?: 0)
    }

}