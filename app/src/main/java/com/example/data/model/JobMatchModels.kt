package com.example.data.model

import java.util.UUID

data class CriticalGap(
    val title: String,
    val severity: String, // "High", "Medium", "Low"
    val explanation: String,
    val remediation: String
)

data class KeywordItem(
    val keyword: String,
    val category: String, // "Hard Skill", "Domain", "Leadership", "Tool"
    val contextTip: String
)

data class MatchReport(
    val matchScore: Int,
    val matchedStrengths: List<String>,
    val criticalGaps: List<CriticalGap>,
    val missingKeywords: List<KeywordItem>,
    val strategicAdvice: String
)

data class ResumeBullet(
    val id: String = UUID.randomUUID().toString(),
    val rawText: String,
    val actionVerb: String,
    val context: String,
    val quantifiableMetric: String,
    val humanCadenceScore: Int = 96
)

data class ExperienceItem(
    val company: String,
    val role: String,
    val duration: String,
    val bullets: List<ResumeBullet>
)

data class CompetencyCategory(
    val categoryName: String,
    val skills: List<String>
)

data class OptimizedResume(
    val fullName: String,
    val targetTitle: String,
    val contactLine: String,
    val professionalSummary: String,
    val coreCompetencies: List<CompetencyCategory>,
    val experience: List<ExperienceItem>,
    val education: List<String>,
    val certifications: List<String>,
    val fullMarkdown: String
)

data class ClicheReplacement(
    val bannedTrope: String,
    val humanAlternative: String,
    val whyRemoved: String
)

data class CadenceBar(
    val sentenceIndex: Int,
    val wordCount: Int,
    val cadenceType: String // "Short Punch", "Balanced Context", "Deep Metric"
)

data class AiBypassAnalysis(
    val overallHumanScore: Int, // e.g. 98%
    val burstinessRating: String, // e.g. "Dynamic & Varied"
    val perplexityRating: String, // e.g. "Authentic Human Lexicon"
    val gptZeroEstimate: Int, // e.g. 99% Human
    val turnitinEstimate: Int, // e.g. 0% AI Detected
    val copyleaksEstimate: String, // e.g. "Human Authored"
    val clichesRemoved: List<ClicheReplacement>,
    val sentenceCadences: List<CadenceBar>,
    val toneAnalysisSummary: String
)

data class JobMatchResult(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val targetRole: String,
    val targetCompany: String,
    val matchReport: MatchReport,
    val optimizedResume: OptimizedResume,
    val aiBypassAnalysis: AiBypassAnalysis,
    val rawResumeText: String,
    val targetJdText: String
)

data class SamplePreset(
    val id: String,
    val roleTitle: String,
    val companyOrField: String,
    val resumeSnippet: String,
    val jdSnippet: String
)
