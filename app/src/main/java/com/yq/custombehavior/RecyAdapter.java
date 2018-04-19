package com.yq.custombehavior;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Description ...
 *
 * @author gsz
 * @since V1.0.1
 */
public class RecyAdapter extends RecyclerView.Adapter<HomeRecyHolder>{
    private ArrayList<String> mData;
    public RecyAdapter(ArrayList<String> data) {
        this.mData = data;
    }
    @NonNull
    @Override
    public HomeRecyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new HomeRecyHolder(inflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyHolder holder, int position) {
        holder.mTv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
