package ph.digipay.digipayelectroniclearning.ui.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseFragment;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;


public class ContentFragment extends BaseFragment {

    private MainContract mainContract;
    private LinearLayout pdfListContainer;
    private LinearLayout videoListContainer;
    private LinearLayout questionnaireContainer;

    private String moduleUid;

    public ContentFragment() {
        // Required empty public constructor
    }

    public static ContentFragment newInstance(String moduleUid) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(StringConstants.MODULE_UID, moduleUid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initialize() {
        pdfListContainer.setOnClickListener(v -> {
            mainContract.showPDFList(moduleUid);
        });

        videoListContainer.setOnClickListener(v -> {
            mainContract.showVideoList(moduleUid);
        });

        questionnaireContainer.setOnClickListener(v -> {
            mainContract.showQuestionnaire(moduleUid);
        });
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
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pdfListContainer = view.findViewById(R.id.pdf_list_container);
        videoListContainer = view.findViewById(R.id.video_list_container);
        questionnaireContainer = view.findViewById(R.id.questionnaire_list_container);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainContract = (MainContract) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
