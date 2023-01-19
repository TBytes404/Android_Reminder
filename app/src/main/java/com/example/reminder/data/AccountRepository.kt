package com.example.reminder.data

class AccountRepository {

    fun getName(): String? = AccountDataProvider.account.name

    fun setName(name: String?) {
        AccountDataProvider.account.name = name?.trim()
    }

    fun getThemePreference(): ThemePreferences = AccountDataProvider.account.themePreference

    fun setThemePreference(preference: ThemePreferences) {
        AccountDataProvider.account.themePreference = preference
    }
}