package com.neoahdev.questbook.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neoahdev.questbook.R
import com.neoahdev.questbook.model.DailyQuest
import com.neoahdev.questbook.ui.MainActivity.Companion.fileManager
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.bottom_sheet_post_daily.*

class BottomSheetDailyFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CustomBottomSheetDialogFragment"
    }

    private lateinit var dailyRefreshInterface: DailyRefreshInterface


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        dailyRefreshInterface = activity as DailyRefreshInterface
        return inflater.inflate(R.layout.bottom_sheet_post_daily, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fabPostDaily.setOnClickListener {
            var questName: String = inputQuestName.text.toString()
            var questDescription: String = inputQuestDescription.text.toString()
            var questTime: Int = 0
            if (!inputQuestTime.text.toString().isNullOrBlank()) {
                questTime = inputQuestTime.text.toString().toInt()
            }

            if (!questName.isNullOrBlank()) {
                var newQuest = DailyQuest(questName, questDescription, questTime)
                postNewDailyQuest(newQuest)
                dailyRefreshInterface.refreshDailyQuestList()
                dismiss()
            } else {
                Toast.makeText(
                    context,
                    "At least name your quest >:(",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    interface DailyRefreshInterface {
        fun refreshDailyQuestList()
    }

    fun postNewDailyQuest(quest: DailyQuest) {
        MainActivity.questLists.dailyQuests.add(quest)
        if (fileManager.updateJsonFile()) Log.d("zz", "File successfully updated")
        else Log.d("zz", "File NOT updated")
    }
}