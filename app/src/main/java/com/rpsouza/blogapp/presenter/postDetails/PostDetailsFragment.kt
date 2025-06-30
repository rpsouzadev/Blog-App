package com.rpsouza.blogapp.presenter.postDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.rpsouza.blogapp.databinding.FragmentPostDetailsBinding
import com.rpsouza.blogapp.presenter.home.HomeFragmentDirections
import com.rpsouza.blogapp.presenter.home.adapter.PostAdapter
import com.rpsouza.blogapp.presenter.postDetails.adapter.CommentsAdapter
import com.rpsouza.blogapp.utils.StateView
import com.rpsouza.blogapp.utils.initToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {
    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!

    private val postDetailsViewModel: PostDetailsViewModel by viewModels()
    private val args: PostDetailsFragmentArgs by navArgs()
    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(toolbar = binding.toolbar)
        setupView()
        initRecycler()
        postDetailsViewModel.onAction(PostDetailsAction.LoadPostDetails(postId = 10))
        setupObservers()
    }

    private fun initRecycler() {
        commentsAdapter = CommentsAdapter()

        with(binding.recyclerComment) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = commentsAdapter
        }
    }

    private fun setupObservers() {
        postDetailsViewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is PostDetailsUiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is PostDetailsUiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    commentsAdapter.submitList(state.comments)
                }

                is PostDetailsUiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView() {
        binding.textTitle.text = args.postTitle
        binding.textDescription.text = args.postDescription
        binding.btnReloadComment.setOnClickListener {
            postDetailsViewModel.onAction(PostDetailsAction.RefreshComments)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}