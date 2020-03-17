package com.techpakka.eerattupetta;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoDetail extends YouTubeFailureRecoveryActivity {

    private String videourl;
    private String videoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        videourl = getIntent().getStringExtra("videourl");
        Uri uri = Uri.parse(videourl);
        videoID = uri.getQueryParameter("v");
        YouTubePlayerView youtube_player_view = findViewById(R.id.youtube_player_view);
        youtube_player_view.initialize("AIzaSyAc9UBp9ANyOBpCHM236xRcX_O0-wTCOzY", this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {

            if (!wasRestored) {
                //player.cueVideo("wKJ9KzGQq0w");
                player.loadVideo(videoID);

        }

    }
    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_player_view);
    }
}
