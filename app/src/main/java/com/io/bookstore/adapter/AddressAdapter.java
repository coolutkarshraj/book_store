package com.io.bookstore.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstore.R;
import com.io.bookstore.holder.AddressHolder;
import com.io.bookstore.holder.CartHolder;
import com.io.bookstore.localStorage.LocalStorage;
import com.io.bookstore.model.addAddressResponseModel.DeliveryAddress;
import com.io.bookstore.model.addAddressResponseModel.GetAddressListDataModel;
import com.io.bookstore.model.getAddressResponseModel.Datum;

import java.util.ArrayList;
import java.util.List;


public class AddressAdapter extends RecyclerView.Adapter<AddressHolder> {
    private Activity activity;
    List<DeliveryAddress> list;

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
        holder.tv_address.setText(list.get(position).getAddress() +","+ list.get(position).getCity()+","+ list.get(position).getState()+","+ list.get(position).getCountry());
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_phone.setText(list.get(position).getPhoneNum());
        if(list.get(position).getChecked()){
            holder.rv_select_Address.setChecked(true);
        }else {
            holder.rv_select_Address.setChecked(false);
        }
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
                LocalStorage localStorage = new LocalStorage(activity);
                localStorage.putString(LocalStorage.addressId,String.valueOf(list.get(position).getAddressId()));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
