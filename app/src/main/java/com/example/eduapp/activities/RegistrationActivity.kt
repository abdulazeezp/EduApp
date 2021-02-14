package com.example.eduapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eduapp.R
import com.example.eduapp.firestore.FireStore
import com.example.eduapp.firestore.ProfileClass
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.lang.Exception


class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        loginLink.setOnClickListener {
            onBackPressed()
        }
        setUpActionBar()
        register()
    }

    private fun register(){
        registartionButton.setOnClickListener{
            if(TextUtils.isEmpty(emailRegistrationText.text.toString())){
                emailRegistrationText.setError("Plaese Enter Emai ID")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(passwordRegistrationText.text.toString())){
            passwordRegistrationText.setError("Plaese Enter Password")
            return@setOnClickListener
            }

            FireStore().auth.createUserWithEmailAndPassword(emailRegistrationText.text.toString(), passwordRegistrationText.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        var userProfile=ProfileClass()
                        userProfile.currentUserID=FireStore().auth.currentUser!!.uid
                        userProfile.email=FireStore().auth.currentUser!!.email!!
                        FireStore().setRegisterDetails(this@RegistrationActivity,userProfile)
                    }
                    else{
                        Toast.makeText(this, "Registration failed, try again!", Toast.LENGTH_LONG).show()}
                }
        }
    }

    fun onSetRegisterFailed(e:Exception){
        Toast.makeText(this, "Register DB writing failed with execption "+e, Toast.LENGTH_LONG).show()
    }

    private fun setUpActionBar(){
        setSupportActionBar(toolbar_register_activity)
        val actionBar =supportActionBar
        if(actionBar!=null){
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow)}
        toolbar_register_activity.setNavigationOnClickListener{onBackPressed()}
    }
}