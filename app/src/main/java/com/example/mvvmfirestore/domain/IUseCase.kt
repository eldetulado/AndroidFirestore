package com.example.mvvmfirestore.domain

import com.example.mvvmfirestore.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IUseCase {
    suspend fun getVersionCode(): Flow<Resource<Int>>
}