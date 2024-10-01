package com.imagine.jordanpass.tools.views.loader

import android.animation.ObjectAnimator
import android.graphics.Color
import android.widget.ImageView

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.imagine.jordanpass.tools.R
import com.imagine.jordanpass.tools.databinding.FragmentLoaderBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


open class LoaderFragment : DialogFragment() {
    private var _binding: FragmentLoaderBinding? = null
    private val binding get() = _binding!!

    private var rotationAnimator:ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val textView = this.view.findViewById<ImageView>(R.id.textView)
        val rotationAnimator = ObjectAnimator.ofFloat(binding.loader, "rotation", 0f, 360f)
        rotationAnimator?.repeatCount = ObjectAnimator.INFINITE
//        rotationAnimator.repeatMode = ObjectAnimator.REVERSE
        rotationAnimator?.duration = 2000 // Set the duration of the animation in milliseconds (1 second in this example)
        rotationAnimator?.start()
    }

    fun showLoader(fragmentManager: FragmentManager){
        this.show(fragmentManager,"Loader")
    }
//
    fun hideLoader(){
        this.dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoaderBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        lifecycleScope.launch {
            delay(5000)
            isCancelable = true
        }
//        return inflater.inflate(R.layout.fragment_loader, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

}