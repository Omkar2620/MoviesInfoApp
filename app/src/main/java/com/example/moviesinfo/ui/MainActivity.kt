package com.example.moviesinfo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviesinfo.R
import com.example.moviesinfo.db.InfoDatabase
import com.example.moviesinfo.repositories.MoviesRepository
import com.example.moviesinfo.ui.viewmodels.MoviesViewModel
import com.example.moviesinfo.ui.viewmodels.MoviesViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moviesRepository = MoviesRepository(InfoDatabase(this))
        val viewModelProviderFactory = MoviesViewModelProviderFactory(application,moviesRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(MoviesViewModel::class.java)

        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
    }
}