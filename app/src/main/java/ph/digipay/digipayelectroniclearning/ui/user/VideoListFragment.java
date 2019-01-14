package ph.digipay.digipayelectroniclearning.ui.user;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseFragment;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.ui.common.VideoPlayerActivity;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.FirebaseDatabaseHelper;


public class VideoListFragment extends BaseFragment {

    private RecyclerView videoListRv;

    private String moduleUid;

    public VideoListFragment() {
        // Required empty public constructor
    }

    public static VideoListFragment newInstance(String moduleUid) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putString(StringConstants.MODULE_UID, moduleUid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            moduleUid = getArguments().getString(StringConstants.MODULE_UID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        videoListRv = view.findViewById(R.id.video_list_rv);
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("CheckResult")
    @Override
    public void initialize() {
        VideoAdapter videoAdapter = new VideoAdapter();

        FirebaseDatabaseHelper<VideoForm> videoFormFirebaseDatabase = new FirebaseDatabaseHelper<>(VideoForm.class);
        videoFormFirebaseDatabase.fetchItems(StringConstants.VIDEO_LIST_DB, itemList -> {
            videoAdapter.clear();
            List<VideoForm> videoFormList = new ArrayList<>();
            for (VideoForm videoForm : itemList){
                if (videoForm.getModuleUid().equals(moduleUid)){
                    videoFormList.add(videoForm);
                }
            }
            videoAdapter.setItems(videoFormList);
        });

        videoAdapter.getPublishSubject().subscribe(videoForm -> {
            startActivity(new Intent(getBaseActivity().getBaseContext(), VideoPlayerActivity.class).putExtra(StringConstants.VIDEO_URL, videoForm.getVideoLink()));
        });

        videoListRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getBaseActivity().getBaseContext()));
        videoListRv.setAdapter(videoAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
