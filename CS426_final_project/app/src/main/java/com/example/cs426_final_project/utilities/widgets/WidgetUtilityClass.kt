package com.example.cs426_final_project.utilities.widgets

import android.appwidget.AppWidgetManager
import android.content.ComponentName

import android.content.Context


class WidgetUtilityClass() {
    fun createWidget(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        if (appWidgetManager.isRequestPinAppWidgetSupported) {
            appWidgetManager.requestPinAppWidget(
                ComponentName(context, AppWidgetProvider::class.java),
                null,
                null
            )
        }
    }
}