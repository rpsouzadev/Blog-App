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
        initConfigData()
        initRecycler()
        getComments()
    }

    private fun initConfigData() {
        binding.textTitle.text = args.postTitle
        binding.textDescription.text = args.postDescription
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

    private fun getComments() {
        postDetailsViewModel.getComments().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is StateView.Success -> {
                    binding.progressBar.isVisible = false

                    if (stateView.data?.isNotEmpty() == true) {
                        commentsAdapter.submitList(stateView.data)
                    } else {
                        binding.textEmptyList.isVisible = true
                    }
                }
                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}