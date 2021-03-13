package com.example.marvelchallenge.app.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelchallenge.R
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {

    private val authContract =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            } else {
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fui_activity_register_email)

        if (Firebase.auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            authContract.launch(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(listOf(AuthUI.IdpConfig.EmailBuilder().build()))
                    .build()
            )
        }
    }
}
