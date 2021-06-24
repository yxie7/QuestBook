package com.neoahdev.questbook.model

import com.squareup.moshi.JsonClass
import java.time.DayOfWeek

@JsonClass(generateAdapter = true)
class WeeklyQuest (
    var name: String,
    var description: String,
    var estimatedCompletionTime: Int = 0,
    var completed: Boolean = false,
    var resetDayOfWeek: DayOfWeek
) : Quest(name, description, estimatedCompletionTime, completed) {

}