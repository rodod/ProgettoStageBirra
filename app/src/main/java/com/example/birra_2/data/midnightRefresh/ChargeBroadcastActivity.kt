package com.example.birra_2.data.midnightRefresh

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.birra_2.data.dataManager.updateFile


class ChargeBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BATTERY_CHANGED) {//la funzione avviene quando riceve l'informazione che il telefono si sta caricando
            val chargingStat = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging = chargingStat == BatteryManager.BATTERY_STATUS_CHARGING ||
                    chargingStat == BatteryManager.BATTERY_STATUS_FULL   //se si sta caricando o se è pieno sotto carica allora aggiorna le informazioni
            if (isCharging) {
                AppUpdateScheduler.scheduleAppUpdate(context)
                updateFile(context as Activity)
            }
        }
    }
}