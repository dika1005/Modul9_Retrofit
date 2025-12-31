package com.pab.modul9_retrofit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pab.modul9_retrofit.adapter.NewsAdapter
import com.pab.modul9_retrofit.databinding.ActivityMainBinding
import com.pab.modul9_retrofit.model.NewsResponse
import com.pab.modul9_retrofit.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupBottomNavigation()
        fetchNews()
    }

    override fun onResume() {
        super.onResume()
        // Reset bottom nav to news when returning
        binding.bottomNav.selectedItemId = R.id.nav_news
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(emptyList()) { news ->
            // Buka link berita di browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.link))
            startActivity(intent)
        }

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsAdapter
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_news -> {
                    // Already on news, do nothing
                    true
                }
                R.id.nav_anime -> {
                    // Navigate to AnimeActivity
                    val intent = Intent(this, AnimeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun fetchNews() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvNews.visibility = View.GONE

        ApiClient.apiService.getNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                binding.progressBar.visibility = View.GONE
                binding.rvNews.visibility = View.VISIBLE

                if (response.isSuccessful) {
                    response.body()?.let { newsResponse ->
                        if (newsResponse.success) {
                            // Update header title dari API
                            binding.tvHeaderTitle.text = newsResponse.data.title

                            // Update adapter dengan data berita
                            newsAdapter.updateData(newsResponse.data.posts)
                        } else {
                            showErrorDialog("Gagal memuat berita: ${newsResponse.message}")
                        }
                    }
                } else {
                    showErrorDialog("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                binding.rvNews.visibility = View.VISIBLE
                showErrorDialog("An error occurred: ${t.message}")
            }
        })  
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Retry") { _, _ ->
                fetchNews()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}