package ph.digipay.digipayelectroniclearning.ui.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.SharedPrefManager;
import ph.digipay.digipayelectroniclearning.common.utils.FragmentUtils;
import ph.digipay.digipayelectroniclearning.ui.LandingPageActivity;
import ph.digipay.digipayelectroniclearning.ui.admin.module.ModuleActivity;
import ph.digipay.digipayelectroniclearning.ui.admin.pdf.PDFManagementFragment;
import ph.digipay.digipayelectroniclearning.ui.admin.questionnaire.QuestionnaireManagementFragment;
import ph.digipay.digipayelectroniclearning.ui.admin.video.VideoManagementFragment;

public class AdminMainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdminMainContract {

    private SharedPrefManager sharedPrefManager;
    private final int ID_FRAGMENT_CONTAINER = R.id.content_main_fragment_container;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPrefManager = new SharedPrefManager(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showMain();
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
            showModuleManagement();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment f = getSupportFragmentManager().findFragmentById(ID_FRAGMENT_CONTAINER);

        switch (id) {
            case R.id.nav_pdf:
                if(!(f instanceof PDFManagementFragment))
                    showPdfManagement();
                break;
            case R.id.nav_video:
                if(!(f instanceof VideoManagementFragment))
                    showVideoManagement();
                break;
            case R.id.nav_questionnaire:
                if(!(f instanceof QuestionnaireManagementFragment))
                    showQuestionnaireManagement();
                break;
            case R.id.nav_logout:
                sharedPrefManager.setLogin(false);
                finish();
                startActivity(new Intent(getApplicationContext(), LandingPageActivity.class));
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void showMain() {
        AdminMainFragment adminMainFragment = AdminMainFragment.newInstance();
        FragmentUtils.addFragment(this, ID_FRAGMENT_CONTAINER, adminMainFragment);
    }

    @Override
    public void showModuleManagement() {
        startActivity(new Intent(getApplicationContext(), ModuleActivity.class));
    }

    @Override
    public void showPdfManagement() {
        PDFManagementFragment pdfManagementFragment = PDFManagementFragment.newInstance();
        FragmentUtils.replaceFragmentAddToBackStack(this, ID_FRAGMENT_CONTAINER, pdfManagementFragment);
    }

    @Override
    public void showVideoManagement() {
        VideoManagementFragment videoManagementFragment = VideoManagementFragment.newInstance();
        FragmentUtils.replaceFragmentAddToBackStack(this, ID_FRAGMENT_CONTAINER, videoManagementFragment);
    }

    @Override
    public void showQuestionnaireManagement() {
        QuestionnaireManagementFragment questionnaireManagementFragment = QuestionnaireManagementFragment.newInstance();
        FragmentUtils.replaceFragmentAddToBackStack(this, ID_FRAGMENT_CONTAINER, questionnaireManagementFragment);
    }

    @Override
    public void setUpToolbar() {
        super.setUpToolbar();
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);
    }
}
