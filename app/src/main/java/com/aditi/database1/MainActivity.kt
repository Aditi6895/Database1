package com.aditi.database1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signButton= findViewById<Button>(R.id.btnSignUp)
        val etName= findViewById<TextInputEditText>(R.id.etName)
        val etMail = findViewById<TextInputEditText>(R.id.etMail)
        val etuserName = findViewById<TextInputEditText>(R.id.etUserName)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)

        signButton.setOnClickListener {
            val name= etName.text.toString()
            val mail= etMail.text.toString()
            val userName= etuserName.text.toString()
            val password= etPassword.text.toString()

            val user= User(name,mail,userName,password)

            database= FirebaseDatabase.getInstance().getReference("Users")
            database.child(userName).setValue(user).addOnSuccessListener {
                Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT).show()
                etName.setText("")
                etMail.setText("")
                etPassword.setText("")
                etuserName.setText("")
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show()
            }

        }

        val signInText = findViewById<TextView>(R.id.tvSignIn)
        signInText.setOnClickListener {
            val intentSign = Intent(this, SignInActivity::class.java)
            startActivity(intentSign)
        }
    }
}

