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

package dev.patrickgold.florisboard.app.settings.about

import android.widget.Toast
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Policy
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.patrickgold.florisboard.BuildConfig
import dev.patrickgold.florisboard.R
import dev.patrickgold.florisboard.app.LocalNavController
import dev.patrickgold.florisboard.app.Routes
import dev.patrickgold.florisboard.clipboardManager
import dev.patrickgold.florisboard.lib.compose.FlorisScreen
import dev.patrickgold.florisboard.lib.util.launchUrl
import dev.patrickgold.jetpref.datastore.ui.Preference
import org.florisboard.lib.android.stringRes
import org.florisboard.lib.compose.stringRes

@Composable
fun AboutScreen() = FlorisScreen {
    title = stringRes(R.string.about__title)

    val navController = LocalNavController.current
    val context = LocalContext.current
    val clipboardManager by context.clipboardManager()

    val appVersion = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"

    content {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(Brush.verticalGradient(listOf(Color(0xFF120D06), Color(0xFF512DA8))))
                .padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.mathra_logo),
                contentDescription = "Mathra Keyboard logo",
                modifier = Modifier.requiredSize(150.dp),
            )
            Text(
                text = stringRes(R.string.app_name),
                color = Color(0xFFFFC107),
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 12.dp),
            )
            Text(
                text = stringRes(R.string.mathra__about__made_by),
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp),
            )
            Text(
                text = stringRes(R.string.mathra__about__description),
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1D1850))
                .padding(16.dp),
        ) {
            Text(
                text = stringRes(R.string.mathra__about__features),
                color = Color(0xFFFFC107),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Text(
                text = stringRes(R.string.mathra__about__features_list),
                color = Color.White,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
        Preference(
            icon = Icons.Default.Person,
            title = stringRes(R.string.mathra__about__creator),
            summary = "Denuwan Akash (දෙනුවන් ආකාශ්)",
            onClick = { context.launchUrl(R.string.florisboard__repo_url) },
        )
        Preference(
            icon = Icons.Default.MusicNote,
            title = "TikTok",
            summary = stringRes(R.string.mathra__about__tiktok),
            onClick = {
                try {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tiktok.com/@mathra_quotes_1"))
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    )
                } catch (_: Throwable) {
                }
            },
        )
        Text(
            text = stringRes(R.string.mathra__about__credit),
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
        )
        Preference(
            icon = Icons.Outlined.Info,
            title = stringRes(R.string.about__version__title),
            summary = appVersion,
            onClick = {
                try {
                    clipboardManager.addNewPlaintext(appVersion)
                    Toast.makeText(context, R.string.about__version_copied__title, Toast.LENGTH_SHORT).show()
                } catch (e: Throwable) {
                    Toast.makeText(
                        context,
                        context.stringRes(R.string.about__version_copied__error, "error_message" to e.message),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            },
        )
        Preference(
            icon = Icons.Default.History,
            title = stringRes(R.string.about__changelog__title),
            summary = stringRes(R.string.about__changelog__summary),
            onClick = { context.launchUrl(R.string.florisboard__changelog_url, "version" to BuildConfig.VERSION_NAME) },
        )
        Preference(
            icon = Icons.Default.Code,
            title = stringRes(R.string.about__repository__title),
            summary = stringRes(R.string.about__repository__summary),
            onClick = { context.launchUrl(R.string.florisboard__repo_url) },
        )
        Preference(
            icon = Icons.Outlined.Policy,
            title = stringRes(R.string.about__privacy_policy__title),
            summary = stringRes(R.string.about__privacy_policy__summary),
            onClick = { context.launchUrl(R.string.florisboard__privacy_policy_url) },
        )
        Preference(
            icon = Icons.Outlined.Description,
            title = stringRes(R.string.about__project_license__title),
            summary = stringRes(R.string.about__project_license__summary, "license_name" to "Apache 2.0"),
            onClick = { navController.navigate(Routes.Settings.ProjectLicense) },
        )
        Preference(
            icon = Icons.Outlined.Description,
            title = stringRes(id = R.string.about__third_party_licenses__title),
            summary = stringRes(id = R.string.about__third_party_licenses__summary),
            onClick = { navController.navigate(Routes.Settings.ThirdPartyLicenses) },
        )
    }
}
