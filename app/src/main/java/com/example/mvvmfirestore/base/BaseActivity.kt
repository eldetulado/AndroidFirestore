package com.example.mvvmfirestore.base

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getViewId())
    }

    protected abstract fun getViewId(): Int

    fun showPrgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    fun showUpdateProgress() {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("Existe una vesion nueva")
            .setCancelable(false)
            .setPositiveButton("Actualizar ahora") { dialog, which ->
                openAppInPlayStore("packagaeNmae")
                dialog.dismiss()
            }
            .setNegativeButton("Ahora no") { dialog, which -> dialog.dismiss() }

        val alert = dialogBuilder.create()
        alert.setTitle("Atencion")
        alert.show()
    }

    private fun openAppInPlayStore(appPackageName: String) {
        try {
            // open in playstore
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(appPackageName)))
        } catch (e: ActivityNotFoundException) {
            // open in browser
        }
    }
}