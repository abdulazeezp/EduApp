package com.example.eduapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.eduapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_registration.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        //setUpActionBar()
        resetPassword()
    }
    private fun setUpActionBar(){
        setSupportActionBar(toolbar_register_activity)
        val actionBar =supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.back_arrow)}
        toolbar_register_activity.setNavigationOnClickListener{onBackPressed()}
    }
    private fun resetPassword(){
        forgotPasswordButton.setOnClickListener{
            val email= emailForgotPassword.text.toString().trim{
                it<=' '   }
            if(email.isEmpty()){
                Toast.makeText(this, "Please Enter valid Email ID", Toast.LENGTH_LONG).show()
            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(this, "Email sent Successfully to reset your password.", Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(this, "Email reset failed with"+ it.exception!!.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}