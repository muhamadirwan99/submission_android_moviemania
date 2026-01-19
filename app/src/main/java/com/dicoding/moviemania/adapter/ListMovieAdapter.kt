package com.dicoding.moviemania.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.moviemania.databinding.ItemRowMovieBinding
import com.dicoding.moviemania.model.DataMovie

class ListMovieAdapter (private  val listMovie: ArrayList<DataMovie>) : RecyclerView.Adapter<ListMovieAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setonItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
       val binding = ItemRowMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listMovie[position]
        val photoUrl = "https://image.tmdb.org/t/p/original${data.posterPath}"

        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemTitle.text = data.title
        holder.binding.tvItemDescription.text = data.overview
        holder.binding.tvItemRating.text = data.voteAverage.toString()
        holder.itemView.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition

            if (currentPosition != RecyclerView.NO_POSITION) {
                onItemClickCallback.onItemClicked(listMovie[currentPosition])
            }
        }
    }

    override fun getItemCount(): Int = listMovie.size

    class ListViewHolder(var binding: ItemRowMovieBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: DataMovie)
    }
}