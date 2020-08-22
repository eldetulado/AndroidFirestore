package com.example.mvvmfirestore.data.network

import com.example.mvvmfirestore.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IRepo {
    suspend fun getVersionCodeRepo(): Flow<Resource<Int>>
    suspend fun getLastName(name: String): String?
    suspend fun getNameId(name: String): Int?
    suspend fun getDataById(id: Int): String
}