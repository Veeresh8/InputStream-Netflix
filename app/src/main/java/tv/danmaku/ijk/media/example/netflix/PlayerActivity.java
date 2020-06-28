package tv.danmaku.ijk.media.example.netflix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;

import com.google.android.exoplayer.TimeRange;
import com.google.android.exoplayer.chunk.Format;
import com.google.android.exoplayer.util.Util;
import com.pano.media.player.R;

import tv.danmaku.ijk.media.exo.demo.player.DashRendererBuilder;
import tv.danmaku.ijk.media.exo.demo.player.DemoPlayer;

public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "PlayerActivity";
    private SurfaceView surfaceView;

    public static void start(Context context) {
        Intent starter = new Intent(context, PlayerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2);

        surfaceView = (SurfaceView) findViewById(R.id.videoView);

        DemoPlayer player = new DemoPlayer(getDashRenderer());
        player.addListener(new DemoPlayer.Listener() {
            @Override
            public void onStateChanged(boolean playWhenReady, int playbackState) {
                Log.d(TAG, "onStateChanged() called with: playWhenReady = [" + playWhenReady + "], playbackState = [" + playbackState + "]");
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                Log.d(TAG, "onVideoSizeChanged() called with: width = [" + width + "], height = [" + height + "], unappliedRotationDegrees = [" + unappliedRotationDegrees + "], pixelWidthHeightRatio = [" + pixelWidthHeightRatio + "]");
            }
        });
        player.setInfoListener(new DemoPlayer.InfoListener() {
            @Override
            public void onVideoFormatEnabled(Format format, int trigger, long mediaTimeMs) {
                Log.d(TAG, "onVideoFormatEnabled() called with: format = [" + format + "], trigger = [" + trigger + "], mediaTimeMs = [" + mediaTimeMs + "]");
            }

            @Override
            public void onAudioFormatEnabled(Format format, int trigger, long mediaTimeMs) {
                Log.d(TAG, "onAudioFormatEnabled() called with: format = [" + format + "], trigger = [" + trigger + "], mediaTimeMs = [" + mediaTimeMs + "]");
            }

            @Override
            public void onDroppedFrames(int count, long elapsed) {
                Log.d(TAG, "onDroppedFrames() called with: count = [" + count + "], elapsed = [" + elapsed + "]");
            }

            @Override
            public void onBandwidthSample(int elapsedMs, long bytes, long bitrateEstimate) {
                Log.d(TAG, "onBandwidthSample() called with: elapsedMs = [" + elapsedMs + "], bytes = [" + bytes + "], bitrateEstimate = [" + bitrateEstimate + "]");
            }

            @Override
            public void onLoadStarted(int sourceId, long length, int type, int trigger, Format format, long mediaStartTimeMs, long mediaEndTimeMs) {
                Log.d(TAG, "onLoadStarted() called with: sourceId = [" + sourceId + "], length = [" + length + "], type = [" + type + "], trigger = [" + trigger + "], format = [" + format + "], mediaStartTimeMs = [" + mediaStartTimeMs + "], mediaEndTimeMs = [" + mediaEndTimeMs + "]");
            }

            @Override
            public void onLoadCompleted(int sourceId, long bytesLoaded, int type, int trigger, Format format, long mediaStartTimeMs, long mediaEndTimeMs, long elapsedRealtimeMs, long loadDurationMs) {
                Log.d(TAG, "onLoadCompleted() called with: sourceId = [" + sourceId + "], bytesLoaded = [" + bytesLoaded + "], type = [" + type + "], trigger = [" + trigger + "], format = [" + format + "], mediaStartTimeMs = [" + mediaStartTimeMs + "], mediaEndTimeMs = [" + mediaEndTimeMs + "], elapsedRealtimeMs = [" + elapsedRealtimeMs + "], loadDurationMs = [" + loadDurationMs + "]");
            }

            @Override
            public void onDecoderInitialized(String decoderName, long elapsedRealtimeMs, long initializationDurationMs) {
                Log.d(TAG, "onDecoderInitialized() called with: decoderName = [" + decoderName + "], elapsedRealtimeMs = [" + elapsedRealtimeMs + "], initializationDurationMs = [" + initializationDurationMs + "]");
            }

            @Override
            public void onAvailableRangeChanged(int sourceId, TimeRange availableRange) {
                Log.d(TAG, "onAvailableRangeChanged() called with: sourceId = [" + sourceId + "], availableRange = [" + availableRange + "]");
            }
        });
        player.prepare();
        player.setSurface(new ShaderSurfaceView(this).getHolder().getSurface());
        player.setSelectedTrack(2, 0);
        player.setPlayWhenReady(true);
    }

    private DemoPlayer.RendererBuilder getDashRenderer() {
        return new DashRendererBuilder(this, Util.getUserAgent(this, "ijkmediademo"),
                NetflixManifestGenerator.getDashManifestPath(),
                new NflxWidevineDrmCallback());
    }
}