package com.example.cs426_final_project.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cs426_final_project.api.FeedApi
import com.example.cs426_final_project.models.posts.FeedResponse
import com.example.cs426_final_project.models.posts.TimelineFeedResponse
import com.example.cs426_final_project.utilities.ImageUtilityClass
import com.example.cs426_final_project.utilities.ApiUtilityClass
import retrofit2.Call


// Worker class to perform background tasks

class UpdateWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Check for updates (e.g., from an API)
        val updatesAvailable = checkForUpdates()

        if (updatesAvailable) {
            // Download new images and update local storage
            downloadAndSaveImages()
        }

        // Indicate whether the task was successful
        return Result.success()
    }

    private fun checkForUpdates(): Boolean {
        return true
    }

    private fun downloadAndSaveImages() {
        //debug
//        println("Oh no, oh no, I am in downloadAndSaveImages")

        val feedApi = ApiUtilityClass.getApiClient(context).create(FeedApi::class.java)
        val call = feedApi.timelineWidget
        call.enqueue(object : retrofit2.Callback<List<TimelineFeedResponse> > {
            override fun onResponse(
                call: Call<List<TimelineFeedResponse> >,
                response: retrofit2.Response<List<TimelineFeedResponse> >
            ) {
                if (response.isSuccessful) {
                    val urls = getCurrentUrls()
                    val feeds = response.body()
                    if (feeds != null) {
                        for (feed in feeds) {
                            /*
                            val url = feed.fields?.imageLink
                            if (url != null) {
                                urls.add(url)
                            }
                            */
                            val url = feed?.imageLink
                            if (url != null) {
                                urls.add(url)
                            }
                        }
                    }

                    // store urls to shared preferences
                    val sharedPreferences = context.getSharedPreferences(
                        "IMAGE_URLS",
                        Context.MODE_PRIVATE
                    )
                    val editor = sharedPreferences.edit()
                    editor.putStringSet("urls", urls.toSet())
                    editor.apply()
                } else {
                    ApiUtilityClass.debug(response)
                }
            }

            override fun onFailure(
                call: Call<List<TimelineFeedResponse> >,
                t: Throwable
            ) {
                t.printStackTrace()
            }
        })
    }

    private fun getCurrentUrls(): HashSet<String> {
        val sharedPreferences = context.getSharedPreferences(
            "IMAGE_URLS",
            Context.MODE_PRIVATE
        )
        // get urls from string set of shared preferences
        val urls = sharedPreferences.getStringSet("urls", null)
        if (urls != null) {
            return urls.toHashSet()
        }
        return HashSet()

    }
}
