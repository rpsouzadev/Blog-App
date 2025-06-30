package com.rpsouza.blogapp.presenter.postDetails

import com.rpsouza.blogapp.data.dto.CommentResponseDTO

sealed class PostDetailsUiState {
    object Loading : PostDetailsUiState()
    data class Success(val comments: List<CommentResponseDTO>) : PostDetailsUiState()
    data class Error(val message: String) : PostDetailsUiState()
}