package ph.digipay.digipayelectroniclearning.ui.admin.video;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.common.utils.SwipeHelper;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;

public class VideoManagementActivity extends BaseActivity {

    private RecyclerView videoRv;
    private VideoListRecyclerAdapter videoListRecyclerAdapter;

    private FirebaseDatabaseHelper<VideoForm> videoFormFirebaseDatabase;
    private List<VideoForm> videoFormList;

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
            videoFormList = itemList;
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        new SwipeHelper(this, videoRv) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        pos -> {
                            final VideoForm videoForm = videoFormList.get(pos);
                            alertDialog.setTitle("Delete Video")
                                    .setMessage("This video will be deleted.")
                                    .setCancelable(false)
                                    .setPositiveButton("Delete", (dialog, which) -> {
                                        videoFormFirebaseDatabase.removeItem(StringConstants.VIDEO_LIST_DB, videoForm.getUid());
                                        videoListRecyclerAdapter.clear();
                                    })
                                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                                    .show();

                        }));
            }
        };

        findViewById(R.id.fab).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), VideoFormActivity.class));
            videoListRecyclerAdapter.clear();
        });
    }
}
