package com.aditi.database1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    companion object {
        const val KEY2 = "com.example.database1.SignInActivity.name"
        const val KEY1 = "com.example.database1.SignInActivity.mail"
        const val KEY3 = "com.example.database1.SignInActivity.usename"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signInButton =findViewById<Button>(R.id.btnSignIn)
        val userName= findViewById<TextInputEditText>(R.id.userNameEditText)

        signInButton.setOnClickListener {
            val uniqueId= userName.text.toString()
            if(uniqueId.isNotEmpty()) {
                readData(uniqueId)
            } else {
                Toast.makeText(this,"Please enter user name", Toast.LENGTH_SHORT).show()
            }
        }
    } //onCreate method over

    private fun readData(uniqueId: String) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(uniqueId).get().addOnSuccessListener {
            if(it.exists()){
                val name= it.child("name").value
                val email= it.child("email").value
                val uniqueId= it.child("uniqueId").value

                val intentWelcome = Intent(this, Welcome::class.java)
                intentWelcome.putExtra(KEY2, name.toString())
                intentWelcome.putExtra(KEY1, email.toString())
                intentWelcome.putExtra(KEY3, uniqueId.toString())
                startActivity(intentWelcome)

            } else {
                Toast.makeText(this,"User does not exist", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener {
            Toast.makeText(this,"Failed, Error in DB", Toast.LENGTH_SHORT).show()
        }

    }
}
