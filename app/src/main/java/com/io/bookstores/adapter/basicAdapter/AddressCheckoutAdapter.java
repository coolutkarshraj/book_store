package com.io.bookstores.adapter.basicAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.io.bookstores.R;
import com.io.bookstores.holder.AddressHolder;
import com.io.bookstores.model.addAddressResponseModel.DeliveryAddress;

import java.util.List;


public class AddressCheckoutAdapter extends RecyclerView.Adapter<AddressHolder> {
    private Activity activity;
    List<DeliveryAddress> list;

    public AddressCheckoutAdapter(Activity activity, List<DeliveryAddress> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_dilvery, parent, false);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressHolder holder, final int position) {
        holder.tv_address.setText("Landmark : "+list.get(position).getState());
        holder.tv_name.setText("Address : "+list.get(position).getAddress());
        holder.tv_phone.setText("City : "+list.get(position).getCity() +", "+list.get(position).getZipcode());
     /*   if(list.get(position).getChecked()){
            holder.rv_select_Address.setChecked(true);
        }else {
            holder.rv_select_Address.setChecked(false);
        }*/
    /*    holder.rv_select_Address.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
