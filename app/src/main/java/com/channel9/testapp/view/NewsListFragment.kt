package com.channel9.testapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.channel9.testapp.adapter.NewsListAdapter
import com.channel9.testapp.databinding.FragmentNewsListBinding
import com.channel9.testapp.viewmodel.NewsListViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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

        binding.newsListRecyclerView.apply {
            adapter = newsListAdapter
        }

        viewModel.newsList.observe(viewLifecycleOwner) {
            newsListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}