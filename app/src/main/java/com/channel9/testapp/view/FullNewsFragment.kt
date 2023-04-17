package com.channel9.testapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.channel9.testapp.databinding.FragmentFullNewsBinding

class FullNewsFragment : Fragment() {

    private lateinit var binding: FragmentFullNewsBinding

    private val args: FullNewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadsImagesAutomatically = true
            }
            // Enforce secure https
            val newUrl = args.url.replace("http://", "https://")
            binding.webView.loadUrl(newUrl)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}