package ph.digipay.digipayelectroniclearning.ui;

import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;

public class TutorialVideoActivity extends BaseActivity {

    private VideoView videoView;
    public static final String TUTS_URL = "https://s3-ap-southeast-1.amazonaws.com/healthguard.digipay.ph/tutorial-test.mp4";
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_video);

        videoView = findViewById(R.id.videoView);
        progressDialog = ProgressDialog.show(this, "", "Buffering video...", true);
        progressDialog.setCancelable(false);

        playVideo();
    }

    private void playVideo()
    {
        try
        {
            getWindow().setFormat(PixelFormat.TRANSLUCENT);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);

            Uri video = Uri.parse(TutorialVideoActivity.TUTS_URL);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);
            videoView.requestFocus();
            videoView.setOnPreparedListener(mp -> {
                progressDialog.dismiss();
                videoView.start();
            });


        }
        catch(Exception e)
        {
            progressDialog.dismiss();
            System.out.println("Video Play Error :"+e.toString());
            finish();
        }

    }


}
