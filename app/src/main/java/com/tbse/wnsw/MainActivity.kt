package com.tbse.wnsw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tbse.wnsw.models.AccessPoint
import com.tbse.wnsw.ui.aplist.AccessPointList
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProvider
import com.tbse.wnsw.ui.aplist.preview.AccessPointPreviewProviderMany
import com.tbse.wnsw.ui.theme.NewWNSWTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewWNSWTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AccessPointList(
                        itemViewStates =
                        AccessPointPreviewProviderMany().values.toList()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NewWNSWTheme {
        AccessPointList(
            itemViewStates =
            AccessPointPreviewProviderMany().values.toList()
        )
    }
}