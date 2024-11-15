package com.example.volleyapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class Signup : AppCompatActivity() {

    // Initialize Firebase Firestore
    private var db = Firebase.firestore
    // Initialize Firebase Auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Find views
        val emailSignUp = findViewById<EditText>(R.id.emailSignUp)
        val passwordSignUp = findViewById<EditText>(R.id.passwordSignUp)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val loginLink = findViewById<TextView>(R.id.loginLink)
        val nameSignUp = findViewById<EditText>(R.id.nameSignUp)

        // Set up click listener for sign-up button
        signUpButton.setOnClickListener {
            val name = nameSignUp.text.toString()
            val email = emailSignUp.text.toString()
            val password = passwordSignUp.text.toString()

            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                signup(name, email, password)
            }
        }

        // Set up click listener for login link
        loginLink.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    // Function to handle sign-up logic
    private fun signup(name: String, email: String, password: String) {
        // Create a new user with Firebase Auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    user?.let {
                        addUserToDatabase(name, email, it.uid, password)
                        Toast.makeText(this, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Sign-up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Function to add user to Firestore database
    private fun addUserToDatabase(name: String, email: String, uid: String, password: String) {
        val user = User(name, email, uid, password)

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("Signup", "User added to Firestore")
            }
            .addOnFailureListener { e ->
                Log.w("Signup", "Error adding user to Firestore", e)
            }
    }
}




