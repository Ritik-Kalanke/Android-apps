package com.example.volleyapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context: Context, val userList: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // Create and inflate the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // Inflate the item layout
        val view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        // Get the user item at the current position
        val user = userList[position]

        // Set the user name to the TextView in the ViewHolder
        holder.textName.text = user.name
    }

    // Return the total number of items in the list
    override fun getItemCount(): Int {
        return userList.size
    }

    // Define the ViewHolder class
    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textName: TextView = itemView.findViewById(R.id.txtname)
    }
}