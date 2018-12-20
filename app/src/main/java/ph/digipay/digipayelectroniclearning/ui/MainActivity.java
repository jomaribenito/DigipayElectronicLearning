package ph.digipay.digipayelectroniclearning.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.SharedPrefManager;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.models.IconText;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.User;
import ph.digipay.digipayelectroniclearning.ui.admin.module.ModuleActivity;
import ph.digipay.digipayelectroniclearning.ui.admin.module.ModuleRecyclerAdapter;
import ph.digipay.digipayelectroniclearning.ui.common.firebase_db.FirebaseDatabaseHelper;

public class MainActivity extends BaseActivity {

    private SharedPrefManager sharedPrefManager;
    private RecyclerView mainMenuRv;
    private MenuRecyclerAdapter menuRecyclerAdapter;
    private List<IconText> iconTextList;
    private FirebaseDatabaseHelper<Module> modulesFirebaseDatabase;
    private ModuleRecyclerAdapter moduleRecyclerAdapter;
    private List<Module> modulesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMenuRv = findViewById(R.id.main_menu_rv);

        sharedPrefManager = new SharedPrefManager(this);

        User user = sharedPrefManager.getLoginUser();

        modulesFirebaseDatabase = new FirebaseDatabaseHelper<>(Module.class);

        modulesFirebaseDatabase.fetchItems(StringConstants.MODULE_DB, itemList -> {
            moduleRecyclerAdapter = new ModuleRecyclerAdapter(modulesList);
            mainMenuRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getApplicationContext()));
            mainMenuRv.setAdapter(moduleRecyclerAdapter);
        });

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
        } else if (id == R.id.action_manage) {
            startActivity(new Intent(getApplicationContext(), ModuleActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
