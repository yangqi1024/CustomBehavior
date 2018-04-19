package com.yq.custombehavior;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Description ...
 *
 * @author gsz
 * @since V1.0.1
 */
public class HomeRecyHolder extends RecyclerView.ViewHolder{

    public  TextView mTv;

    public HomeRecyHolder(View itemView) {
        super(itemView);
        mTv = itemView.findViewById(R.id.tv);
    }
}
