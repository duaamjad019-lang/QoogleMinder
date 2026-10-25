package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job_matches")
data class JobMatchEntity(
    @PrimaryKey
    val id: String,
    val timestamp: Long,
    val targetRole: String,
    val targetCompany: String,
    val matchScore: Int,
    val rawResumeText: String,
    val targetJdText: String,
    val fullMarkdownResume: String,
    val matchedStrengthsJson: String,
    val criticalGapsJson: String,
    val missingKeywordsJson: String,
    val aiBypassScore: Int,
    val toneSummary: String
)
