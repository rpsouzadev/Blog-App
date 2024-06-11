package com.rpsouza.blogapp.domain.useCases

import com.google.gson.reflect.TypeToken
import com.rpsouza.blogapp.constants.StorageConstants
import com.rpsouza.blogapp.data.dto.PostResponseDTO
import com.rpsouza.blogapp.data.store.SharedPreferenceHelper
import com.rpsouza.blogapp.domain.repository.PostRepository
import com.rpsouza.blogapp.utils.NetworkHelper
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val sharedPreferenceHelper: SharedPreferenceHelper,
    private val networkHelper: NetworkHelper
) {
    suspend operator fun invoke(): List<PostResponseDTO> {
        return if (networkHelper.isNetworkConnected()) {
            try {
                val posts = postRepository.getPosts()
                savePosts(posts)
                posts
            } catch (e: Exception) {
                getSavedPosts()
            }
        } else {
            getSavedPosts()
        }
    }

    private fun savePosts(posts: List<PostResponseDTO>) {
        sharedPreferenceHelper.saveItemList(posts, StorageConstants.POST_KEY)
    }

    private fun getSavedPosts(): List<PostResponseDTO> {
        val type = object : TypeToken<List<PostResponseDTO>>() {}.type
        return sharedPreferenceHelper.getItemList(StorageConstants.POST_KEY, type)
    }
}