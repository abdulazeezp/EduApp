package com.example.eduapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.eduapp.firestore.FireStore
import com.example.eduapp.PROFILE_STATUS
import com.example.eduapp.R
import com.example.eduapp.firestore.ProfileClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        FireStore().getUserProfileDetails(this)
        signOutButton.setOnClickListener {
            auth.signOut()
            PROFILE_STATUS =0
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        //loadEngineering()
    }

    fun loadProfile(userProfile:ProfileClass?) {
        if (userProfile != null) {
            nameProfileText.text = userProfile.name
            ageProfileText.text = userProfile.age.toString()
        } else {
            Toast.makeText(this, "No document exist, try again!", Toast.LENGTH_LONG).show()  }
    }

    /*fun loadEngineering() {
        val user = auth.currentUser
        db.collection("users").document(auth.currentUser.toString())
            .collection("engineering").document("equations").set("E=MC2")
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null) {
                    val document=documentSnapshot.toObject(Profile::class.java)
                    nameProfile.text = document?.name
                    ageProfile.text = document?.age.toString()

                } else {
                    Toast.makeText(this, "No document exist, try again!", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Login failed with +${exception}+ try again!", Toast.LENGTH_LONG).show()
            }

        db.collection("users").document(auth.currentUser.toString())
            .collection("engineering").document("equations").get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null) {
                    val document=documentSnapshot.toObject(Profile::class.java)
                    nameProfile.text = document?.name
                    ageProfile.text = document?.age.toString()

                } else {
                    Toast.makeText(this, "No document exist, try again!", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Login failed with +${exception}+ try again!", Toast.LENGTH_LONG).show()
            }
        val engineeringDBRef = userDBRef?.child("engineering")
        engineeringDBRef?.child("equation")?.setValue("E=mc2")
        engineeringDBRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                engineeringText.text = snapshot.child("equation").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Engineering Data Loading Failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }*/
}
    