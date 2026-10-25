package com.example

import com.example.data.ai.ExecutiveCareerEngine
import com.example.data.model.SamplePresets
import org.junit.Assert.*
import org.junit.Test

class JobMatchEngineTest {

    @Test
    fun testPresetsDataIntegrity() {
        val presets = SamplePresets.presets
        assertTrue("Presets must not be empty", presets.isNotEmpty())
        assertEquals(4, presets.size)

        presets.forEach { preset ->
            assertTrue(preset.roleTitle.isNotBlank())
            assertTrue(preset.resumeSnippet.isNotBlank())
            assertTrue(preset.jdSnippet.isNotBlank())
        }
    }

    @Test
    fun testPhase1GapAnalysisWorkflow() {
        val preset = SamplePresets.presets.first()
        val result = ExecutiveCareerEngine.analyzeAndOptimize(
            rawResume = preset.resumeSnippet,
            targetJd = preset.jdSnippet
        )

        // Phase 1 verification
        val report = result.matchReport
        assertTrue("Match score should be between 70% and 99%", report.matchScore in 70..99)
        assertTrue("Matched strengths should be populated", report.matchedStrengths.isNotEmpty())
        assertTrue("Critical gaps should identify missing competencies", report.criticalGaps.isNotEmpty())
        assertTrue("Missing keywords should be listed", report.missingKeywords.isNotEmpty())
        assertTrue("Strategic advice must be non-empty", report.strategicAdvice.isNotBlank())
    }

    @Test
    fun testPhase2OptimizedResumeFormula() {
        val preset = SamplePresets.presets.first()
        val result = ExecutiveCareerEngine.analyzeAndOptimize(
            rawResume = preset.resumeSnippet,
            targetJd = preset.jdSnippet
        )

        // Phase 2 verification
        val resume = result.optimizedResume
        assertTrue("Professional summary must be punchy", resume.professionalSummary.length > 50)
        assertTrue("Core competencies must be grouped", resume.coreCompetencies.isNotEmpty())
        assertTrue("Experience items must exist", resume.experience.isNotEmpty())

        val bullets = resume.experience.flatMap { it.bullets }
        assertTrue("Bullets must follow formula", bullets.isNotEmpty())
        bullets.forEach { bullet ->
            assertTrue("Action verb must be present", bullet.actionVerb.isNotBlank())
            assertTrue("Context must be present", bullet.context.isNotBlank())
            assertTrue("Metric must be present", bullet.quantifiableMetric.isNotBlank())
            assertTrue("Cadence score must be high", bullet.humanCadenceScore >= 90)
        }
    }

    @Test
    fun testPhase3AiBypassVerification() {
        val preset = SamplePresets.presets.first()
        val result = ExecutiveCareerEngine.analyzeAndOptimize(
            rawResume = preset.resumeSnippet,
            targetJd = preset.jdSnippet
        )

        // Phase 3 verification
        val aiBypass = result.aiBypassAnalysis
        assertTrue("Human score must exceed 90%", aiBypass.overallHumanScore >= 95)
        assertEquals(0, aiBypass.turnitinEstimate)
        assertTrue(aiBypass.gptZeroEstimate >= 95)
        assertTrue(aiBypass.clichesRemoved.isNotEmpty())
        assertTrue(aiBypass.sentenceCadences.isNotEmpty())
        assertTrue(aiBypass.toneAnalysisSummary.isNotBlank())
    }

    @Test
    fun testSingleBulletTransformer() {
        val raw = "Responsible for building payment microservices and handling on-call."
        val transformed = ExecutiveCareerEngine.transformSingleBullet(raw, "Platform Lead")

        assertTrue(transformed.actionVerb.isNotBlank())
        assertTrue(transformed.quantifiableMetric.isNotBlank())
        assertTrue(transformed.humanCadenceScore in 90..100)
    }
}
