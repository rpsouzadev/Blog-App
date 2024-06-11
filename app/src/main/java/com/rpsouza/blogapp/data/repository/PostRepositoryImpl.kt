package com.rpsouza.blogapp.data.repository

import com.rpsouza.blogapp.data.api.ServiceAPI
import com.rpsouza.blogapp.data.dto.CommentResponseDTO
import com.rpsouza.blogapp.data.dto.PostResponseDTO
import com.rpsouza.blogapp.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val serviceAPI: ServiceAPI
) : PostRepository {
    override suspend fun getPosts(): List<PostResponseDTO> {
        return serviceAPI.getPosts()
    }

    override suspend fun getComments(): List<CommentResponseDTO> {
        return serviceAPI.getComments()
    }
}