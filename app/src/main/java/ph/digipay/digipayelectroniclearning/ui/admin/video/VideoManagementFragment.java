package ph.digipay.digipayelectroniclearning.ui.admin.video;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseFragment;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.common.utils.SwipeHelper;
import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.FirebaseDatabaseHelper;

public class VideoManagementFragment extends BaseFragment {
    
    private RecyclerView videoRv;
    private FloatingActionButton fab;
    private VideoListRecyclerAdapter videoListRecyclerAdapter;

    private FirebaseDatabaseHelper<VideoForm> videoFormFirebaseDatabase;
    private List<VideoForm> videoFormList;
    

    public VideoManagementFragment() {
        // Required empty public constructor
    }

    public static VideoManagementFragment newInstance() {
        VideoManagementFragment fragment = new VideoManagementFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initialize() {
        videoFormFirebaseDatabase = new FirebaseDatabaseHelper<>( VideoForm.class);

        videoFormFirebaseDatabase.fetchItems(StringConstants.VIDEO_LIST_DB, itemList -> {
            videoListRecyclerAdapter = new VideoListRecyclerAdapter(itemList);
            videoRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getBaseActivity().getBaseContext()));
            videoRv.setAdapter(videoListRecyclerAdapter);
            videoFormList = itemList;
        });

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseActivity().getBaseContext());
        new SwipeHelper(getBaseActivity().getBaseContext(), videoRv) {
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

        fab.setOnClickListener(v -> {
            startActivity(new Intent(getBaseActivity().getBaseContext(), VideoFormActivity.class));
            videoListRecyclerAdapter.clear();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        videoRv = view.findViewById(R.id.video_list_rv);
        fab = view.findViewById(R.id.fab);
        super.onViewCreated(view, savedInstanceState);
    }
}
