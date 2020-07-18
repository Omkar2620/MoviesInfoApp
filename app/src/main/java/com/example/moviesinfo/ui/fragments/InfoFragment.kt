package com.example.moviesinfo.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesinfo.R
import com.example.moviesinfo.ui.MainActivity
import com.example.moviesinfo.ui.viewmodels.MoviesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : Fragment(R.layout.fragment_info) {

    lateinit var viewModel: MoviesViewModel
    val args: InfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val movie = args.movie
        fabSave.setImageResource(R.drawable.ic_fab_fav)

        tvTitle.text = "${movie.Title} (${movie.Year})"
        tvRating.text = "IMDB: ${movie.imdbRating} "
        Glide.with(this).load(movie.Poster).into(ivPoster)
        tvRuntime.text = movie.Runtime
        tvGenre.text = movie.Genre
        tvPlot.text = movie.Plot
        tvActors.text = movie.Actors
        tvDirector.text = movie.Director
        tvLanguage.text = "Language: ${movie.Language}"
        tvBoxOffice.text = "Box Office: ${movie.BoxOffice}"
        tvWriters.text = movie.Writer
        tvAwards.text = movie.Awards

        fabSave.setOnClickListener {
            viewModel.saveMovie(movie)
            Snackbar.make(view,"Movie saved to favourites",Snackbar.LENGTH_LONG)
                .setAction("UNDO"){
                    viewModel.deleteMovie(movie)
                }
                .show()
        }

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}