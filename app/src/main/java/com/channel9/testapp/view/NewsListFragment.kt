package com.channel9.testapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.channel9.testapp.adapter.NewsListAdapter
import com.channel9.testapp.databinding.FragmentNewsListBinding
import com.channel9.testapp.viewmodel.NewsListViewModel
import com.channel9.testapp.viewmodel.State
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Shows a list of news
 */
class NewsListFragment : Fragment(), KoinComponent {

    private val viewModel: NewsListViewModel by inject()
    private lateinit var binding: FragmentNewsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsListAdapter = NewsListAdapter()

        binding.newsListRecyclerView.adapter = newsListAdapter

        lifecycleScope.launch {
            viewModel.newsList.collect { state ->
                when (state) {
                    is State.Loading -> Unit // TODO: Show a loading state
                    is State.Empty -> Unit // TODO: Show a message for empty state
                    is State.Success -> newsListAdapter.submitList(state.data)
                    is State.Failure -> Unit // TODO: Show a message for failure state
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}