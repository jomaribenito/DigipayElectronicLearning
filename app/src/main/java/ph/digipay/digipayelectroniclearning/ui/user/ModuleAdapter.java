package ph.digipay.digipayelectroniclearning.ui.user;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseRecyclerAdapter;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.ui.common.IconTextVH;

public class ModuleAdapter extends BaseRecyclerAdapter<Module, IconTextVH> {

    public PublishSubject<Module> onPublishSubject = PublishSubject.create();

    public PublishSubject<Module> getPublishSubject() {
        return onPublishSubject;
    }

    public ModuleAdapter() {
    }

    @NonNull
    @Override
    public IconTextVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IconTextVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_menu, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IconTextVH holder, int i) {
        Module module = getItem(i);

        holder.icon.setImageResource(R.drawable.ic_folder_open_24dp);
        holder.title.setText(module.getName());
        holder.description.setText(module.getDescription());
        holder.itemContainer.setOnClickListener(v -> {
            onPublishSubject.onNext(module);
        });
    }

}
