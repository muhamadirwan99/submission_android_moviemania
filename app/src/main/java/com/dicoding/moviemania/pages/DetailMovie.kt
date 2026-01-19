package com.dicoding.moviemania.pages

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.dicoding.moviemania.R
import com.dicoding.moviemania.databinding.ActivityDetailMovieBinding
import com.dicoding.moviemania.model.DataMovie
import com.dicoding.moviemania.utils.toCompactNumber
import com.dicoding.moviemania.utils.withDateFormat

class DetailMovie : AppCompatActivity() {
    private lateinit var binding : ActivityDetailMovieBinding

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnBack.setOnClickListener{
           onBackPressedDispatcher.onBackPressed();
        }

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<DataMovie>(EXTRA_MOVIE, DataMovie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<DataMovie>(EXTRA_MOVIE)
        }

        if (movie != null){
            binding.tvTitle.text = movie.title
            binding.tvDate.text = movie.releaseDate.withDateFormat()
            binding.tvRate.text = movie.voteAverage.toString()
            binding.tvPopularity.text = movie.popularity.toCompactNumber()
            binding.tvDescription.text = movie.overview
            binding.tvVoters.text =  this.getString(
                R.string.format_vote_count,
                movie.voteCount.toCompactNumber()
            )

            val photoUrl = "https://image.tmdb.org/t/p/original${movie.backdropPath}"
            binding.shimmerViewContainer.startShimmer()
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.imageMovie.visibility = View.INVISIBLE

            Glide.with(this)
                .load(photoUrl)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.shimmerViewContainer.stopShimmer()
                        binding.shimmerViewContainer.visibility = View.GONE
                          binding.imageMovie.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                         binding.shimmerViewContainer.stopShimmer()
                         binding.shimmerViewContainer.visibility = View.GONE

                         binding.imageMovie.visibility = View.VISIBLE
                        return false
                    }
                })
                .into(binding.imageMovie)
        }
    }
}