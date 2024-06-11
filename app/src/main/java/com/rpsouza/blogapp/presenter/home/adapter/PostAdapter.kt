package com.rpsouza.blogapp.presenter.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rpsouza.blogapp.data.dto.PostResponseDTO
import com.rpsouza.blogapp.databinding.PostCardItemBinding
import com.rpsouza.blogapp.presenter.postDetails.adapter.CommentsAdapter

class PostAdapter(
    private val postDetails: (postTitle: String, postDescription: String) -> Unit
) : ListAdapter<PostResponseDTO, PostAdapter.MyViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostResponseDTO>() {
            override fun areItemsTheSame(
                oldItem: PostResponseDTO,
                newItem: PostResponseDTO
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PostResponseDTO,
                newItem: PostResponseDTO
            ): Boolean {
                return oldItem == newItem
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PostCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = getItem(position)

        holder.binding.textTitle.text = post?.title
        holder.binding.textDescription.text = post?.body

        holder.binding.cardPost.setOnClickListener {
            if (post.title != null && post.body != null) {
                postDetails(post.title, post.body)
            }
        }
    }

    inner class MyViewHolder(val binding: PostCardItemBinding) : RecyclerView.ViewHolder(binding.root)
}