package ph.digipay.digipayelectroniclearning.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private LinearLayout tutsVideoLL;
    private LinearLayout agentTestLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tutsVideoLL = findViewById(R.id.tuts_video_ll);
        agentTestLL = findViewById(R.id.agent_test_ll);

        tutsVideoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TutorialVideoActivity.class));
            }
        });

        agentTestLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QuestionnaireActivity.class));
            }
        });




    }
}
