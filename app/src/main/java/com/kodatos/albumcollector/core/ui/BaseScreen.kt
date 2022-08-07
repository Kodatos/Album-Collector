package com.kodatos.albumcollector.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseScreen<T: ViewBinding>: Fragment() {

    private var _binding: T? = null
    protected val binding: T
        get() = _binding!!

    abstract fun onInflateBinding(layoutInflater: LayoutInflater, savedInstanceState: Bundle?): T
    abstract fun onBindingInflated(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = onInflateBinding(layoutInflater, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onBindingInflated(savedInstanceState)
    }
}