package com.drmontoya.moviesbl.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.drmontoya.moviesbl.R
import com.drmontoya.moviesbl.data.remote.api.ApiConstants
import com.drmontoya.moviesbl.databinding.FragmentDetailBinding
import com.drmontoya.moviesbl.domain.model.Movie
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    val args by navArgs<DetailFragmentArgs>()
    val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val id = args.id
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setupViewModelObervers()
        viewModel.loadMovieWithId(id)
        return binding.root

    }

    private fun setupViewModelObervers() {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            bindViewWithMovie(movie)
        }
        viewModel.errorOccurred.observe(viewLifecycleOwner) { message ->
            message?.let {
                displaySnackWithMessage(message)
                viewModel.snackbarHasDisplayed()
            }
        }
    }

    private fun displaySnackWithMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }


    private fun bindViewWithMovie(movie: Movie) {
        val context = requireContext()
        binding.titleTextView.text = movie.original_title
        binding.rateTextView.text =
            StringBuilder().append(context.getString(R.string.average_votes))
                .append(movie.vote_average).append(context.getString(R.string.overTen))
        binding.overviewTextView.text = movie.overview
        val imageUrl =
            StringBuilder().append(ApiConstants.BACKDROP_BASE_URL).append(movie.backdrop_path).toString()
        Glide.with(context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(
                RequestOptions().placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_network_error)
            ).into(binding.movieBackdrop)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}