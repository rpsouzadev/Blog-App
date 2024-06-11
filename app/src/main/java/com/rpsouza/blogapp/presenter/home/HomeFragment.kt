package com.rpsouza.blogapp.presenter.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rpsouza.blogapp.databinding.FragmentHomeBinding
import com.rpsouza.blogapp.presenter.home.adapter.PostAdapter
import com.rpsouza.blogapp.presenter.postDetails.adapter.CommentsAdapter
import com.rpsouza.blogapp.utils.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var postAdapter: PostAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        getPosts()
    }

    private fun getPosts() {
        homeViewModel.getPosts().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    if (stateView.data?.isNotEmpty() == true) {
                        postAdapter.submitList(stateView.data)
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

    private fun initRecycler() {
        postAdapter = PostAdapter { postTitle, postDescription ->
            val action = HomeFragmentDirections.actionHomeFragmentToPostDetailsFragment(
                postTitle = postTitle,
                postDescription = postDescription,
            )

            findNavController().navigate(action)
        }

        with(binding.recyclerPost) {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
            adapter = postAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}