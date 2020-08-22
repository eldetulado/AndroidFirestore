package com.example.mvvmfirestore

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmfirestore.base.BaseActivity
import com.example.mvvmfirestore.data.network.RepoImpl
import com.example.mvvmfirestore.domain.UseCaseImpl
import com.example.mvvmfirestore.presentation.viewmodel.MainViewModel
import com.example.mvvmfirestore.presentation.viewmodel.MainViewModelFactory
import com.example.mvvmfirestore.vo.Resource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    companion object {
        private val TAG = MainActivity::class.simpleName
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(UseCaseImpl(RepoImpl()))
        ).get(MainViewModel::class.java)
    }

    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeData()

        btnLogin.setOnClickListener {
            viewModel.getData()
        }

        viewModel.showLoadingDialog.observe(this, Observer {
            if (it.first) {
                Log.e(TAG, "onCreate: showing dialog .....")
                showMDialog(it.second)
            } else {
                hideMDialog()
                Log.e(TAG, "onCreate: hide dialog........")
            }
        })

    }

    override fun getViewId(): Int = R.layout.activity_main

    private fun observeData() {
        viewModel.fetchVersionCode.observe(this, Observer {
            when (it) {
                is Resource.Loading -> {
                    showPrgress()
                }
                is Resource.Success -> {
//                    if (appIsOutDated(it.data)) {
//                        showUpdateProgress()
//                    }
                    txt_version.text = it.data.toString()
                    hideProgress()
                }
                is Resource.Failure -> {
                    Toast.makeText(this, "Error ${it.throwable.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showMDialog(message: String) {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(message)
            .setCancelable(false)

        dialog = dialogBuilder.create()
        dialog.setTitle("Atencion")
        dialog.show()
    }

    private fun hideMDialog() {
        dialog.dismiss()
    }

    private fun appIsOutDated(actualVesion: Int): Boolean {
        val currentVersion = BuildConfig.VERSION_CODE
        return currentVersion < actualVesion
    }
}