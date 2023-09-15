package com.example.cs426_final_project.worker

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cs426_final_project.widgets.updateAppWidget

class WidgetUpdateWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {
    override fun doWork(): Result {
        // Implement widget update logic here
        // This can include fetching data and updating the widget's views

        // Call the update method of your AppWidgetProvider
        val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(applicationContext, AppWidgetProvider::class.java))
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(applicationContext, appWidgetManager, appWidgetId)
        }

        // Indicate whether the task was successful
        return Result.success()
    }
}