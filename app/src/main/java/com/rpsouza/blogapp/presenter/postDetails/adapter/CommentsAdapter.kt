package com.rpsouza.blogapp.presenter.postDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rpsouza.blogapp.data.dto.CommentResponseDTO
import com.rpsouza.blogapp.databinding.CommentItemBinding
import com.rpsouza.blogapp.databinding.PostCardItemBinding

class CommentsAdapter() : ListAdapter<CommentResponseDTO, CommentsAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommentResponseDTO>() {
            override fun areItemsTheSame(
                oldItem: CommentResponseDTO,
                newItem: CommentResponseDTO
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CommentResponseDTO,
                newItem: CommentResponseDTO
            ): Boolean {
                return oldItem == newItem
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CommentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val comment = getItem(position)

        holder.binding.textUsername.text = comment.name
        holder.binding.textEmail.text = comment.email
        holder.binding.textDescription.text = comment.body
    }

    inner class MyViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)
}