package com.example.evalrm.presentation.navigation

sealed interface AppRoute {
    data object List : AppRoute
    data class Detail(val locationId: Int) : AppRoute
}

