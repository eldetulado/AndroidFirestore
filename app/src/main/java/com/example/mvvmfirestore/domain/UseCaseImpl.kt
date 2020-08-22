package com.example.mvvmfirestore.domain

import com.example.mvvmfirestore.data.network.IRepo
import com.example.mvvmfirestore.vo.Resource
import kotlinx.coroutines.flow.Flow


class UseCaseImpl(private val repo: IRepo) : IUseCase {
    override suspend fun getVersionCode(): Flow<Resource<Int>> {
        return repo.getVersionCodeRepo()
    }

    override suspend fun getLastName(name: String): String? {
        return repo.getLastName(name)
    }

    override suspend fun getNameId(name: String): Int? {
        return repo.getNameId(name)
    }

    override suspend fun getDataById(id: Int): String {
        return repo.getDataById(id)
    }
}