package com.rpsouza.blogapp.data.api

import com.rpsouza.blogapp.data.dto.CommentResponseDTO
import com.rpsouza.blogapp.data.dto.PostResponseDTO
import retrofit2.http.GET

interface ServiceAPI {

    @GET("posts")
    suspend fun getPosts(): List<PostResponseDTO>

    @GET("comments")
    suspend fun getComments(): List<CommentResponseDTO>
}