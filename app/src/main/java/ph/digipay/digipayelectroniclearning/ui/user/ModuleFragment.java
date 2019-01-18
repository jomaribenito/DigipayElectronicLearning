package ph.digipay.digipayelectroniclearning.ui.user;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseFragment;
import ph.digipay.digipayelectroniclearning.common.constants.StringConstants;
import ph.digipay.digipayelectroniclearning.common.utils.EndlessRecyclerLinearLayoutManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModuleFragment extends BaseFragment {

    private MainContract mainContract;
    private RecyclerView moduleRv;


    public ModuleFragment() {
        // Required empty public constructor
    }

    public static ModuleFragment newInstance() {
        ModuleFragment fragment = new ModuleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_module, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        moduleRv = view.findViewById(R.id.module_rv);
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("CheckResult")
    @Override
    public void initialize() {
        ModuleAdapter moduleAdapter = new ModuleAdapter();

        getBaseActivity().getDigipayELearningApplication().getAppComponent().getModuleFbDatabase()
                .fetchItems(StringConstants.MODULE_DB, newItems -> {
                    moduleAdapter.clear();
                    moduleAdapter.setItems(newItems);
                });

        moduleAdapter.getPublishSubject().subscribe(module -> {
            mainContract.showContent(module.getUid());
        });

        moduleRv.setLayoutManager(new EndlessRecyclerLinearLayoutManager(getContext()));
        moduleRv.setAdapter(moduleAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainContract = (MainContract) context;
    }
}
