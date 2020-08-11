package com.example.mvvmfirestore.data.network

import com.example.mvvmfirestore.vo.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RepoImpl : IRepo {

    @ExperimentalCoroutinesApi
    override suspend fun getVersionCodeRepo(): Flow<Resource<Int>> = callbackFlow {
        // firebase
        val docRef = FirebaseFirestore.getInstance()
            .collection("params")
            .document("app")

        val subscription = docRef.addSnapshotListener { document, error ->
            if (document!!.exists()) {
                val codeVersion = document.getLong("version")!!.toInt()
                offer(Resource.Success(codeVersion))
            }
        }

        awaitClose { subscription.remove() }
    }
}