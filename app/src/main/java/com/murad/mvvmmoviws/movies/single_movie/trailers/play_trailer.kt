package com.murad.mvvmmoviws.movies.single_movie.trailers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.murad.mvvmmoviws.R


class play_trailer : YouTubeBaseActivity() , YouTubePlayer.OnInitializedListener {
    private lateinit var trailerKey:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_trailer)

         trailerKey= intent.extras?.getString("trailer_key")!!

        val youTubeView = findViewById<View>(R.id.youtubeLay) as YouTubePlayerView

        youTubeView.initialize("AIzaSyB-HiGnNr_tFogz5hxnmulxkbZp0uamfBc", this)
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        p1?.loadVideo(trailerKey)
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Toast.makeText(this, "Oh no!"+p1.toString(),Toast.LENGTH_LONG).show();
    }
}