package com.example.moviesinfo.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesinfo.repositories.MoviesRepository

class MoviesViewModelProviderFactory(
    val app: Application,
    private val repository: MoviesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesViewModel(app,repository) as T
    }
}