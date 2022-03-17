package com.esmaeel.challenge.ui.recommendationsList

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.esmaeel.challenge.R
import com.esmaeel.challenge.data.remote.models.RecommendationResult
import com.esmaeel.challenge.databinding.RecommendationItemBinding
import com.esmaeel.challenge.utils.ktx.layoutInflator


class RecommendationsListAdapter(
    private val onResultClicked: (item: RecommendationResult, position: Int) -> Unit,
) : ListAdapter<RecommendationResult, RecommendationsListAdapter.ResultViewHolder>(ItemDiffUtil) {

    private object ItemDiffUtil : DiffUtil.ItemCallback<RecommendationResult>() {
        override fun areItemsTheSame(oldItem: RecommendationResult, newItem: RecommendationResult) =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: RecommendationResult,
            newItem: RecommendationResult
        ) = areItemsTheSame(oldItem, newItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationsListAdapter.ResultViewHolder {
        return ResultViewHolder(
            RecommendationItemBinding.inflate(
                parent.layoutInflator,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecommendationsListAdapter.ResultViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class ResultViewHolder(private val binder: RecommendationItemBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(item: RecommendationResult) = with(binder) {
            root.setOnClickListener { onResultClicked(item, absoluteAdapterPosition) }
            binder.nameTv.text = item.name
            binder.info.text = item.location.address
            image.load(item.getImage()) {
                fallback(R.drawable.ic_baseline_my_location_24_black)
                error(R.drawable.ic_baseline_my_location_24_black)
                placeholder(R.drawable.ic_baseline_my_location_24_black)
            }
        }
    }


}

