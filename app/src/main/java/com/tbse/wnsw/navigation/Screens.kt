package com.tbse.wnsw.navigation

import androidx.navigation.NavHostController
import com.tbse.wnsw.util.Action
import com.tbse.wnsw.util.Constants.LIST_SCREEN

/**
 * Created by toddsmith on 12/11/21.
 * Copyright TBSE 2022
 */
class Screens(navController: NavHostController) {
    val listOfAps: (Action) -> Unit = { action ->
        navController.navigate(
            "list/${action.name}"
        ) {
            popUpTo(LIST_SCREEN)
        }
    }
    val details: (String) -> Unit = { bssid ->
        navController.navigate(
            "detail/$bssid"
        ) {

        }
    }
}
