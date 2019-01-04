package ph.digipay.digipayelectroniclearning.ui.user;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import io.reactivex.subjects.PublishSubject;
import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseRecyclerAdapter;
import ph.digipay.digipayelectroniclearning.models.Module;
import ph.digipay.digipayelectroniclearning.models.PDFForm;
import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.ui.common.IconTextVH;

public class VideoAdapter extends BaseRecyclerAdapter<VideoForm, IconTextVH> {

    public PublishSubject<VideoForm> onPublishSubject = PublishSubject.create();

    public PublishSubject<VideoForm> getPublishSubject() {
        return onPublishSubject;
    }

    public VideoAdapter() {
    }

    @NonNull
    @Override
    public IconTextVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IconTextVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_menu, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IconTextVH holder, int i) {
        VideoForm videoForm = getItem(i);

        holder.title.setText(videoForm.getName());
        holder.description.setText(videoForm.getDescription());
        holder.itemContainer.setOnClickListener(v -> {
            onPublishSubject.onNext(videoForm);
        });
    }
}
