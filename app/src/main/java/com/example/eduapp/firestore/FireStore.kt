package com.example.eduapp.firestore

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.eduapp.Constants
import com.example.eduapp.activities.LoginActivity
import com.example.eduapp.activities.MainActivity
import com.example.eduapp.activities.ProfileActivity
import com.example.eduapp.activities.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_registration.*

class FireStore{
    val auth=FirebaseAuth.getInstance()
    val mFireStore= FirebaseFirestore.getInstance()
    fun setRegisterDetails(activity: RegistrationActivity,userInfo:ProfileClass){
        val sharedPreference =activity.getSharedPreferences(Constants.EDUAPP_PREFERENCES,MODE_PRIVATE)
        val editor= sharedPreference.edit()
        editor.putString(Constants.LOGGEDIN_USERNAME, userInfo.name).apply()
        mFireStore.collection(Constants.USERS).document(userInfo.currentUserID).set(userInfo, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                activity.onBackPressed()
            }
            .addOnFailureListener { e ->
                activity.onSetRegisterFailed(e)
            }
    }

    fun setProfileUpdate(activity: ProfileActivity,userInfo:ProfileClass){
        mFireStore.collection(Constants.USERS).document(userInfo.currentUserID).set(userInfo, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                activity.profileUpdationSuccess()
            }
            .addOnFailureListener { e ->
                activity.profileUpdationFailed(e)
            }
    }

    fun getCurrentUserID():String{
        var currentUserID= FirebaseAuth.getInstance().currentUser?.uid
        if(currentUserID==null)
            currentUserID=""
        return currentUserID
    }

    fun getUserProfileDetails(activity: Activity){
        mFireStore.collection(Constants.USERS).document(getCurrentUserID()).get().addOnSuccessListener {documentSnapshot->
            val user= documentSnapshot.toObject(ProfileClass::class.java)
            when(activity){
                is MainActivity->{
                    activity.loadProfile(user)
                }
                is LoginActivity->{
                    activity.loginSuccessfull(user!!)
                }
            }

        }.addOnFailureListener { exception ->
            Toast.makeText(activity, "Document could not read as +${exception}+ try again!", Toast.LENGTH_LONG).show()
        }
    }



}