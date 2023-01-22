package com.example.reminder.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private const val ACCOUNT_PREFERENCES_NAME = "account_preferences"

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = ACCOUNT_PREFERENCES_NAME)

class AccountRepository(private val context: Context) {
    companion object {
        private val ACCOUNT_NAME = stringPreferencesKey("account_name")
        private val PREFERS_DARK_THEME = booleanPreferencesKey("prefers_dark_theme")
    }

    val account: Flow<Account> = context.datastore.data.map {
        val name = it[ACCOUNT_NAME]
        Account(
            name = if (name?.trim() == "") null else name,
            prefersDarkTheme = it[PREFERS_DARK_THEME] ?: false
        )
    }

//    suspend fun saveAccount(account: Account) {
//        context.datastore.edit {
//            it[ACCOUNT_NAME] = account.name ?: ""
//            it[PREFERS_DARK_THEME] = account.prefersDarkTheme
//        }
//    }

//    val accountName: Flow<String?> = context.datastore.data.catch {
//        if (it is IOException) {
//            it.printStackTrace()
//            emit(emptyPreferences())
//        } else {
//            throw it
//        }
//    }.map {
//        val name = it[ACCOUNT_NAME]
//        if (name?.trim() == "") null else name
//    }

    suspend fun saveAccountName(name: String?) {
        context.datastore.edit { it[ACCOUNT_NAME] = name ?: "" }
    }

//    val prefersDarkTheme: Flow<Boolean> = context.datastore.data.catch {
//        if (it is IOException) {
//            it.printStackTrace()
//            emit(emptyPreferences())
//        } else {
//            throw it
//        }
//    }.map { it[PREFERS_DARK_THEME] ?: false }

    suspend fun saveThemePreference(prefersDarkTheme: Boolean) {
        context.datastore.edit { it[PREFERS_DARK_THEME] = prefersDarkTheme }
    }
}