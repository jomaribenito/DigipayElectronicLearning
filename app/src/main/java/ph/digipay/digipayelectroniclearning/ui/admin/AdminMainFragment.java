package ph.digipay.digipayelectroniclearning.ui.admin;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseFragment;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.ui.common.PDFBrowserActivity;
import ph.digipay.digipayelectroniclearning.ui.common.VideoPlayerActivity;

public class AdminMainFragment extends BaseFragment {

    private MainExpandableAdapter mainExpandableAdapter;
    private ExpandableListView expandableListView;


    public AdminMainFragment() {
        // Required empty public constructor
    }

    public static AdminMainFragment newInstance() {
        AdminMainFragment fragment = new AdminMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        expandableListView = view.findViewById(R.id.admin_main_elv);
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("CheckResult")
    @Override
    public void initialize() {

        mainExpandableAdapter = new MainExpandableAdapter(getBaseActivity().getBaseContext());
        getBaseActivity().getDigipayELearningApplication().getAppComponent().getModuleFbDatabase()
                .fetchItems(StringConstants.MODULE_DB, newValue -> mainExpandableAdapter.setModuleList(newValue));
        getBaseActivity().getDigipayELearningApplication().getAppComponent().getPdfFormFbDatabase()
                .fetchItems(StringConstants.PDF_LIST_DB, newValue -> mainExpandableAdapter.setPdfFormList(newValue));
        getBaseActivity().getDigipayELearningApplication().getAppComponent().getVideoFormFbDatabase()
                .fetchItems(StringConstants.VIDEO_LIST_DB, newValue -> mainExpandableAdapter.setVideoFormList(newValue));
        getBaseActivity().getDigipayELearningApplication().getAppComponent().getQuestionnaireFbDatabase()
                .fetchItems(StringConstants.QUESTIONNAIRE_DB, newValue -> mainExpandableAdapter.setQuestionnaireList(newValue));

        expandableListView.setAdapter(mainExpandableAdapter);

        mainExpandableAdapter.getPdfFormPublishSubject().subscribe(pdfForm -> startActivity(new Intent(getBaseActivity().getBaseContext(), PDFBrowserActivity.class).putExtra(StringConstants.PDF_URL, pdfForm.getPdfLink())));

        mainExpandableAdapter.getVideoFormPublishSubject().subscribe(videoForm -> startActivity(new Intent(getBaseActivity().getBaseContext(), VideoPlayerActivity.class).putExtra(StringConstants.VIDEO_URL, videoForm.getVideoLink())));

        mainExpandableAdapter.getQuestionnairePublishSubject().subscribe(questionnaire -> {
            Log.e("TAG", questionnaire.getQuestion());
        });
    }
}
