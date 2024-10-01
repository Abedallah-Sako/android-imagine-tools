package com.imagine.android_imagine_tools.tools.ui.fragments.webView

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.imagine.android_imagine_tools.tools.R
import com.imagine.android_imagine_tools.tools.databinding.FragmentWebViewBinding
import com.imagine.android_imagine_tools.tools.ext.fadeInToVisible
import com.imagine.android_imagine_tools.tools.ext.fadeOutToGone
import com.imagine.android_imagine_tools.tools.ext.isNavControllerSet
import com.imagine.android_imagine_tools.tools.ext.toPx
import com.imagine.android_imagine_tools.tools.ext.visible

/**
 * Takes the following as arguments
 *
 * title : Title of the toolbar
 *
 * link : URL of the web view, must start with https/http
 *
 * topPadding: Top padding of the toolbar
 *
 * toolbarBGColor: Background color of the toolbar
 *
 * */
class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    private var title = "Web view"
    private var link = ""
    private var topPaddingDP = 0
    private var toolbarBGColor = "#ffffff"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getArgs()
        setupUI()
        setupWebView()
        setupListeners()

    }

    private fun getArgs() {
        title = arguments?.getString("title", "Web view") ?: "Web view"
        link = arguments?.getString("link", "") ?: ""
        topPaddingDP = arguments?.getInt("topPadding", 0) ?: 0
        toolbarBGColor = arguments?.getString("toolbarBGColor", "#ffffff") ?: "#ffffff"
    }

    private fun setupUI() {
        //set toolbar title
        binding.webViewIncludeToolbar.toolbarTitleTextView.text = title

        //set toolbar close button drawable
        binding.webViewIncludeToolbar.toolbarShareImageFilterView.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_close
            )
        )

        binding.webViewIncludeToolbar.root.setPadding(0, topPaddingDP.toPx, 0, 0)
        binding.webViewIncludeToolbar.root.setBackgroundColor(Color.parseColor(toolbarBGColor))
    }

    private fun setupWebView() {

        if (!Patterns.WEB_URL.matcher(link).matches() || (!link.startsWith("http"))) {
            Snackbar.make(binding.webView, getString(R.string.use_valid_URL), Snackbar.LENGTH_LONG)
                .show()
            if (isNavControllerSet()) {
                findNavController().navigateUp()
            } else {
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
            return
        }


        //show loading progress bar while loading
        binding.webViewProgressBar.visible()

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                if (_binding != null) {
                    //hide loading progress bar
                    binding.webViewProgressBar.fadeOutToGone()

                    //show close button if web view can go back else hide it
                    if (binding.webView.canGoBack()) {
                        binding.webViewIncludeToolbar.toolbarShareImageFilterView.fadeInToVisible()
                    } else {
                        binding.webViewIncludeToolbar.toolbarShareImageFilterView.fadeOutToGone()
                    }
                }


            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)

                if (_binding != null) {
                    //show error
                    Snackbar.make(
                        binding.webView,
                        getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG
                    ).show()

                    //hide loading progress bar
                    binding.webViewProgressBar.fadeOutToGone()
                }

            }
        }

//        binding.webView.settings.cacheMode = WebSettings.LOAD_DEFAULT

        //load url
        binding.webView.loadUrl(link)
    }

    private fun setupListeners() {

        //back button
        binding.webViewIncludeToolbar.toolbarBackImageFilterView.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            } else {
                if (isNavControllerSet()) {
                    findNavController().navigateUp()
                } else {
                    requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
                }
            }
        }

        //close button
        binding.webViewIncludeToolbar.toolbarShareImageFilterView.setOnClickListener {
            if (isNavControllerSet()) {
                findNavController().navigateUp()
            } else {
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}