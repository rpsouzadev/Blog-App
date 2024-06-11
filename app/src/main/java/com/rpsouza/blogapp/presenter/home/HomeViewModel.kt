package com.rpsouza.blogapp.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.blogapp.domain.useCases.GetPostsUseCase
import com.rpsouza.blogapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel()
class HomeViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    fun getPosts() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val posts = getPostsUseCase()

            emit(StateView.Success(data = posts))
        } catch (ex: HttpException) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }
}