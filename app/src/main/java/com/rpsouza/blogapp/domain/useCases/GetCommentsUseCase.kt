package com.rpsouza.blogapp.domain.useCases

import com.google.gson.reflect.TypeToken
import com.rpsouza.blogapp.constants.StorageConstants
import com.rpsouza.blogapp.data.dto.CommentResponseDTO
import com.rpsouza.blogapp.data.dto.PostResponseDTO
import com.rpsouza.blogapp.data.store.SharedPreferenceHelper
import com.rpsouza.blogapp.domain.repository.PostRepository
import com.rpsouza.blogapp.utils.NetworkHelper
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val networkHelper: NetworkHelper
) {
    suspend operator fun invoke(): List<CommentResponseDTO> {
        return if (networkHelper.isNetworkConnected()) {
            try {
                val comments = postRepository.getComments()
                saveComments(comments)
                comments
            } catch (e: Exception) {
                getSavedComments()
            }
        } else {
            getSavedComments()
        }
    }

    private fun saveComments(comments: List<CommentResponseDTO>) {
        sharedPreferenceHelper.saveItemList(comments, StorageConstants.COMMENT_KEY)
    }

    private fun getSavedComments(): List<CommentResponseDTO> {
        val type = object : TypeToken<List<CommentResponseDTO>>() {}.type
        return sharedPreferenceHelper.getItemList(StorageConstants.COMMENT_KEY, type)
    }
}