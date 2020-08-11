package com.example.mvvmfirestore.data.network

import com.example.mvvmfirestore.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IRepo {
    suspend fun getVersionCodeRepo(): Flow<Resource<Int>>
}