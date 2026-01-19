package com.dicoding.moviemania

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.moviemania.adapter.ListMovieAdapter
import com.dicoding.moviemania.databinding.ActivityMainBinding
import com.dicoding.moviemania.model.DataMovie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val list = ArrayList<DataMovie>()
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvMovie.setHasFixedSize(true)
        list.addAll(getListMovies())
        showRecyclerList()
    }

    private fun getListMovies(): ArrayList<DataMovie>{
        val jsonString: String
        try {
            jsonString = assets.open("dummy_data.json").bufferedReader().use { it.readText() }
        }catch (ioException: IOException){
            ioException.printStackTrace()
            return arrayListOf()
        }

        val listMovieType = object : TypeToken<ArrayList<DataMovie>>() {}.type
        return Gson().fromJson(jsonString, listMovieType)
    }

    private fun showRecyclerList(){
        binding.rvMovie.layoutManager = LinearLayoutManager(this)
        val listMovieAdapter = ListMovieAdapter(list)
        binding.rvMovie.adapter = listMovieAdapter

        listMovieAdapter.setonItemClickCallback(object : ListMovieAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DataMovie) {
                TODO("Not yet implemented")
            }
        })
    }
}