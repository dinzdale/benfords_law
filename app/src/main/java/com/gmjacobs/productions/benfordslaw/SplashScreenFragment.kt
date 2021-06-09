package com.gmjacobs.productions.benfordslaw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(1000 * 3)
            findNavController().navigate(R.id.integer_entry_fragment)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashScreenFragment()
    }
}