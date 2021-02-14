package com.example.eduapp.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.eduapp.*
import com.example.eduapp.firestore.FireStore
import com.example.eduapp.firestore.ProfileClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*

class LoginActivity : AppCompatActivity() {
    //lateinit var auth: FirebaseAuth
    lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //auth=FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        if(FireStore().auth.currentUser!=null){
            startActivity(Intent( this, MainActivity::class.java))
            finish()
        }
        login()
    }
    private fun login() {
        loginButton.setOnClickListener {
            if (TextUtils.isEmpty(emailLoginText.text.toString())) {
                emailLoginText.setError("Please enter UserID/Email")
                return@setOnClickListener}
            else if (TextUtils.isEmpty(passwordLoginText.text.toString())) {
                emailLoginText.setError("Please enter Password")
                return@setOnClickListener   }
            FireStore().auth.signInWithEmailAndPassword(emailLoginText.text.toString(),passwordLoginText.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        FireStore().getUserProfileDetails(this@LoginActivity)// Call back login success full function
                    }
                    else { Toast.makeText(this, "Login failed, try again!", Toast.LENGTH_LONG).show() }
                }
        }
        registerLink.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }
        forgotPasswordLink.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
            finish()
        }
    }

    fun loginSuccessfull(userProfile:ProfileClass) {
        if (userProfile.PROFILE_UPDATED==1)
            startActivity(Intent(this, MainActivity::class.java))
        else
            startActivity(Intent(this, ProfileActivity::class.java))
        finish()
    }

}
