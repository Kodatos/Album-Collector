package com.kodatos.albumcollector.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseScreen<T: ViewBinding>: Fragment() {

    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!

    abstract fun onInflateBinding(layoutInflater: LayoutInflater, savedInstanceState: Bundle?): T
    abstract fun onBindingInflated(savedInstanceState: Bundle?)
    open fun applySystemBarInsets(insets: Insets) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = onInflateBinding(layoutInflater, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, wi ->
            val insets = wi.getInsets(WindowInsetsCompat.Type.systemBars())
            applySystemBarInsets(insets)
            WindowInsetsCompat.CONSUMED
        }
        onBindingInflated(savedInstanceState)
    }
}