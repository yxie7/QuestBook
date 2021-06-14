package com.neoahdev.questbook.model

import java.time.DayOfWeek

class WeeklyQuest (
    name: String,
    description: String,
    estimatedCompletionTime: Int = 0,
    completed: Boolean = false,
    resetDayOfWeek: DayOfWeek
) : Quest(name, description, estimatedCompletionTime, completed) {

}