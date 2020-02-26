package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.holder.AddressHolder;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.addAddressResponseModel.DeliveryAddress;

import java.util.List;


public class AddressAdapter extends RecyclerView.Adapter<AddressHolder> {
    private Activity activity;
    List<DeliveryAddress> list;
    int postionn = 0;
    public AddressAdapter(Activity activity, List<DeliveryAddress> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.address_adapter, parent, false);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, final int position) {
        holder.tv_address.setText("Landmark : " + list.get(position).getState());
        holder.tv_name.setText("Address : " + list.get(position).getAddress());
        holder.tv_phone.setText("City : " + list.get(position).getCity() + ", " + list.get(position).getZipcode());

        holder.rv_select_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0;i <list.size();i++){
                    if(i == position){
                        list.get(position).setChecked(true);
                    }else {
                        list.get(i).setChecked(false);
                    }
                }
                postionn = position;
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.addressId,String.valueOf(list.get(position).getAddressId()));
                notifyDataSetChanged();
            }
        });

        if (postionn == position) {
            LocalStorage localStorage = new LocalStorage(activity);
            localStorage.putString(LocalStorage.addressId,String.valueOf(list.get(postionn).getAddressId()));
            holder.rv_select_Address.setChecked(true);
         /*   if (list.get(position).getChecked()) {
                holder.rv_select_Address.setChecked(true);
            } else {
                holder.rv_select_Address.setChecked(false);
            }*/
        } else {
            holder.rv_select_Address.setChecked(false);

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
