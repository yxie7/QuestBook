package com.neoahdev.questbook.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

open abstract class Quest(
    name: String,
    description: String,
    estimatedCompletionTime: Int = 0,
    completed: Boolean = false
):Serializable {
    private var QuestName: String= name
    private var QuestDescription: String = description
    private var QuestEstimatedCompletionTime: Int = estimatedCompletionTime
    private var QuestCompleted: Boolean = completed

}