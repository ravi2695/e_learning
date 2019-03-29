package com.example.ravi.species;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class speciesVideo extends AppCompatActivity {

    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer exoPlayer;
    Uri uri;


    @Override
    protected void onPause() {
        super.onPause();
        if(isFinishing())
        {
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_species_video);

        Intent i=getIntent();
        String videourl=i.getStringExtra("message");

        simpleExoPlayerView=findViewById(R.id.speciesVideoView);

        try {

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            uri = Uri.parse(videourl);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource videoSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);

            simpleExoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(videoSource);
            exoPlayer.setPlayWhenReady(true);
        }
        catch (Exception e)
        {
            Log.e("error","exception in video"+e.toString());
        }

    }
}
