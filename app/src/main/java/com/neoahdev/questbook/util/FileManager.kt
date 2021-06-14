package com.neoahdev.questbook.util

import android.content.Context
import android.util.Log
import com.neoahdev.questbook.model.DailyQuest
import com.neoahdev.questbook.ui.MainActivity
import com.neoahdev.questbook.model.QuestLists
import com.neoahdev.questbook.model.Quest
import com.neoahdev.questbook.model.WeeklyQuest
import com.squareup.moshi.Moshi
import java.io.*
import java.lang.StringBuilder

class FileManager( private val context: Context){

    fun getQuestLists(): QuestLists {
        var questLists: QuestLists = QuestLists(mutableListOf<DailyQuest>(), mutableListOf<WeeklyQuest>())

        val file = File(context.getExternalFilesDir("questlists"), "questLists.json")
        if (file.exists()) {
            Log.d("zz", "External storage file found? yes?")

            var fileInputStream = FileInputStream(file)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            fileInputStream.close()

            val moshi = Moshi.Builder().build()
            val jsonString = stringBuilder.toString()

            questLists = moshi.adapter(QuestLists::class.java).fromJson(jsonString)!!
        } else Log.d("zz", "FILE NOT FOUND")
        return questLists
    }

    fun updateJsonFile():Boolean{
        val moshi = Moshi.Builder().build()
        val jsonString = moshi.adapter(QuestLists::class.java).toJson(MainActivity.questLists)
        Log.d("zz", jsonString)

        val fileName = "questLists.json"
        val questListsFile = File(context.getExternalFilesDir("questlists"), fileName)
        try {
            val fileOutPutStream = FileOutputStream(questListsFile)
            fileOutPutStream.write(jsonString.toByteArray())
            fileOutPutStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val file = File(context.getExternalFilesDir("questlists"), "questLists.json")
        if (file.exists()) {
            Log.d("zz", "External storage file found? yes?")
        } else Log.d("zz", "FILE LOST")
        return false
    }
}