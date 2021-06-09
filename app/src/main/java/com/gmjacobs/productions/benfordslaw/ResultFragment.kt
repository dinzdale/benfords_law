package com.gmjacobs.productions.benfordslaw

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmjacobs.productions.benfordslaw.model.DataViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {

    private val benfordTable = listOf(.301, .176, .125, .097, .079, .067, .058, .051, .046)

    lateinit var integer_set_tv: TextView
    lateinit var result_list: RecyclerView
    lateinit var viewModel: DataViewModel

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_benfords_law_result, container, false)?.apply {
            integer_set_tv = findViewById(R.id.integer_set_tv)
            result_list = findViewById<RecyclerView>(R.id.result_list)
            val layout = LinearLayoutManager(requireContext())
            layout.orientation = LinearLayoutManager.VERTICAL
            result_list.layoutManager = layout
            viewModel.integerSet.observe(viewLifecycleOwner) {
                val sb = StringBuffer()
                it.forEachIndexed { index, nxtInt->
                    sb.append(nxtInt.toString())
                    if (index < it.lastIndex) {
                        sb.append(",")
                    }
                }
                integer_set_tv.text = sb.toString()
                result_list.adapter = BenfordLawListAdapter(requireContext(), evaluateSet(it))
            }
        }
    }

    fun evaluateSet(list: List<Int>): List<BenfordLawResult> {
        val resultList = arrayListOf<BenfordLawResult>()
        val occurrenceList = arrayListOf<Int>(0, 0, 0, 0, 0, 0, 0, 0, 0)
        list.forEach { nxtInt ->
            val digit = getFirstDigit(nxtInt)
            occurrenceList[digit-1]++
        }

        occurrenceList.forEachIndexed { index, nxtSum ->
            if (nxtSum > 0) {
                resultList.add(BenfordLawResult(index+1, nxtSum, nxtSum.toFloat() / list.size))
            } else {
                resultList.add(BenfordLawResult(index+1))
            }
        }
        return resultList
    }

    fun getFirstDigit(nxtInt: Int): Int {
        var result = nxtInt
        while (result > 9) {
            result /= 10
        }
        return result
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

data class BenfordLawResult(
    val digit: Int,
    val occurrences: Int = 0,
    val occurPercentage: Float = 0.0f,
    val benfordLaw: Boolean = false
)

class BenfordLawListAdapter(val context: Context, val list: List<BenfordLawResult>) :
    RecyclerView.Adapter<BenfordLawListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.benford_law_list_item, null)
        )
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.digit.text = list[position].digit.toString()
        holder.occurrences.text = list[position].occurrences.toString()
        holder.percentage.text = list[position].occurPercentage.toString()
        holder.match.text = list[position].benfordLaw.toString()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val digit = view.findViewById<TextView>(R.id.digit)
        val occurrences = view.findViewById<TextView>(R.id.occurrences)
        val percentage = view.findViewById<TextView>(R.id.percentage)
        val match = view.findViewById<TextView>(R.id.benford_law_match)
    }
}