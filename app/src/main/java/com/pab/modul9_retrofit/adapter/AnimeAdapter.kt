package com.pab.modul9_retrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pab.modul9_retrofit.databinding.ItemAnimeBinding
import com.pab.modul9_retrofit.model.Anime

class AnimeAdapter(
    private var animeList: List<Anime>,
    private val onItemClick: (Anime) -> Unit
) : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: Anime, position: Int) {
            binding.tvAnimeNumber.text = "${position + 1}"
            binding.tvAnimeTitle.text = anime.judul

            binding.root.setOnClickListener {
                onItemClick(anime)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animeList[position], position)
    }

    override fun getItemCount(): Int = animeList.size

    fun updateData(newList: List<Anime>) {
        animeList = newList
        notifyDataSetChanged()
    }
}
