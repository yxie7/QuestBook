package com.neoahdev.questbook.model

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class QuestLists(
    var dailyQuests: MutableList<DailyQuest> = mutableListOf<DailyQuest>(),
    var weeklyQuests: MutableList<WeeklyQuest> = mutableListOf<WeeklyQuest>()
): Serializable