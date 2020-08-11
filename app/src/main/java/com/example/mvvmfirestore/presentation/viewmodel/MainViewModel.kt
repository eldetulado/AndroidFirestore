package com.example.mvvmfirestore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.mvvmfirestore.domain.IUseCase
import com.example.mvvmfirestore.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class MainViewModel(useCase: IUseCase) : ViewModel() {
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
}