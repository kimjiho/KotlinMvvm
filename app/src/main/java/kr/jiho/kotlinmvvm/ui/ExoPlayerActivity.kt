package kr.jiho.kotlinmvvm.ui

import android.app.PictureInPictureParams
import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import kr.jiho.kotlinmvvm.databinding.ActivityExoPlayerBinding
import kr.jiho.kotlinmvvm.service.PlaybackService

@UnstableApi
class ExoPlayerActivity : AppCompatActivity(), MediaSession.Callback {

    private val binding by lazy { ActivityExoPlayerBinding.inflate(layoutInflater) }

    private var controllerFuture: ListenableFuture<MediaController>? = null

    private val url = "https://vod.cdn.hunet.co.kr/eLearning/CreditBank/ELSC04805/Low/14_01_01.mp4"
    private val artWorkImage = "https://media.gettyimages.com/id/2058229760/photo/lesser-panda-or-red-panda-on-a-tree.jpg?s=2048x2048&w=gi&k=20&c=t2lFaEGhGDV_Wf0-ZPDrPtXqRYsx6q_EAeyFjUZq3CQ="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initPlayer()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.w("DEBUG", "ExoPlayerActivity onNewIntent")
    }

    private fun initPlayer() {
        val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))

        controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture!!.addListener({
            val controller = controllerFuture!!.get()
            binding.playerView.player = controller
            val mediaItem = MediaItem.Builder()
                .setUri(Uri.parse(url))
                .setMediaMetadata(MediaMetadata.Builder()
                    .setTitle("동영상 강의")
                    //.setArtworkUri(Uri.parse(artWorkImage))
                    .build())
                .build()

            controller.setMediaItem(mediaItem)
            controller.prepare()

        }, MoreExecutors.directExecutor())
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        if(isInPictureInPictureMode) {
            // hide fullScreen UI
        }else {
            // Restore fullScreen UI
        }
    }

    override fun onUserLeaveHint() {
        if(binding.playerView.player?.playWhenReady == true) {
            val sourceRectHint = Rect()
            binding.playerView.getGlobalVisibleRect(sourceRectHint)

            val params = PictureInPictureParams.Builder()
                //.setAspectRatio(aspectRatio)
                .setSourceRectHint(sourceRectHint)
                //.setAutoEnterEnabled(true)
                .build()

            enterPictureInPictureMode(params)
        }
    }

    override fun onDestroy() {
        Log.e("DEBUG", "ExoPlayerActivity onDestroy")

        controllerFuture?.let {
            MediaController.releaseFuture(it)
        }

        super.onDestroy()
    }
}