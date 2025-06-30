package com.rpsouza.blogapp.presenter.postDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rpsouza.blogapp.domain.useCases.GetCommentsUseCase
import com.rpsouza.blogapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData<PostDetailsUiState>()
    val uiState: LiveData<PostDetailsUiState> = _uiState

    fun onAction(action: PostDetailsAction) {
        when (action) {
            is PostDetailsAction.LoadPostDetails -> fetchPostDetails(action.postId)
            is PostDetailsAction.RefreshComments -> refreshComments()
        }
    }

    private fun fetchPostDetails(postId: Int) {
        viewModelScope.launch {
            _uiState.value = PostDetailsUiState.Loading
            try {
                val comments = getCommentsUseCase()
                _uiState.value = PostDetailsUiState.Success(comments)
            } catch (e: Exception) {
                _uiState.value = PostDetailsUiState.Error("Erro ao carregar comentários")
            }
        }
    }

    private fun refreshComments() {
        viewModelScope.launch {
            _uiState.value = PostDetailsUiState.Loading

            try {
                val comments = getCommentsUseCase()
                _uiState.value = PostDetailsUiState.Success(comments)
            } catch (e: Exception) {
                _uiState.value = PostDetailsUiState.Error("Erro ao atualizar comentários")
            }
        }
    }
}