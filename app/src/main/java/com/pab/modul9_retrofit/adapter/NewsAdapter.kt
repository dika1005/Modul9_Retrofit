package com.pab.modul9_retrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pab.modul9_retrofit.R
import com.pab.modul9_retrofit.databinding.ItemNewsBinding
import com.pab.modul9_retrofit.model.NewsPost
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter(
    private var newsList: List<NewsPost>,
    private val onItemClick: (NewsPost) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: NewsPost) {
            binding.tvTitle.text = news.title
            binding.tvDescription.text = news.description
            binding.tvDate.text = formatDate(news.pubDate)

            // Load thumbnail dengan Glide
            Glide.with(binding.root.context)
                .load(news.thumbnail)
                .placeholder(R.drawable.ic_cnn_logo)
                .error(R.drawable.ic_cnn_logo)
                .centerCrop()
                .into(binding.ivThumbnail)

            // Handle click
            binding.root.setOnClickListener {
                onItemClick(news)
            }
        }

        private fun formatDate(dateString: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm WIB", Locale.getDefault())
                val date = inputFormat.parse(dateString)
                date?.let { outputFormat.format(it) } ?: dateString
            } catch (e: Exception) {
                dateString
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    fun updateData(newList: List<NewsPost>) {
        newsList = newList
        notifyDataSetChanged()
    }
}
