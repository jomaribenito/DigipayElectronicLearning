package ph.digipay.digipayelectroniclearning.ui.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ph.digipay.digipayelectroniclearning.R;

public class IconTextVH extends RecyclerView.ViewHolder {

    public ImageView icon;
    public TextView title;
    public TextView description;

    public IconTextVH(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.icon_iv);
        title = itemView.findViewById(R.id.title_tv);
        description = itemView.findViewById(R.id.description_tv);
    }
}
