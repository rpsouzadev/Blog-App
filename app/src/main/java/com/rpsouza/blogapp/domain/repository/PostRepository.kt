package com.rpsouza.blogapp.domain.repository

import com.rpsouza.blogapp.data.dto.CommentResponseDTO
import com.rpsouza.blogapp.data.dto.PostResponseDTO
import retrofit2.http.GET

interface PostRepository {
    suspend fun getPosts(): List<PostResponseDTO>

    suspend fun getComments(): List<CommentResponseDTO>
}