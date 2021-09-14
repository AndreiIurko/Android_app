package com.andreyyurko.firstapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = findViewById<RecyclerView>(R.id.usersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = UserAdapter()
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL)
        val divider_image = resources.getDrawable(R.drawable.divider, theme)
        dividerItemDecoration.setDrawable(divider_image)
        recyclerView.addItemDecoration(CustomPositionItemDecoration(divider_image))
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