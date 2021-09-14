package com.andreyyurko.firstapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration





class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = findViewById<RecyclerView>(R.id.usersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = UserAdapter()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
        adapter.userList = loadUsers()
        adapter.notifyDataSetChanged()
    }
    private fun loadUsers() : List<User> {
        return listOf(
            User(
                avatarUrl = "",
                userName = "User name 1",
                groupName = "A"
            ),
            User(
                avatarUrl = "",
                userName = "User name 2",
                groupName = "B"
            ),
            User(
                avatarUrl = "",
                userName = "User name 3",
                groupName = "C"
            ),
            User(
                avatarUrl = "",
                userName = "User name 4",
                groupName = "A"
            ),
            User(
                avatarUrl = "",
                userName = "User name 5",
                groupName = "B"
            ),
            User(
                avatarUrl = "",
                userName = "User name 6",
                groupName = "C"
            ),
            User(
                avatarUrl = "",
                userName = "User name 7",
                groupName = "A"
            ),
            User(
                avatarUrl = "",
                userName = "User name 8",
                groupName = "B"
            ),
            User(
                avatarUrl = "",
                userName = "User name 9",
                groupName = "C"
            ),
            User(
                avatarUrl = "",
                userName = "User name 10",
                groupName = "A"
            ),
        )
    }
}