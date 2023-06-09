package com.example.birra_2

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import java.util.Calendar


object AppUpdateScheduler {
    private const val JOB_ID = 1
    fun scheduleAppUpdate(context: Context) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val componentName = ComponentName(context, ChargeBroadcastReceiver::class.java)
        val builder = JobInfo.Builder(JOB_ID, componentName)
        builder.setRequiresCharging(true) // Richiede che il telefono sia in carica
        builder.setPersisted(true) // Sopravvive ai riavvii del dispositivo

        // Specifica il momento esatto per eseguire il lavoro
        builder.setMinimumLatency(millisecondsToMidnight)
        jobScheduler.schedule(builder.build())
    }

    private val millisecondsToMidnight: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            calendar[Calendar.HOUR_OF_DAY] = 0
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            val currentTime = System.currentTimeMillis()
            val midnightTime = calendar.timeInMillis
            return midnightTime - currentTime
        }
}