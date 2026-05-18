package com.appmoviles.gymrat20.ui.detail

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.appmoviles.gymrat20.ui.theme.GymRat20Theme

class DetalleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val videoResId = intent.getIntExtra(EXTRA_VIDEO_RES_ID, 0)

        setContent {
            GymRat20Theme {
                PantallaDetalleVideo(
                    videoResId = videoResId,
                    onBack = { finish() }
                )
            }
        }
    }

    companion object {
        const val EXTRA_VIDEO_RES_ID = "extra_video_res_id"
    }
}

@Composable
fun PantallaDetalleVideo(
    videoResId: Int,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color.Companion.Black)
    ) {

        AndroidView(
            modifier = Modifier.Companion.fillMaxSize(),
            factory = {
                VideoView(context).apply {
                    val uri = Uri.parse("android.resource://${context.packageName}/$videoResId")
                    setVideoURI(uri)

                    setOnPreparedListener { mediaPlayer ->
                        mediaPlayer.isLooping = true
                        start()
                    }
                }
            }
        )

        Surface(
            modifier = Modifier.Companion
                .align(Alignment.Companion.TopStart)
                .padding(16.dp),
            shape = CircleShape,
            color = Color.Companion.Black.copy(alpha = 0.55f)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.Companion.White
                )
            }
        }
    }
}