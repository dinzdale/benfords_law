package com.gmjacobs.productions.benfordslaw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.gmjacobs.productions.benfordslaw.model.DataViewModel
import com.google.android.material.textfield.TextInputEditText


class IntegerEntryFragment : Fragment() {

    lateinit var viewModel: DataViewModel
    lateinit var newIntTV: TextInputEditText
    lateinit var integerSetTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_integer_entry, container, false)?.apply {

            newIntTV = findViewById(R.id.integer_entry)
            integerSetTV = findViewById(R.id.integer_set)

            viewModel.integerSet.observe(viewLifecycleOwner) { list ->

                val sb = StringBuilder()
                list.forEachIndexed { index, nxtInt ->
                    sb.append(nxtInt)
                    if (index < list.lastIndex) {
                        sb.append(",  ")
                    }
                }
                if (::integerSetTV.isInitialized) {
                    integerSetTV.text = sb.toString()
                }

            }
            findViewById<Button>(R.id.result_btn)?.apply {
                setOnClickListener {
                    findNavController().navigate(R.id.result_fragment)
                }
            }

            findViewById<Button>(R.id.add_btn)?.apply {
                setOnClickListener {
                    if (::newIntTV.isInitialized && ::viewModel.isInitialized) {
                        val newInt = newIntTV.text.toString()
                        if (isValidInteger(newInt)) {
                            viewModel.addIntegerToSet(newInt.toInt())
                        }
                        else {
                            AlertDialog.Builder(requireContext())
                                .setMessage("$newInt is not a valid integer, please try again")
                                .setPositiveButton(android.R.string.ok,null)
                                .show()
                        }
                        newIntTV.text?.clear()
                    }
                }
            }

            findViewById<Button>(R.id.clear_btn)?.apply {
                setOnClickListener {
                    viewModel.clearIntegerSet()
                    newIntTV.text?.clear()
                }
            }
        }
    }

    fun isValidInteger(number: String): Boolean {
        val regex = "[1-9]+".toRegex()
        return regex.matches(number)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IntegerEntryFragment()
    }
}