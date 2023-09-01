package com.example.cs426_final_project.utilities

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.ComponentName
import android.os.Build

import android.content.Context


class WidgetUtilityClass() {
    fun createWidget(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        if (appWidgetManager.isRequestPinAppWidgetSupported) {
            appWidgetManager.requestPinAppWidget(
                ComponentName(context, AppWidgetProviderInfo::class.java),
                null,
                null
            )
        }
    }
}