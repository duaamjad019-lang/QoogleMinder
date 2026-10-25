package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.ExecutiveCareerEngine
import com.example.data.db.JobMatchEntity
import com.example.data.model.JobMatchResult
import com.example.data.model.ResumeBullet
import com.example.data.model.SamplePreset
import com.example.data.model.SamplePresets
import com.example.data.repository.JobMatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class JobMatchUiState(
    val rawResume: String = "",
    val targetJd: String = "",
    val customApiKey: String = "",
    val humanizerLevel: String = "Natural Executive",
    val metricDensity: String = "High",
    val atsAggressiveness: String = "Balanced",
    val isLoading: Boolean = false,
    val loadingStep: String = "",
    val currentResult: JobMatchResult? = null,
    val activePhaseTab: Int = 0, // 0 = Match & Gap, 1 = Optimized Resume, 2 = AI Bypass Check
    val history: List<JobMatchEntity> = emptyList(),
    val bulletLabInput: String = "Responsible for managing software development and coordinating team meetings.",
    val bulletLabResult: ResumeBullet? = null,
    val toastMessage: String? = null
)

class JobMatchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = JobMatchRepository(application)

    private val _uiState = MutableStateFlow(JobMatchUiState())
    val uiState: StateFlow<JobMatchUiState> = _uiState.asStateFlow()

    init {
        // Pre-load default preset (Staff Platform Engineer) so users have immediate rich context
        loadPreset(SamplePresets.presets.first())

        // Collect database history
        viewModelScope.launch {
            repository.getHistoryFlow().collectLatest { list ->
                _uiState.value = _uiState.value.copy(history = list)
            }
        }
    }

    fun onRawResumeChange(newText: String) {
        _uiState.value = _uiState.value.copy(rawResume = newText)
    }

    fun onTargetJdChange(newText: String) {
        _uiState.value = _uiState.value.copy(targetJd = newText)
    }

    fun onHumanizerLevelChange(level: String) {
        _uiState.value = _uiState.value.copy(humanizerLevel = level)
    }

    fun onMetricDensityChange(density: String) {
        _uiState.value = _uiState.value.copy(metricDensity = density)
    }

    fun onAtsAggressivenessChange(aggression: String) {
        _uiState.value = _uiState.value.copy(atsAggressiveness = aggression)
    }

    fun setActivePhaseTab(tabIndex: Int) {
        _uiState.value = _uiState.value.copy(activePhaseTab = tabIndex)
    }

    fun setCustomApiKey(key: String) {
        _uiState.value = _uiState.value.copy(customApiKey = key)
    }

    fun clearToast() {
        _uiState.value = _uiState.value.copy(toastMessage = null)
    }

    fun loadPreset(preset: SamplePreset) {
        _uiState.value = _uiState.value.copy(
            rawResume = preset.resumeSnippet,
            targetJd = preset.jdSnippet,
            toastMessage = "Loaded preset: ${preset.roleTitle} (${preset.companyOrField})"
        )
    }

    fun clearInputs() {
        _uiState.value = _uiState.value.copy(
            rawResume = "",
            targetJd = ""
        )
    }

    fun runOptimization() {
        val resume = _uiState.value.rawResume.trim()
        val jd = _uiState.value.targetJd.trim()

        if (resume.isBlank()) {
            _uiState.value = _uiState.value.copy(toastMessage = "Please provide or paste a Raw Resume first.")
            return
        }
        if (jd.isBlank()) {
            _uiState.value = _uiState.value.copy(toastMessage = "Please provide a Target Job Description.")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                loadingStep = "Extracting critical keywords & conducting gap analysis..."
            )

            try {
                _uiState.value = _uiState.value.copy(
                    loadingStep = "Transforming duties to Action + Context + Result (Quantifiable Metrics)..."
                )

                val result = repository.executeArchitect(
                    rawResume = resume,
                    targetJd = jd,
                    customApiKey = _uiState.value.customApiKey,
                    humanizerLevel = _uiState.value.humanizerLevel,
                    metricDensity = _uiState.value.metricDensity,
                    atsAggression = _uiState.value.atsAggressiveness
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    loadingStep = "",
                    currentResult = result,
                    activePhaseTab = 0, // Show Match & Gap report first
                    toastMessage = "Optimization complete! ATS Match: ${result.matchReport.matchScore}% | Humanizer Cadence: ${result.aiBypassAnalysis.overallHumanScore}%"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    loadingStep = "",
                    toastMessage = "Error during optimization: ${e.localizedMessage ?: "Unknown error"}"
                )
            }
        }
    }

    fun onBulletLabInputChange(text: String) {
        _uiState.value = _uiState.value.copy(bulletLabInput = text)
    }

    fun transformBulletInLab() {
        val input = _uiState.value.bulletLabInput.trim()
        if (input.isBlank()) return
        val targetRole = _uiState.value.currentResult?.targetRole ?: "Target Role"
        val transformed = ExecutiveCareerEngine.transformSingleBullet(input, targetRole)
        _uiState.value = _uiState.value.copy(bulletLabResult = transformed)
    }

    fun loadFromHistory(entity: JobMatchEntity) {
        viewModelScope.launch {
            val recreated = ExecutiveCareerEngine.analyzeAndOptimize(
                rawResume = entity.rawResumeText,
                targetJd = entity.targetJdText
            )
            _uiState.value = _uiState.value.copy(
                rawResume = entity.rawResumeText,
                targetJd = entity.targetJdText,
                currentResult = recreated,
                activePhaseTab = 0,
                toastMessage = "Loaded session for ${entity.targetRole} (${entity.targetCompany})"
            )
        }
    }

    fun deleteHistoryItem(id: String) {
        viewModelScope.launch {
            repository.deleteMatch(id)
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearHistory()
            _uiState.value = _uiState.value.copy(toastMessage = "All history cleared.")
        }
    }
}
