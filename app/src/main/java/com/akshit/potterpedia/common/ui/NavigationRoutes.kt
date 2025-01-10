package com.akshit.potterpedia.common.ui

enum class NavigationRoutes(val route: String) {
    /**
     * Navigate to LandingScreen
     */
    LANDING_SCREEN("LandingScreen"),

    /**
     * Navigate to CharacterInfoScreen
     */
    CHARACTER_INFO_SCREEN("CharacterInfoScreen/{characterId}"),

    /**
     * Navigate to InfoHelpScreen
     */
    HELP_INFO_SCREEN("HelpInfoScreen"),
}
fun buildScreen2Route(id: String): String {
    return "CharacterInfoScreen/$id"
}
