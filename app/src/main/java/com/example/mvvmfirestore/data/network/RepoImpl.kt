package com.example.mvvmfirestore.data.network

import android.util.Log
import com.example.mvvmfirestore.vo.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RepoImpl : IRepo {

    companion object {
        private val TAG = RepoImpl::class.simpleName
    }

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

    override suspend fun getNameId(name: String): Int? {
        Log.d(TAG, "getNameId: ")
        delay(3000)
        if (name == "oso")
            return 1
        return null
    }

    override suspend fun getLastName(name: String): String? {
        Log.d(TAG, "getLastName: ")
        delay(5000)
        if (name == "oso")
            return "el grande"
        return "default"
    }

    override suspend fun getDataById(id: Int): String {
        Log.d(TAG, "getDataById: ")
        if (id == 1)
            return "oso el grande"
        return "default personaje"
    }
}