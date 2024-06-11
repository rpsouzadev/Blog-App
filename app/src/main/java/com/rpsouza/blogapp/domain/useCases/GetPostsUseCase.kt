package com.rpsouza.blogapp.domain.useCases

import com.rpsouza.blogapp.data.dto.PostResponseDTO
import com.rpsouza.blogapp.domain.repository.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(): List<PostResponseDTO> {
        return postRepository.getPosts()
    }
}