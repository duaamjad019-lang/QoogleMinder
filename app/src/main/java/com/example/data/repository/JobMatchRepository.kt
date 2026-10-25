package com.example.data.repository

import android.content.Context
import com.example.data.ai.GeminiCareerArchitectService
import com.example.data.db.AppDatabase
import com.example.data.db.JobMatchEntity
import com.example.data.model.JobMatchResult
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray

class JobMatchRepository(context: Context) {

    private val db = AppDatabase.getInstance(context)
    private val dao = db.jobMatchDao()

    fun getHistoryFlow(): Flow<List<JobMatchEntity>> = dao.getAllMatches()

    suspend fun saveMatchResult(result: JobMatchResult) {
        val strengthsArray = JSONArray()
        result.matchReport.matchedStrengths.forEach { strengthsArray.put(it) }

        val gapsArray = JSONArray()
        result.matchReport.criticalGaps.forEach { gap ->
            gapsArray.put("${gap.severity}: ${gap.title} - ${gap.remediation}")
        }

        val keywordsArray = JSONArray()
        result.matchReport.missingKeywords.forEach { kw ->
            keywordsArray.put("${kw.keyword} (${kw.category})")
        }

        val entity = JobMatchEntity(
            id = result.id,
            timestamp = result.timestamp,
            targetRole = result.targetRole,
            targetCompany = result.targetCompany,
            matchScore = result.matchReport.matchScore,
            rawResumeText = result.rawResumeText,
            targetJdText = result.targetJdText,
            fullMarkdownResume = result.optimizedResume.fullMarkdown,
            matchedStrengthsJson = strengthsArray.toString(),
            criticalGapsJson = gapsArray.toString(),
            missingKeywordsJson = keywordsArray.toString(),
            aiBypassScore = result.aiBypassAnalysis.overallHumanScore,
            toneSummary = result.aiBypassAnalysis.toneAnalysisSummary
        )
        dao.insertMatch(entity)
    }

    suspend fun deleteMatch(id: String) {
        dao.deleteMatchById(id)
    }

    suspend fun clearHistory() {
        dao.clearAll()
    }

    suspend fun executeArchitect(
        rawResume: String,
        targetJd: String,
        customApiKey: String? = null,
        humanizerLevel: String = "Natural Executive",
        metricDensity: String = "High",
        atsAggression: String = "Balanced"
    ): JobMatchResult {
        val result = GeminiCareerArchitectService.runCareerArchitect(
            rawResume = rawResume,
            targetJd = targetJd,
            customApiKey = customApiKey,
            humanizerLevel = humanizerLevel,
            metricDensity = metricDensity,
            atsAggression = atsAggression
        )
        // Automatically save to local history
        saveMatchResult(result)
        return result
    }
}
