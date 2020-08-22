package com.example.mvvmfirestore.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.mvvmfirestore.domain.IUseCase
import com.example.mvvmfirestore.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val useCase: IUseCase) : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.simpleName
    }

    private val _showLoadingDialog = MutableLiveData<Pair<Boolean, String>>()
    val showLoadingDialog: LiveData<Pair<Boolean, String>> get() = _showLoadingDialog

    val fetchVersionCode = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            useCase.getVersionCode().collect {
                emit(it)
            }
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun getData() {
        val start = System.currentTimeMillis()

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _showLoadingDialog.value = Pair(true, "Get last name")
            }

            val lastName = useCase.getLastName("oso")
            Log.d(TAG, "getData: $lastName")

            withContext(Dispatchers.Main) {
                _showLoadingDialog.value = Pair(false, "")
                _showLoadingDialog.value = Pair(true, "async method")
            }


            val id11 = async { useCase.getNameId("oso") }
            val id22 = async { useCase.getNameId("osos") }

            val id1 = id11.await()
            val id2 = id22.await()

            withContext(Dispatchers.Main) {
                _showLoadingDialog.value = Pair(false, "")
                _showLoadingDialog.value = Pair(true, "Get data")
            }

            Log.d(TAG, "getData: $id1 === $id2")
            if (id1 != null) {
                val resp = useCase.getDataById(id1)
                Log.d(TAG, "getData: $resp")
            }
            if (id2 != null) {
                val resp = useCase.getDataById(id2)
                Log.d(TAG, "getData: $resp")
            }
            withContext(Dispatchers.Main) {
                _showLoadingDialog.value = Pair(false, "")
            }
            val end = System.currentTimeMillis()
            val res = end - start
            Log.e(TAG, "TIME RESULT $res")
        }


    }
}