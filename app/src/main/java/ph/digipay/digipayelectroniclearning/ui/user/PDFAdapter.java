package ph.digipay.digipayelectroniclearning.ui.user;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.reactivex.subjects.PublishSubject;
import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseRecyclerAdapter;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.ui.common.IconTextVH;

public class PDFAdapter extends BaseRecyclerAdapter<PDFForm, IconTextVH> {

    public PublishSubject<PDFForm> onPublishSubject = PublishSubject.create();

    public PublishSubject<PDFForm> getPublishSubject() {
        return onPublishSubject;
    }

    public PDFAdapter() {
    }

    @NonNull
    @Override
    public IconTextVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IconTextVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_menu, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IconTextVH holder, int i) {
        PDFForm pdfForm = getItem(i);

        holder.icon.setImageResource(R.drawable.ic_paper_24dp);
        holder.title.setText(pdfForm.getName());
        holder.description.setText(pdfForm.getDescription());
        holder.itemContainer.setOnClickListener(v -> {
            onPublishSubject.onNext(pdfForm);
        });
    }
}
