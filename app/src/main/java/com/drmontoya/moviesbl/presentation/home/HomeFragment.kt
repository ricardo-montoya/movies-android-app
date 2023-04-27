package com.drmontoya.moviesbl.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.drmontoya.moviesbl.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private val navToDetail: (id: Int) -> Unit = { id ->
        this.findNavController()
            .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
    }
    private val topRankedListAdapter = MovieListAdapter(navToDetail, MovieViewType.TopRated)
    private val nowPlayingListAdapter = MovieListAdapter(navToDetail, MovieViewType.NowPlaying)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupUiComponents()
        setupUiListeners()
        setupViewModelObservers()
        return binding.root

    }

    private fun setupUiListeners() {
        binding.searchEditText.doOnTextChanged { text, start, before, count ->
            viewModel.filterListsByKeyword(text.toString())
        }
    }

    private fun setupViewModelObservers() {
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
            nowPlayingListAdapter.submitList(it)
        }
        viewModel.topRatedMovies.observe(viewLifecycleOwner) {
            topRankedListAdapter.submitList(it)
        }
        viewModel.errorOccurred.observe(viewLifecycleOwner) { message ->
            message?.let {
                showSnackbar(it)
                viewModel.erroMessageHasDisplayed()
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupUiComponents() {
        binding.topRatedRecyclerView.adapter = topRankedListAdapter
        binding.nowPlayingRecyclerView.adapter = nowPlayingListAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNowPlayingMovies()
        viewModel.getTopRatedMovies()
    }

}