package ph.digipay.digipayelectroniclearning.ui;

import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;

public class VideoPlayerActivity extends BaseActivity {

    private VideoView videoView;
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_video);

        videoView = findViewById(R.id.videoView);
        progressDialog = ProgressDialog.show(this, "", "Buffering video...", true);
        progressDialog.setCancelable(false);

        try {
            getWindow().setFormat(PixelFormat.TRANSLUCENT);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String url = bundle.getString(StringConstants.VIDEO_URL);
                assert url != null;
                Uri video = Uri.parse(url);
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(video);
                videoView.requestFocus();
                videoView.setOnPreparedListener(mp -> {
                    progressDialog.dismiss();
                    videoView.start();
                });
            }


        } catch (Exception e) {
            progressDialog.dismiss();
            System.out.println("Video Play Error :" + e.toString());
            finish();
        }
    }

}
