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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IntegerEntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IntegerEntryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IntegerEntryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IntegerEntryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}