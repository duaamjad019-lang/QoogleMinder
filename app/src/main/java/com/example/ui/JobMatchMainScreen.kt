package com.example.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.SamplePresets
import com.example.ui.components.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobMatchMainScreen(
    viewModel: JobMatchViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var showHistoryDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    var activeInputTab by remember { mutableStateOf(0) } // 0 = Resume, 1 = Job Description

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    if (showHistoryDialog) {
        HistoryDialog(
            historyList = uiState.history,
            onSelectHistory = { viewModel.loadFromHistory(it) },
            onDeleteItem = { viewModel.deleteHistoryItem(it) },
            onClearAll = { viewModel.clearAllHistory() },
            onDismiss = { showHistoryDialog = false }
        )
    }

    if (showSettingsDialog) {
        SettingsDialog(
            currentApiKey = uiState.customApiKey,
            onSaveApiKey = { viewModel.setCustomApiKey(it) },
            onDismiss = { showSettingsDialog = false }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = RoyalBlue600,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Rounded.Work,
                                    contentDescription = "Logo",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "JobMatch Pro",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "AI Career Architect & Humanizer",
                                style = MaterialTheme.typography.labelSmall,
                                color = Emerald400,
                                fontSize = 10.sp
                            )
                        }
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showHistoryDialog = true },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("history_action_btn")
                    ) {
                        Icon(imageVector = Icons.Rounded.History, contentDescription = "History")
                    }
                    IconButton(
                        onClick = { showSettingsDialog = true },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("settings_action_btn")
                    ) {
                        Icon(imageVector = Icons.Rounded.Settings, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(GoogleBlue, GoogleRed, GoogleYellow, GoogleGreen)
                        )
                    )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Banner
            item {
                HeroBannerSection(
                    onSampleSelected = { preset -> viewModel.loadPreset(preset) }
                )
            }

            // Input Workspace
            item {
                InputWorkspaceCard(
                    rawResume = uiState.rawResume,
                    targetJd = uiState.targetJd,
                    activeInputTab = activeInputTab,
                    onTabSelected = { activeInputTab = it },
                    onResumeChange = { viewModel.onRawResumeChange(it) },
                    onJdChange = { viewModel.onTargetJdChange(it) },
                    onClearInputs = { viewModel.clearInputs() },
                    humanizerLevel = uiState.humanizerLevel,
                    onHumanizerLevelChange = { viewModel.onHumanizerLevelChange(it) },
                    metricDensity = uiState.metricDensity,
                    onMetricDensityChange = { viewModel.onMetricDensityChange(it) },
                    isLoading = uiState.isLoading,
                    loadingStep = uiState.loadingStep,
                    onRunOptimization = { viewModel.runOptimization() }
                )
            }

            // Results Section (Phase 1, Phase 2, Phase 3, Bullet Lab)
            val result = uiState.currentResult
            if (result != null) {
                item {
                    PhaseTabsBar(
                        activeTab = uiState.activePhaseTab,
                        onTabSelect = { viewModel.setActivePhaseTab(it) },
                        matchScore = result.matchReport.matchScore,
                        humanScore = result.aiBypassAnalysis.overallHumanScore
                    )
                }

                item {
                    when (uiState.activePhaseTab) {
                        0 -> Phase1MatchGapView(
                            matchReport = result.matchReport,
                            targetRole = result.targetRole,
                            targetCompany = result.targetCompany
                        )
                        1 -> Phase2OptimizedResumeView(
                            optimizedResume = result.optimizedResume
                        )
                        2 -> Phase3AiBypassView(
                            aiBypassAnalysis = result.aiBypassAnalysis
                        )
                        3 -> BulletLabView(
                            input = uiState.bulletLabInput,
                            onInputChange = { viewModel.onBulletLabInputChange(it) },
                            transformedBullet = uiState.bulletLabResult,
                            onTransformClick = { viewModel.transformBulletInLab() }
                        )
                    }
                }
            } else {
                // Interactive Bullet Lab Preview for immediate testing
                item {
                    Text(
                        text = "OR TEST A SINGLE ACHIEVEMENT STATEMENT:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    BulletLabView(
                        input = uiState.bulletLabInput,
                        onInputChange = { viewModel.onBulletLabInputChange(it) },
                        transformedBullet = uiState.bulletLabResult,
                        onTransformClick = { viewModel.transformBulletInLab() },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun HeroBannerSection(
    onSampleSelected: (com.example.data.model.SamplePreset) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DeepSlate900)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.jobmatch_banner_1792911050248),
                contentDescription = "Executive Career Analytics",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            // Gradient scrim
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, DeepSlate950.copy(alpha = 0.95f))
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "EXECUTIVE CAREER ARCHITECT",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Emerald400,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = "ATS Optimization & AI-Humanizer",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FeatureMiniBadge("Gap Analysis")
                    FeatureMiniBadge("Action + Metric")
                    FeatureMiniBadge("Zero AI Tropes")
                }
            }
        }

        // Preset selector bar
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "Load Sample Resume & Target JD (1-Tap):",
                style = MaterialTheme.typography.labelSmall,
                color = TextMutedDark,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(SamplePresets.presets) { preset ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = CardDefaults.outlinedCardBorder(),
                        modifier = Modifier
                            .clickable { onSampleSelected(preset) }
                            .minimumInteractiveComponentSize()
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                            Text(
                                text = preset.roleTitle,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = preset.companyOrField,
                                style = MaterialTheme.typography.labelSmall,
                                color = RoyalBlue400,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeatureMiniBadge(text: String) {
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = Color.Black.copy(alpha = 0.5f)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFFE2E8F0),
            fontSize = 9.sp,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun InputWorkspaceCard(
    rawResume: String,
    targetJd: String,
    activeInputTab: Int,
    onTabSelected: (Int) -> Unit,
    onResumeChange: (String) -> Unit,
    onJdChange: (String) -> Unit,
    onClearInputs: () -> Unit,
    humanizerLevel: String,
    onHumanizerLevelChange: (String) -> Unit,
    metricDensity: String,
    onMetricDensityChange: (String) -> Unit,
    isLoading: Boolean,
    loadingStep: String,
    onRunOptimization: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Tab Selector between Raw Resume and Target JD
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                InputTabButton(
                    title = "Raw Resume",
                    subtitle = "${rawResume.split("\\s+".toRegex()).filter { it.isNotBlank() }.size} words",
                    selected = activeInputTab == 0,
                    onClick = { onTabSelected(0) },
                    modifier = Modifier.weight(1f)
                )
                InputTabButton(
                    title = "Target JD",
                    subtitle = "${targetJd.split("\\s+".toRegex()).filter { it.isNotBlank() }.size} words",
                    selected = activeInputTab == 1,
                    onClick = { onTabSelected(1) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Text input area
            if (activeInputTab == 0) {
                OutlinedTextField(
                    value = rawResume,
                    onValueChange = onResumeChange,
                    label = { Text("Paste Raw Resume Text [RAW_RESUME]") },
                    placeholder = { Text("Paste existing resume text, work experience, and competencies...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .testTag("raw_resume_input"),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodySmall
                )
            } else {
                OutlinedTextField(
                    value = targetJd,
                    onValueChange = onJdChange,
                    label = { Text("Paste Target Job Description [TARGET_JOB_DESCRIPTION]") },
                    placeholder = { Text("Paste job posting, responsibilities, and required qualifications...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .testTag("target_jd_input"),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Configuration Options
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Humanizer Mode:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    ConfigChip(
                        label = "Executive",
                        selected = humanizerLevel == "Natural Executive",
                        onClick = { onHumanizerLevelChange("Natural Executive") }
                    )
                    ConfigChip(
                        label = "High Burstiness",
                        selected = humanizerLevel == "High Burstiness",
                        onClick = { onHumanizerLevelChange("High Burstiness") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Metric Formula:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    ConfigChip(
                        label = "High Quantifiable",
                        selected = metricDensity == "High",
                        onClick = { onMetricDensityChange("High") }
                    )
                    ConfigChip(
                        label = "Strategic",
                        selected = metricDensity == "Strategic",
                        onClick = { onMetricDensityChange("Strategic") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Run Button / Loading Progress
            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp)),
                        color = Emerald400,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = loadingStep,
                        style = MaterialTheme.typography.bodySmall,
                        color = Emerald400,
                        fontSize = 11.sp
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onClearInputs,
                        modifier = Modifier.minimumInteractiveComponentSize(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Clear")
                    }

                    Button(
                        onClick = onRunOptimization,
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("run_optimization_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RoyalBlue600
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Rounded.RocketLaunch, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Architect & Optimize Resume",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InputTabButton(
    title: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.surface else Color.Transparent,
        label = "tabBg"
    )
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .minimumInteractiveComponentSize(),
        color = bgColor
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = if (selected) RoyalBlue400 else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun ConfigChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (selected) RoyalBlue600 else MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .clickable(onClick = onClick)
            .minimumInteractiveComponentSize()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun PhaseTabsBar(
    activeTab: Int,
    onTabSelect: (Int) -> Unit,
    matchScore: Int,
    humanScore: Int
) {
    ScrollableTabRow(
        selectedTabIndex = activeTab,
        edgePadding = 16.dp,
        containerColor = Color.Transparent,
        contentColor = RoyalBlue400,
        divider = {}
    ) {
        Tab(
            selected = activeTab == 0,
            onClick = { onTabSelect(0) },
            modifier = Modifier.minimumInteractiveComponentSize().testTag("phase1_tab"),
            text = {
                Text(
                    text = "Phase 1: Gap Report ($matchScore%)",
                    fontWeight = if (activeTab == 0) FontWeight.Bold else FontWeight.Normal
                )
            }
        )
        Tab(
            selected = activeTab == 1,
            onClick = { onTabSelect(1) },
            modifier = Modifier.minimumInteractiveComponentSize().testTag("phase2_tab"),
            text = {
                Text(
                    text = "Phase 2: Optimized Resume",
                    fontWeight = if (activeTab == 1) FontWeight.Bold else FontWeight.Normal
                )
            }
        )
        Tab(
            selected = activeTab == 2,
            onClick = { onTabSelect(2) },
            modifier = Modifier.minimumInteractiveComponentSize().testTag("phase3_tab"),
            text = {
                Text(
                    text = "Phase 3: AI-Bypass ($humanScore%)",
                    fontWeight = if (activeTab == 2) FontWeight.Bold else FontWeight.Normal
                )
            }
        )
        Tab(
            selected = activeTab == 3,
            onClick = { onTabSelect(3) },
            modifier = Modifier.minimumInteractiveComponentSize().testTag("bullet_lab_tab"),
            text = {
                Text(
                    text = "Bullet Transformer Lab",
                    fontWeight = if (activeTab == 3) FontWeight.Bold else FontWeight.Normal
                )
            }
        )
    }
}
