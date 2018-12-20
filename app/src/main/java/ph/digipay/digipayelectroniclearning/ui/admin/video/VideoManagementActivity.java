package ph.digipay.digipayelectroniclearning.ui.admin.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;

public class VideoManagementActivity extends BaseActivity {

    private RecyclerView videoRv;
    private VideoListRecyclerAdapter videoListRecyclerAdapter;

    private FirebaseDatabaseHelper<VideoForm> videoFormFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_management);

        videoRv = findViewById(R.id.video_list_rv);

        videoFormFirebaseDatabase = new FirebaseDatabaseHelper<>( VideoForm.class);

        videoFormFirebaseDatabase.fetchItems(StringConstants.VIDEO_LIST_DB, itemList -> {
            videoListRecyclerAdapter = new VideoListRecyclerAdapter(itemList);
            videoRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getApplicationContext()));
            videoRv.setAdapter(videoListRecyclerAdapter);
        });

        findViewById(R.id.fab).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), VideoFormActivity.class));
        });
    }
}
