package com.io.bookstore.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.io.bookstore.Config;
import com.io.bookstore.R;
import com.io.bookstore.model.sliderAdModel.AdData;
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
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        viewHolder.adName.setText(adList.get(position).getName());
        Glide.with(viewHolder.itemView)
                .load(Config.imageUrl + adList.get(position).getImagePath())
                .into(viewHolder.adImage);

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
