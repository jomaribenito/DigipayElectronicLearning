package ph.digipay.digipayelectroniclearning.ui.admin.pdf;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseRecyclerAdapter;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.ui.common.IconTextVH;

public class PDFListRecyclerAdapter extends BaseRecyclerAdapter<PDFForm, IconTextVH> {

    public PDFListRecyclerAdapter(List<PDFForm> items) {
        super(items);
    }

    @NonNull
    @Override
    public IconTextVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IconTextVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_menu, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IconTextVH holder, int i) {
        PDFForm pdfForm = getItem(i);

        holder.icon.setVisibility(View.GONE);
        holder.title.setText(pdfForm.getName());
        holder.description.setText(pdfForm.getDescription());


    }
}
