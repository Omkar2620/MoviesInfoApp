package com.example.moviesinfo.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesinfo.model.Info
import com.example.moviesinfo.other.Resource
import com.example.moviesinfo.repositories.MoviesRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MoviesViewModel(
    val app: Application,
    private val repository: MoviesRepository
) : AndroidViewModel(app) {

    val moviesInfo: MutableLiveData<Resource<Info>> = MutableLiveData()
    var movieResponse: Info? = null

    fun saveMovie(info: Info) =viewModelScope.launch {
        repository.saveMovie(info)
    }

    fun getSavedMovies() = repository.getSavedMovies()

    fun deleteMovie(info: Info) = viewModelScope.launch {
        repository.deleteMovie(info)
    }


    fun getMoviesFromAPI(searchQuery: String) = viewModelScope.launch {
        safeMoviesApiCall(searchQuery)
    }

    private suspend fun safeMoviesApiCall(searchQuery: String){
        try {
            if (hasInternetConnection()){
                val response = repository.getMoviesFromAPI(searchQuery)
                moviesInfo.postValue(handleMoviesResponse(response))
            }else {
                moviesInfo.postValue(Resource.Error("No internet connection"))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> moviesInfo.postValue(Resource.Error("network failure"))
                else -> moviesInfo.postValue(Resource.Error("Sorry :("))
            }
        }
    }

    private fun handleMoviesResponse(response: Response<Info>) : Resource<Info> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                movieResponse = resultResponse
                return Resource.Success(movieResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection():Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork?:return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?: return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}