/*
 * Copyright (C) 2021-2025 The FlorisBoard Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.patrickgold.florisboard.app.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Gesture
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.patrickgold.florisboard.R
import dev.patrickgold.florisboard.app.LocalNavController
import dev.patrickgold.florisboard.app.Routes
import dev.patrickgold.florisboard.lib.compose.FlorisScreen
import dev.patrickgold.florisboard.lib.util.InputMethodUtils
import org.florisboard.lib.compose.stringRes

private val MathraGold = Color(0xFFFFC107)
private val MathraDeep = Color(0xFF120D06)
private val MathraPurple = Color(0xFF512DA8)

@Composable
fun HomeScreen() = FlorisScreen {
    title = stringRes(R.string.settings__home__title)
    navigationIconVisible = false
    previewFieldVisible = true

    val navController = LocalNavController.current
    val context = LocalContext.current

    content {
        val isEnabled by InputMethodUtils.observeIsFlorisboardEnabled(foregroundOnly = true)
        val isSelected by InputMethodUtils.observeIsFlorisboardSelected(foregroundOnly = true)

        val statusText = when {
            !isEnabled -> stringRes(R.string.mathra__status_enable)
            !isSelected -> stringRes(R.string.mathra__status_select)
            else -> stringRes(R.string.mathra__status_ready)
        }
        val statusColor = when {
            !isEnabled -> Color(0xFFFF5252)
            !isSelected -> Color(0xFFFFB300)
            else -> Color(0xFF69F0AE)
        }
        val onStatusClick: () -> Unit = {
            when {
                !isEnabled -> InputMethodUtils.showImeEnablerActivity(context)
                !isSelected -> InputMethodUtils.showImePicker(context)
            }
        }

        // Hero card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(Brush.verticalGradient(listOf(MathraDeep, MathraPurple)))
                .clickable(enabled = !(isEnabled && isSelected), onClick = onStatusClick)
                .padding(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.mathra_logo),
                    contentDescription = "Mathra Keyboard logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(120.dp),
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = stringRes(R.string.app_name),
                    color = MathraGold,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = stringRes(R.string.mathra__tagline),
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp),
                )
                Spacer(Modifier.height(14.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Color.Black.copy(alpha = 0.35f))
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                ) {
                    Box(
                        Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(50))
                            .background(statusColor),
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(text = statusText, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
            }
        }

        val tiles = listOf(
            Triple(Icons.Outlined.Palette, R.string.settings__theme__title, { navController.navigate(Routes.Settings.Theme) }),
            Triple(Icons.Outlined.Keyboard, R.string.settings__keyboard__title, { navController.navigate(Routes.Settings.Keyboard) }),
            Triple(Icons.Default.Language, R.string.settings__localization__title, { navController.navigate(Routes.Settings.Localization) }),
            Triple(Icons.Default.SmartButton, R.string.settings__smartbar__title, { navController.navigate(Routes.Settings.Smartbar) }),
            Triple(Icons.Default.Spellcheck, R.string.settings__typing__title, { navController.navigate(Routes.Settings.Typing) }),
            Triple(Icons.Default.Gesture, R.string.settings__gestures__title, { navController.navigate(Routes.Settings.Gestures) }),
            Triple(Icons.AutoMirrored.Outlined.Assignment, R.string.settings__clipboard__title, { navController.navigate(Routes.Settings.Clipboard) }),
            Triple(Icons.Default.SentimentSatisfiedAlt, R.string.settings__media__title, { navController.navigate(Routes.Settings.Media) }),
            Triple(Icons.Default.Extension, R.string.ext__home__title, { navController.navigate(Routes.Ext.Home) }),
            Triple(Icons.Outlined.Build, R.string.settings__other__title, { navController.navigate(Routes.Settings.Other) }),
            Triple(Icons.Outlined.Info, R.string.about__title, { navController.navigate(Routes.Settings.About) }),
        )
        Column(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            tiles.chunked(2).forEach { rowTiles ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    rowTiles.forEach { (icon, titleRes, onClick) ->
                        DashboardTile(
                            icon = icon,
                            title = stringRes(titleRes),
                            onClick = onClick,
                            modifier = Modifier.weight(1f),
                        )
                    }
                    if (rowTiles.size == 1) Spacer(Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun DashboardTile(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF2A1F0E), Color(0xFF1D1850))))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MathraGold, modifier = Modifier.size(28.dp))
        Text(text = title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}
