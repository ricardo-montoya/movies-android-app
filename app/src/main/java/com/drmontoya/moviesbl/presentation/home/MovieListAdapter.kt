package com.drmontoya.moviesbl.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.drmontoya.moviesbl.R
import com.drmontoya.moviesbl.data.remote.api.ApiConstants
import com.drmontoya.moviesbl.databinding.ItemNowPlayingBinding
import com.drmontoya.moviesbl.databinding.ItemTopRatedBinding
import com.drmontoya.moviesbl.domain.model.Movie


class MovieListAdapter(val onClick: (id: Int) -> Unit, val type: MovieViewType) :
    ListAdapter<Movie, MovieViewHolder>(MovieDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent, this.type)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, this.type, this.onClick)
    }

}

class MovieViewHolder private constructor(val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie, type: MovieViewType, onClick: (id: Int) -> Unit) {
        val context = binding.root.context
        when (binding) {
            is ItemNowPlayingBinding -> {
                binding.titleTextView.text = item.original_title
                binding.voteAverageTextView.text = StringBuilder().append(item.vote_average).append(
                    context.getString(
                        R.string.overTen
                    )
                )
                val urlImage =
                    StringBuilder().append(ApiConstants.IMAGE_BASE_URL).append(item.poster_path)
                        .toString()
                Glide.with(context).load(urlImage)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_network_error)
                    ).into(binding.posterImageView)
                binding.root.setOnClickListener { onClick(item.id) }
            }

            is ItemTopRatedBinding -> {
                binding.titleTextView.text = item.original_title
                binding.voteAverageTextView.text = StringBuilder().append(item.vote_average).append(
                    context.getString(
                        R.string.overTen
                    )
                )
                val urlImage =
                    StringBuilder().append(ApiConstants.IMAGE_BASE_URL).append(item.poster_path)
                        .toString()
                Glide.with(context).load(urlImage)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_loading)
                            .error(R.drawable.ic_network_error)
                    ).into(binding.posterImageView)
                binding.root.setOnClickListener { onClick(item.id) }
            }
        }
    }

    companion object {

        fun from(parent: ViewGroup, viewType: MovieViewType): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                MovieViewType.NowPlaying -> {
                    val binding = ItemNowPlayingBinding.inflate(inflater, parent, false)
                    MovieViewHolder(binding)
                }

                MovieViewType.TopRated -> {
                    val binding = ItemTopRatedBinding.inflate(inflater, parent, false)
                    MovieViewHolder(binding)
                }
            }
        }
    }
}

class MovieDiffUtil() : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}

sealed class MovieViewType {
    object TopRated : MovieViewType()
    object NowPlaying : MovieViewType()
}