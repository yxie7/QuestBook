package com.neoahdev.questbook.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DailyQuest(
    var name: String,
    var description: String,
    var estimatedCompletionTime: Int = 0,
    var completed: Boolean = false
) : Quest(name, description, estimatedCompletionTime, completed) {

}