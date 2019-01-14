package ph.digipay.digipayelectroniclearning.ui.admin.module;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Optional;

import java.util.List;
import java.util.Objects;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseActivity;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;
import ph.digipay.digipayelectroniclearning.common.utils.FormUtils;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.persistence.firebase_db.FirebaseDatabaseHelper;

public class ModuleActivity extends BaseActivity implements Validator.ValidationListener {

    private RecyclerView moduleRv;
    @NotEmpty
    protected TextInputEditText moduleName;
    @Optional
    protected TextInputEditText moduleDescription;

    private FirebaseDatabaseHelper<Module> modulesFirebaseDatabase;

    private Validator validator;

    private ModuleRecyclerAdapter moduleRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        moduleName = findViewById(R.id.module_name_tiet);
        moduleDescription = findViewById(R.id.module_description);
        moduleRv = findViewById(R.id.modules_rv);

        modulesFirebaseDatabase = new FirebaseDatabaseHelper<>(Module.class);
        modulesFirebaseDatabase.fetchItems(StringConstants.MODULE_DB, itemList -> {
            moduleRecyclerAdapter = new ModuleRecyclerAdapter(itemList);
            moduleRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(this));
            moduleRv.setAdapter(moduleRecyclerAdapter);
        });


        validator = new Validator(this);
        validator.setValidationListener(this);

        findViewById(R.id.add_module_btn).setOnClickListener(v -> validator.validate());
    }

    @Override
    public void onValidationSucceeded() {
        Module modules = new Module(FormUtils.getTrimmedString(moduleName), FormUtils.getTrimmedString(moduleDescription));
        modulesFirebaseDatabase.insertItems(StringConstants.MODULE_DB, modules);
        moduleRecyclerAdapter.clear();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        displayErrors(errors);
    }

    @Override
    public void setUpToolbar() {
        super.setUpToolbar();
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
