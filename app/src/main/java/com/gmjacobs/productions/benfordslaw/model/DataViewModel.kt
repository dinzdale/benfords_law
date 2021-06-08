package com.gmjacobs.productions.benfordslaw.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {

    val integerSet = MutableLiveData<ArrayList<Int>>(ArrayList())

    fun addIntegerToSet(newInt:Int) {
        integerSet.value?.apply {
            add(newInt)
            integerSet.postValue(this)
        }
    }

    fun clearIntegerSet() {
        integerSet.postValue(ArrayList())
    }
}