package ph.digipay.digipayelectroniclearning.ui.admin.module;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseRecyclerAdapter;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.ui.common.IconTextVH;

public class ModuleRecyclerAdapter extends BaseRecyclerAdapter<Module, IconTextVH> {

    public ModuleRecyclerAdapter(List<Module> items) {
        super(items);
    }

    @NonNull
    @Override
    public IconTextVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IconTextVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_menu, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IconTextVH holder, int i) {
        Module modules = getItem(i);

        holder.icon.setVisibility(View.GONE);
        holder.title.setText(modules.getName());
        holder.description.setText(modules.getDescription());

    }
}
