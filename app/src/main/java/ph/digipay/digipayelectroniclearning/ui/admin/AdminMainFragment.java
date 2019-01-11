package ph.digipay.digipayelectroniclearning.ui.admin;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseFragment;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;
import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.ui.common.PDFBrowserActivity;
import ph.digipay.digipayelectroniclearning.ui.common.VideoPlayerActivity;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;
import ph.digipay.digipayelectroniclearning.ui.user.ModuleFragment;

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

        FirebaseDatabaseHelper<Module> moduleFirebaseDatabase = new FirebaseDatabaseHelper<>(Module.class);
        FirebaseDatabaseHelper<PDFForm> pdfFormFirebaseDatabase = new FirebaseDatabaseHelper<>(PDFForm.class);
        FirebaseDatabaseHelper<VideoForm> videoFormFirebaseDatabase = new FirebaseDatabaseHelper<>(VideoForm.class);
        FirebaseDatabaseHelper<Questionnaire> questionnaireFirebaseDatabase = new FirebaseDatabaseHelper<>(Questionnaire.class);

        mainExpandableAdapter = new MainExpandableAdapter(getBaseActivity().getBaseContext());
        moduleFirebaseDatabase.fetchItems(StringConstants.MODULE_DB, newValue -> mainExpandableAdapter.setModuleList(newValue));
        pdfFormFirebaseDatabase.fetchItems(StringConstants.PDF_LIST_DB, newValue -> mainExpandableAdapter.setPdfFormList(newValue));
        videoFormFirebaseDatabase.fetchItems(StringConstants.VIDEO_LIST_DB, newValue -> mainExpandableAdapter.setVideoFormList(newValue));
        questionnaireFirebaseDatabase.fetchItems(StringConstants.QUESTIONNAIRE_DB, newValue -> mainExpandableAdapter.setQuestionnaireList(newValue));

        expandableListView.setAdapter(mainExpandableAdapter);

        mainExpandableAdapter.getPdfFormPublishSubject().subscribe(pdfForm -> startActivity(new Intent(getBaseActivity().getBaseContext(), PDFBrowserActivity.class).putExtra(StringConstants.PDF_URL, pdfForm.getPdfLink())));

        mainExpandableAdapter.getVideoFormPublishSubject().subscribe(videoForm -> startActivity(new Intent(getBaseActivity().getBaseContext(), VideoPlayerActivity.class).putExtra(StringConstants.VIDEO_URL, videoForm.getVideoLink())));

        mainExpandableAdapter.getQuestionnairePublishSubject().subscribe(questionnaire -> {
            Log.e("TAG", questionnaire.getQuestion());
        });
    }
}