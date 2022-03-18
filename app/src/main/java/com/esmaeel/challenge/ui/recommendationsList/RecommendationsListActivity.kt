package com.esmaeel.challenge.ui.recommendationsList

import android.annotation.SuppressLint
import androidx.activity.viewModels
import com.esmaeel.challenge.R
import com.esmaeel.challenge.common.base.BaseActivity
import com.esmaeel.challenge.common.base.ViewState
import com.esmaeel.challenge.data.remote.models.RecommendationResult
import com.esmaeel.challenge.databinding.RecommendationsListActivityBinding
import com.esmaeel.challenge.ui.ConfigurationActivity
import com.esmaeel.challenge.utils.ktx.gone
import com.esmaeel.challenge.utils.ktx.hasLocationPermission
import com.esmaeel.challenge.utils.ktx.show
import com.skydoves.bundler.bundle
import com.skydoves.bundler.intentOf
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecommendationsListActivity :
    BaseActivity<RecommendationsListActivityBinding, RecommendationsViewModel>(R.layout.recommendations_list_activity) {

    private val lat: Double by bundle(LAT, 0.0)
    private val long: Double by bundle(LONG, 0.0)


    companion object {
        const val LAT = "LAT"
        const val LONG = "LNG"
    }

    private fun openConfigurationActivity() {
        intentOf<ConfigurationActivity> {
            startActivity(this@RecommendationsListActivity)
            finishAffinity()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        if (hasLocationPermission().not())
            openConfigurationActivity()
    }

    override val viewModel: RecommendationsViewModel by viewModels()

    private val listAdapter by lazy {
        RecommendationsListAdapter(
            onResultClicked = { item, _ -> openDetails(item) }
        )
    }


    override fun setup() = with(binder) {
        recycler.adapter = listAdapter
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = false
            getData()
        }

        refreshButton.setOnClickListener {
            getData()
        }

        getData()
    }

    private fun getData() {
        viewModel.getRecommendationsList(lat, long)
    }

    override fun render(state: ViewState) {
        when (state) {
            is RecommendationsListViewState.OnRecommendationsListResponse -> bindList(state.data)
            is RecommendationsListViewState.OnShouldRequestPermission -> openConfigurationActivity()
            is ViewState.Empty -> showEmptyView()
            is ViewState.Error -> handleError(state.error)
        }

    }

    private fun showEmptyView() {
        binder.recycler.gone()
        binder.emptyLayout.show()
    }

    private fun handleError(error: String?) {
        error?.let { showError(it) }
    }

    private fun bindList(data: List<RecommendationResult>) {
        binder.recycler.show()
        listAdapter.submitList(data)
    }

    private fun openDetails(item: RecommendationResult) {
        // TODO: Open details activity
    }

}

