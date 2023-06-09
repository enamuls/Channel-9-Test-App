package com.channel9.testapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.channel9.testapp.databinding.FragmentFullNewsBinding

/**
 * Shows a full news in a webView. Receives a URL as argument.
 */
class FullNewsFragment : Fragment() {

    private val args: FullNewsFragmentArgs by navArgs()
    private lateinit var binding: FragmentFullNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadsImagesAutomatically = true
            }

            // Enforce secure https
            val secureUrl = args.url.replace("http://", "https://")
            loadUrl(secureUrl)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}