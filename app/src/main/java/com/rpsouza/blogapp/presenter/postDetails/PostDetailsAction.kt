package com.rpsouza.blogapp.presenter.postDetails

sealed class PostDetailsAction {
    data class LoadPostDetails(val postId: Int) : PostDetailsAction()
    object RefreshComments : PostDetailsAction()
}