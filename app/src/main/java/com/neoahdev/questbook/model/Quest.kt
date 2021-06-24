package com.neoahdev.questbook.model

import com.squareup.moshi.JsonClass
import java.io.Serializable


open abstract class Quest(
    name: String,
    description: String,
    estimatedCompletionTime: Int = 0,
    completed: Boolean = false
):Serializable {
    var QuestName: String= name
    var QuestDescription: String = description
    var QuestEstimatedCompletionTime: Int = estimatedCompletionTime
    var QuestCompleted: Boolean = completed

}