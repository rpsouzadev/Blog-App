package com.rpsouza.blogapp.presenter.postDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rpsouza.blogapp.domain.useCases.GetCommentsUseCase
import com.rpsouza.blogapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    fun getComments() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val comment = getCommentsUseCase()

            emit(StateView.Success(data = comment))
        } catch (ex: HttpException) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }
}