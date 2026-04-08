package com.example.evalrm.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AppNavigator {
    var route: AppRoute by mutableStateOf(AppRoute.List)
        private set

    fun openDetail(locationId: Int) {
        route = AppRoute.Detail(locationId)
    }

    fun backToList() {
        route = AppRoute.List
    }
}

