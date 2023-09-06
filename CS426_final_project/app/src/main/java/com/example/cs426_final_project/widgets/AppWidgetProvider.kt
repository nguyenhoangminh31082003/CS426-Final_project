package com.example.cs426_final_project.widgets



import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.SizeF
import android.widget.RemoteViews
import com.example.cs426_final_project.R


/**
 * Implementation of App Widget functionality.
 */
class AppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }



    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        // Get the new sizes.
        val sizes = newOptions?.getParcelableArrayList<SizeF>(
            AppWidgetManager.OPTION_APPWIDGET_SIZES
        )
        // Check that the list of sizes is provided by the launcher.
        if (sizes.isNullOrEmpty()) {
            return
        }
        // Map the sizes to the RemoteViews that you want.


        val remoteViews = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            fun createRemoteViews(size: SizeF?): RemoteViews? {
                return when (size?.width?.toInt()) {
                    100 -> RemoteViews(context?.packageName, R.layout.app_widget_provider)
                    else -> RemoteViews(context?.packageName, R.layout.app_widget_provider)
                }

            }
            RemoteViews(sizes.associateWith(::createRemoteViews))
        } else {
            TODO("VERSION.SDK_INT < S")
        }
        appWidgetManager?.updateAppWidget(appWidgetId, remoteViews)


    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    // keep the aspect ratio of the content inside the widget

}
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {




    val intent = Intent(context, AppWidgetProvider::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    val remoteViews = RemoteViews(context.packageName, R.layout.app_widget_provider)



    appWidgetManager.updateAppWidget(appWidgetId, remoteViews)


}