package com.tbse.wnsw.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbse.wnsw.domain.repositories.APRepository
import com.tbse.wnsw.models.AccessPointUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by toddsmith on 12/11/21.
 * Copyright TBSE 2022
 */
@HiltViewModel
class APViewModel @Inject constructor(
    private val repository: APRepository,
) : ViewModel() {

    private val _allAPs = MutableStateFlow<List<AccessPointUI>>(emptyList())

    val allAPs: StateFlow<List<AccessPointUI>> = _allAPs

    fun getAllAPs() {
        viewModelScope.launch {
            repository.getAllAps.collect {
                _allAPs.value = it.map { ap ->
                    AccessPointUI(
                        BSSID = ap.bssid,
                        SSID = ap.ssid,
                        capabilities = "cap",
                        frequency = 1,
                        level = 1,
                        strength = 1,
                        channel = 1,
                        latitude = 1.0,
                        longitude = 1.0,
                        isSuggested = true
                    )
                }
            }
        }
    }

}