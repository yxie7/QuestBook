package com.neoahdev.questbook.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neoahdev.questbook.R
import com.neoahdev.questbook.adapter.DailyQuestAdapter
import com.neoahdev.questbook.util.FileManager

/**
 * A fragment representing a list of Items.
 */
class DailyQuestFragment : Fragment() {
    private var columnCount = 1

    var fileManager: FileManager = MainActivity.fileManager
    var dailyQuestList = MainActivity.questLists.dailyQuests

    lateinit var mAdapter: DailyQuestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                mAdapter = DailyQuestAdapter(dailyQuestList)
                adapter = mAdapter
            }
        }
        return view
    }

    fun refreshDailyQuestList() {
        mAdapter.notifyDataSetChanged()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            DailyQuestFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}