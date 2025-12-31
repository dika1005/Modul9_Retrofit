package com.pab.modul9_retrofit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.pab.modul9_retrofit.adapter.AnimeAdapter
import com.pab.modul9_retrofit.databinding.ActivityAnimeBinding
import com.pab.modul9_retrofit.model.Anime
import com.pab.modul9_retrofit.model.AnimeScheduleItem
import com.pab.modul9_retrofit.network.AnimeApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimeBinding
    private lateinit var animeAdapter: AnimeAdapter
    private var scheduleData: List<AnimeScheduleItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupRecyclerView()
        setupTabListener()
        fetchAnimeSchedule()
    }

    private fun setupViews() {
        // Back button
        binding.ivBack.setOnClickListener {
            finish()
        }

        // Search button (placeholder for now)


        // Retry button
        binding.btnRetry.setOnClickListener {
            fetchAnimeSchedule()
        }
    }

    private fun setupRecyclerView() {
        animeAdapter = AnimeAdapter(emptyList()) { anime ->
            // Handle click - show toast with anime info
            Toast.makeText(this, "Dipilih: ${anime.judul}", Toast.LENGTH_SHORT).show()
        }

        binding.rvAnime.apply {
            layoutManager = LinearLayoutManager(this@AnimeActivity)
            adapter = animeAdapter
        }
    }

    private fun setupTabListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    val position = it.position
                    if (position < scheduleData.size) {
                        updateAnimeList(scheduleData[position].anime)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun fetchAnimeSchedule() {
        showLoading()

        AnimeApiClient.apiService.getAnimeSchedule().enqueue(object : Callback<List<AnimeScheduleItem>> {
            override fun onResponse(
                call: Call<List<AnimeScheduleItem>>,
                response: Response<List<AnimeScheduleItem>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        scheduleData = data
                        setupTabs(data)
                        
                        // Show first day's anime by default
                        if (data.isNotEmpty()) {
                            updateAnimeList(data[0].anime)
                        }
                        showContent()
                    } ?: showError("Data kosong")
                } else {
                    showError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<AnimeScheduleItem>>, t: Throwable) {
                showError("Gagal memuat: ${t.message}")
            }
        })
    }

    private fun setupTabs(scheduleData: List<AnimeScheduleItem>) {
        binding.tabLayout.removeAllTabs()
        
        scheduleData.forEach { schedule ->
            val tab = binding.tabLayout.newTab()
            tab.text = schedule.hari
            binding.tabLayout.addTab(tab)
        }
    }

    private fun updateAnimeList(animeList: List<Anime>) {
        animeAdapter.updateData(animeList)
        
        // Scroll to top
        binding.rvAnime.scrollToPosition(0)
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvAnime.visibility = View.GONE
        binding.errorView.visibility = View.GONE
    }

    private fun showContent() {
        binding.progressBar.visibility = View.GONE
        binding.rvAnime.visibility = View.VISIBLE
        binding.errorView.visibility = View.GONE
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.rvAnime.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
        binding.tvError.text = message
    }
}
