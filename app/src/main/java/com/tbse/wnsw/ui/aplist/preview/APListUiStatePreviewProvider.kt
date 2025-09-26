package com.tbse.wnsw.ui.aplist.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.tbse.wnsw.ui.aplist.APListUiState

class APListUiStatePreviewProvider : PreviewParameterProvider<APListUiState> {
    override val values: Sequence<APListUiState> = sequenceOf(
        APListUiState.NoAPs,
        APListUiState.HasListOfAPs(
            aps = AccessPointPreviewProviderMany().values.toList()
        )
    )
}
