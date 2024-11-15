package com.example.volleyapi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var userrecyclerview: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private val db = com.google.firebase.Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        userList = ArrayList()
        adapter = UserAdapter(this, userList)
        userrecyclerview = findViewById(R.id.recyclerView)
        userrecyclerview.layoutManager = LinearLayoutManager(this)
        userrecyclerview.adapter = adapter

        fetchUsers()
    }

    private fun fetchUsers() {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val initialSize = userList.size
                userList.clear()

                for (document in result) {
                    val user = document.toObject(User::class.java)
                    userList.add(user)
                }

                when {
                    initialSize == 0 -> adapter.notifyItemRangeInserted(0, userList.size)
                    userList.size > initialSize -> adapter.notifyItemRangeInserted(initialSize, userList.size - initialSize)
                    userList.size < initialSize -> adapter.notifyItemRangeRemoved(userList.size, initialSize - userList.size)
                    else -> adapter.notifyItemRangeChanged(0, userList.size)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting users: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }


}


