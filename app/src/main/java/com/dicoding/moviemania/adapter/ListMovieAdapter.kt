package com.dicoding.moviemania.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
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
        holder.binding.shimmerViewContainer.startShimmer()
        holder.binding.shimmerViewContainer.visibility = View.VISIBLE
        holder.binding.imgItemPhoto.visibility = View.INVISIBLE

        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.binding.shimmerViewContainer.stopShimmer()
                    holder.binding.shimmerViewContainer.visibility = View.GONE
                    holder.  binding.imgItemPhoto.visibility = View.VISIBLE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder. binding.shimmerViewContainer.stopShimmer()
                    holder. binding.shimmerViewContainer.visibility = View.GONE

                    holder. binding.imgItemPhoto.visibility = View.VISIBLE
                    return false
                }
            })
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