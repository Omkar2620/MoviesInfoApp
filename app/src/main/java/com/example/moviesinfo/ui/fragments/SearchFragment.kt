package com.example.moviesinfo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviesinfo.R
import com.example.moviesinfo.adapters.MoviesAdapter
import com.example.moviesinfo.model.Info
import com.example.moviesinfo.other.Resource
import com.example.moviesinfo.ui.MainActivity
import com.example.moviesinfo.ui.viewmodels.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {

    lateinit var viewModel: MoviesViewModel
    lateinit var movie: Info

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        var job: Job? = null
        searchBox.visibility = View.INVISIBLE

        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(1000L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.getMoviesFromAPI(editable.toString())
                    }
                }
            }
        }

        searchBox.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("movie", movie)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_infoFragment,
                bundle
            )
        }

        viewModel.moviesInfo.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    searchBox.visibility = View.VISIBLE
                    movie = response.data!!
                    response.data?.let {
                        Glide.with(this).load(response.data.Poster).into(ivPoster)
                        tvTitle.text = response.data.Title
                        tvRating.text = "IMDB Rating: ${response.data.imdbRating}"
                        tvGenre.text = response.data.Genre
                        tvPlot.text = response.data.Plot
                    }
                }
                is Resource.Error -> {
                    searchBox.visibility = View.INVISIBLE
                    response.message?.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occurred $message",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {

                }
            }
        })
    }


}