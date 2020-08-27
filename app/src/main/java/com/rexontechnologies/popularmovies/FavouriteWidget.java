package com.rexontechnologies.popularmovies;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class FavouriteWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favourite_widget);
        SharedPreferences sharedPreferences = context.getSharedPreferences(DetailActivity.PREF,Context.MODE_PRIVATE);
        String defaultValue ="No Movies";
        CharSequence movieName  =sharedPreferences.getString(DetailActivity.MOVIE_NAME,defaultValue);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context,FavouriteWidgetService.class);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        appWidgetManager =AppWidgetManager.getInstance(context);
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,FavouriteWidget.class));
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

