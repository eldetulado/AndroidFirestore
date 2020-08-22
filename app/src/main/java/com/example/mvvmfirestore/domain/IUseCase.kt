package com.example.mvvmfirestore.domain

import com.example.mvvmfirestore.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IUseCase {
    suspend fun getVersionCode(): Flow<Resource<Int>>
    suspend fun getLastName(name: String): String?
    suspend fun getNameId(name: String): Int?
    suspend fun getDataById(id: Int): String
}