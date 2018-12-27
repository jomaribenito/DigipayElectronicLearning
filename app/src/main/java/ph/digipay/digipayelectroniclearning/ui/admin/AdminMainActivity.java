package ph.digipay.digipayelectroniclearning.ui.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.models.Questionnaire;
import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.ui.PDFBrowserActivity;
import ph.digipay.digipayelectroniclearning.ui.VideoPlayerActivity;
import ph.digipay.digipayelectroniclearning.ui.admin.module.ModuleActivity;
import ph.digipay.digipayelectroniclearning.ui.admin.pdf.PDFManagementActivity;
import ph.digipay.digipayelectroniclearning.ui.admin.questionnaire.QuestionnaireManagementActivity;
import ph.digipay.digipayelectroniclearning.ui.admin.video.VideoManagementActivity;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;

public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ExpandableListView expandableListView;

    private FirebaseDatabaseHelper<Module> moduleFirebaseDatabase;
    private FirebaseDatabaseHelper<PDFForm> pdfFormFirebaseDatabase;
    private FirebaseDatabaseHelper<VideoForm> videoFormFirebaseDatabase;
    private FirebaseDatabaseHelper<Questionnaire> questionnaireFirebaseDatabase;

    private MainExpandableAdapter mainExpandableAdapter;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        expandableListView = findViewById(R.id.admin_main_elv);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        moduleFirebaseDatabase = new FirebaseDatabaseHelper<>(Module.class);
        pdfFormFirebaseDatabase = new FirebaseDatabaseHelper<>(PDFForm.class);
        videoFormFirebaseDatabase = new FirebaseDatabaseHelper<>(VideoForm.class);
        questionnaireFirebaseDatabase = new FirebaseDatabaseHelper<>(Questionnaire.class);

        mainExpandableAdapter = new MainExpandableAdapter(this);
        moduleFirebaseDatabase.fetchItems(StringConstants.MODULE_DB, newValue -> mainExpandableAdapter.setModuleList(newValue));
        pdfFormFirebaseDatabase.fetchItems(StringConstants.PDF_LIST_DB, newValue -> mainExpandableAdapter.setPdfFormList(newValue));
        videoFormFirebaseDatabase.fetchItems(StringConstants.VIDEO_LIST_DB, newValue -> mainExpandableAdapter.setVideoFormList(newValue));
        questionnaireFirebaseDatabase.fetchItems(StringConstants.QUESTIONNAIRE_DB, newValue -> mainExpandableAdapter.setQuestionnaireList(newValue));

        expandableListView.setAdapter(mainExpandableAdapter);

        mainExpandableAdapter.getPdfFormPublishSubject().subscribe(pdfForm -> startActivity(new Intent(this, PDFBrowserActivity.class).putExtra(StringConstants.PDF_URL, pdfForm.getPdfLink())));

        mainExpandableAdapter.getVideoFormPublishSubject().subscribe(videoForm -> startActivity(new Intent(this, VideoPlayerActivity.class).putExtra(StringConstants.VIDEO_URL, videoForm.getVideoLink())));

        mainExpandableAdapter.getQuestionnairePublishSubject().subscribe(questionnaire -> {
            Log.e("TAG", questionnaire.getQuestion());

        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_module_settings) {
            startActivity(new Intent(getApplicationContext(), ModuleActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_pdf:
                startActivity(new Intent(getApplicationContext(), PDFManagementActivity.class));
                break;
            case R.id.nav_video:
                startActivity(new Intent(getApplicationContext(), VideoManagementActivity.class));
                break;
            case R.id.nav_questionnaire:
                startActivity(new Intent(getApplicationContext(), QuestionnaireManagementActivity.class));
                break;
            case R.id.nav_logout:
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
