package ph.digipay.digipayelectroniclearning.ui.admin.video;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ph.digipay.digipayelectroniclearning.R;
import ph.digipay.digipayelectroniclearning.common.base.BaseRecyclerAdapter;
import ph.digipay.digipayelectroniclearning.models.VideoForm;
import ph.digipay.digipayelectroniclearning.ui.common.IconTextVH;

public class VideoListRecyclerAdapter extends BaseRecyclerAdapter<VideoForm, IconTextVH> {

    public VideoListRecyclerAdapter(List<VideoForm> items) {
        super(items);
    }

    @NonNull
    @Override
    public IconTextVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IconTextVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_menu, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IconTextVH holder, int i) {
        VideoForm videoForm = getItem(i);

        holder.icon.setVisibility(View.GONE);
        holder.title.setText(videoForm.getName());
        holder.description.setText(videoForm.getDescription());
    }
}
