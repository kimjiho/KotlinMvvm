package kr.jiho.kotlinmvvm.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import kr.jiho.kotlinmvvm.R
import kr.jiho.kotlinmvvm.ui.MainActivity
import kotlin.concurrent.thread

class DownloadService : Service() {

    private val CHANNEL_ID = "DOWNLOAD_SERVICE"
    private val CHANNEL_NAME = "Download_Channel"
    private val CHANNEL_DESCRIPTION = "This id Download Service"
    private val NOTIFICATION_DOWNLOAD_ID = 1
    private val NOTIFICATION_COMPLETE_ID = 2

    private val notificationManager
        get() = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    override fun onCreate() {
        super.onCreate()
        Log.d("DEBUG", "service onCreate")
        registerDefaultNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.w("DEBUG", "Start Service ===>>")

        startForeground(NOTIFICATION_DOWNLOAD_ID, createDownloadingNotification(0))

        thread {
            for(i in 1..100) {
                Thread.sleep(100)
                updateProgress(i)
            }

            // todo download
            // http://vod.cdn.hunet.co.kr/eLearning/Hunet/HLSC55725/01_05.mp4

            stopForeground(true)
            stopSelf()
            notificationManager.notify(NOTIFICATION_COMPLETE_ID, createCompleteNotification())
        }

        //return super.onStartCommand(intent, flags, startId)
        return START_REDELIVER_INTENT
    }

    private fun updateProgress(@IntRange(from = 0L, to = 100L) progress: Int) {
        notificationManager.notify(NOTIFICATION_DOWNLOAD_ID, createDownloadingNotification(progress))
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DEBUG", "service onDestroy")
    }

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("DEBUG", "service onBind")
        return null
    }

    private fun registerDefaultNotificationChannel() {
        notificationManager.createNotificationChannel(createDefaultNotificationChannel())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createDefaultNotificationChannel() =
        NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW).apply {
            description = CHANNEL_DESCRIPTION
            this.setShowBadge(true)
            this.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }

    private fun createDownloadingNotification(@IntRange(from = 0L, to = 100L) progress: Int) =
        NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle("Download video...")
            setContentText("Wait!")
            setSmallIcon(R.mipmap.ic_launcher_round)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            setContentIntent(
                PendingIntent.getActivity(
                    this@DownloadService, 0, Intent(this@DownloadService, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }, 0
                )
            )

            setProgress(100, progress, false)
        }.build()

    private fun createCompleteNotification() = NotificationCompat.Builder(this, CHANNEL_ID).apply {
        setContentTitle("Download complete!")
        setContentText("Nice 🚀")
        setSmallIcon(R.drawable.ic_launcher_background)
        setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        setContentIntent(
            PendingIntent.getActivity(
                this@DownloadService, 0, Intent(this@DownloadService, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }, 0
            )
        )
    }.build()
}