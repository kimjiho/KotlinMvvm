package kr.jiho.kotlinmvvm.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSourceBitmapLoader
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CacheBitmapLoader
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ConnectionResult
import androidx.media3.session.MediaSession.ConnectionResult.AcceptedResultBuilder
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import kr.jiho.kotlinmvvm.ui.ExoPlayerActivity

/**
 * ExoPlayer를 사용하는 MediaSessionService를 상속받아 구현한 PlaybackService 클래스
 * Created by Jiho on 2024-04-02.
 */
@OptIn(UnstableApi::class)
class PlaybackService: MediaSessionService() {

    private var mediaSession: MediaSession? = null

    private val saveToFavorites = "save_to_favorites"

    private val stopMediaService = "stop_media_service"

    private val ffMediaService = "forward_media_service"

    private val mediaServiceId = "hunet_media_service_id"

    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        val forwardPlayer = object: ForwardingPlayer(player) {
            override fun play() {
                //Add custom logic
                super.play()
            }
        }

        val likeButton = CommandButton.Builder()
            .setDisplayName("Like")
            .setIconResId(android.R.drawable.ic_menu_upload)
            .setSessionCommand(SessionCommand(SessionCommand.COMMAND_CODE_SESSION_SET_RATING))
            .build()
        val forwardButton = CommandButton.Builder()
            .setDisplayName("Forward")
            .setIconResId(android.R.drawable.ic_media_ff)
            .setSessionCommand(SessionCommand(ffMediaService, Bundle()))
            .build()
        val favoriteButton = CommandButton.Builder()
            .setDisplayName("Close Service")
            .setIconResId(android.R.drawable.ic_menu_close_clear_cancel)
            .setSessionCommand(SessionCommand(stopMediaService, Bundle()))
            .build()

        mediaSession = MediaSession.Builder(this, forwardPlayer)
            .setId(mediaServiceId)
            .setSessionActivity(getSingleTopActivity())
            .setCustomLayout(ImmutableList.of(likeButton, forwardButton, favoriteButton))
            .setCallback(PlaybackSessionCallback())
            .setBitmapLoader(CacheBitmapLoader(DataSourceBitmapLoader(this)))
            .build()
    }

    private fun getSingleTopActivity(): PendingIntent {
        val nextIntent = Intent(applicationContext, ExoPlayerActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingFlag = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        return PendingIntent.getActivity(
            applicationContext, 0, nextIntent, pendingFlag)
    }

    private inner class PlaybackSessionCallback: MediaSession.Callback {

        override fun onConnect(
            session: MediaSession,
            controller: MediaSession.ControllerInfo
        ): ConnectionResult {
            Log.w("PlaybackService", "onConnect controller: $controller")

            val sessionCommands = ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
                .add(SessionCommand(stopMediaService, Bundle.EMPTY))
                .add(SessionCommand(ffMediaService, Bundle.EMPTY))
                .build()
            return AcceptedResultBuilder(session)
                .setAvailableSessionCommands(sessionCommands)
                .build()
        }

        override fun onCustomCommand(
            session: MediaSession,
            controller: MediaSession.ControllerInfo,
            customCommand: SessionCommand,
            args: Bundle
        ): ListenableFuture<SessionResult> {
            Log.e("DEBUG", "onCustomCommand ${customCommand.customAction}")
            val player = session.player

            if (customCommand.customAction == stopMediaService) {
                player.stop()
                stopForeground(Service.STOP_FOREGROUND_REMOVE)

            } else if (customCommand.customAction == ffMediaService) {
                player.seekForward()
            }

            return Futures.immediateFuture(
                SessionResult(SessionResult.RESULT_SUCCESS)
            )
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        Log.e("DEBUG", "PlaybackService onTaskRemoved")

        val player = mediaSession?.player!!
        if (!player.playWhenReady || player.playbackState == Player.STATE_ENDED) {
            stopSelf()
        }
    }

    override fun onDestroy() {
        Log.e("DEBUG", "PlaybackService onDestroy")

        mediaSession?.run {
            player.release()
            release()
            //mediaSession = null
        }
        super.onDestroy()
    }
}