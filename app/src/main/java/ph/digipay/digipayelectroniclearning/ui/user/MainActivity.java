package ph.digipay.digipayelectroniclearning.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.SharedPrefManager;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.FragmentUtils;
import ph.digipay.digipayelectroniclearning.models.User;
import ph.digipay.digipayelectroniclearning.ui.LandingPageActivity;

public class MainActivity extends BaseActivity implements MainContract {

    private SharedPrefManager sharedPrefManager;
    private static final int ID_FRAGMENT_CONTAINER = R.id.main_activity_fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = new SharedPrefManager(this);

        User user = sharedPrefManager.getLoginUser();

        showModule();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            sharedPrefManager.setLogin(false);
            finish();
            startActivity(new Intent(getApplicationContext(), LandingPageActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showModule() {
        ModuleFragment moduleFragment = ModuleFragment.newInstance();
        FragmentUtils.addFragment(this, ID_FRAGMENT_CONTAINER, moduleFragment);
    }

    @Override
    public void showContent(String moduleUid) {
        ContentFragment contentFragment = ContentFragment.newInstance(moduleUid);
        FragmentUtils.replaceFragmentAddToBackStack(this, ID_FRAGMENT_CONTAINER, contentFragment);
    }

    @Override
    public void showPDFList(String moduleUid) {
        PDFListFragment pdfListFragment = PDFListFragment.newInstance(moduleUid);
        FragmentUtils.replaceFragmentAddToBackStack(this, ID_FRAGMENT_CONTAINER, pdfListFragment);
    }

    @Override
    public void showVideoList(String moduleUid) {
        VideoListFragment videoListFragment = VideoListFragment.newInstance(moduleUid);
        FragmentUtils.replaceFragmentAddToBackStack(this, ID_FRAGMENT_CONTAINER, videoListFragment);
    }

    @Override
    public void showQuestionnaire(String moduleUid) {
        startActivity(new Intent(this, QuestionnaireActivity.class).putExtra(StringConstants.MODULE_UID, moduleUid));
    }
}
