package com.neoahdev.questbook.model

class DailyQuest(
    name: String,
    description: String,
    estimatedCompletionTime: Int = 0,
    completed: Boolean = false
) : Quest(name, description, estimatedCompletionTime, completed) {

}