package com.tbse.wnsw.ui.need_permissions

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.tbse.wnsw.R
import com.tbse.wnsw.ui.need_permissions.preview.OnClickLambdaPreviewProvider

/**
 * Created by toddsmith on 5/20/21.
 * Copyright TBSE 2022
 */
@Composable
@Preview
fun NeedPermissionsPage(
    @PreviewParameter(
        provider = OnClickLambdaPreviewProvider::class
    )
    onPermissionsClick: () -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            Text("Need permissions")
            Button(
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = colorResource(id = R.color.blue),
                    contentColor = colorResource(id = R.color.black)
                ),
                onClick = onPermissionsClick,
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Text("Grant Permissions")
            }
        }
    }
}