package com.andreyyurko.firstapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
//import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreyyurko.firstapp.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = setupRecyclerView()
        
        findViewById<View>(R.id.usersRecyclerView).isVisible = false
        findViewById<View>(R.id.progressBar).isVisible = true

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.userList = loadUsers()
                adapter.notifyDataSetChanged()
                findViewById<View>(R.id.usersRecyclerView).isVisible = true
                findViewById<View>(R.id.progressBar).isVisible = false
            }
        }
    }

    private fun setupRecyclerView(): UserAdapter {
        val recyclerView = findViewById<RecyclerView>(R.id.usersRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = UserAdapter()
        recyclerView.adapter = adapter
        val dividerImage = resources.getDrawable(R.drawable.divider, theme)
        recyclerView.addItemDecoration(CustomPositionItemDecoration(dividerImage))
        return adapter
    }

}