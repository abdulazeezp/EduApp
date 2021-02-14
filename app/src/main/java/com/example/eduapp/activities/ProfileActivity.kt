package com.example.eduapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.eduapp.R
import com.example.eduapp.firestore.FireStore
import com.example.eduapp.firestore.ProfileClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.ageProfile
import kotlinx.android.synthetic.main.activity_profile.nameProfile

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        updateProfile.setOnClickListener{
            profileUpadate()
        }
    }
    private fun profileUpadate() {
        val userProfile=ProfileClass()
        userProfile.currentUserID=FireStore().auth.currentUser?.uid!!
        userProfile.name=nameProfile.text.toString()
        userProfile.email=FireStore().auth.currentUser?.email!!
        userProfile.age=Integer.valueOf(ageProfile.text.toString())
        userProfile.mobile_no=Integer.valueOf(mobileProfile.text.toString())
        userProfile.PROFILE_UPDATED=1
        FireStore().setProfileUpdate(this@ProfileActivity, userProfile)
    }

    fun profileUpdationSuccess() {
        Toast.makeText(this, "Profile updation successful!..", Toast.LENGTH_LONG).show()
        startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
        finish()
    }
    fun profileUpdationFailed(e:Exception) {
        Toast.makeText(this, "Profile updation failed.."+e, Toast.LENGTH_LONG).show()
    }

}
