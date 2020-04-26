package com.io.bookstores.adapter.homeAdapter;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.io.bookstores.Config;
import com.io.bookstores.R;
import com.io.bookstores.model.sliderAdModel.AdData;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class AdSliderAdapter extends SliderViewAdapter<AdSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<AdData> adList;
    private Integer mFlagDB;

    public AdSliderAdapter(Context context, List<AdData> adList) {
        this.context = context;
        this.adList = adList;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        viewHolder.adName.setText(adList.get(position).getName());
        Glide.with(viewHolder.itemView)
                .load(Config.imageUrl + adList.get(position).getImagePath())
                .into(viewHolder.adImage);
        viewHolder.adImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogZoom(Config.imageUrl + adList.get(position).getImagePath());
            }
        });

    }

    private void dialogZoom(String s) {
        final Dialog dialog = new Dialog(context, R.style.dialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dilog_zoom);
        dialog.setTitle("");

        final PhotoView iv_item_image = (PhotoView) dialog.findViewById(R.id.photo_view);
        final ImageView Clear = (ImageView) dialog.findViewById(R.id.iv_cancel);

        Glide.with(context).load(s).into(iv_item_image);


        Clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return adList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView adImage;
        TextView adName;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            adImage = itemView.findViewById(R.id.iv_auto_image_slider);
            adName = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
