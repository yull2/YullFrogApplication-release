package com.frogoutofwell.yullfrogapplication.home;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.frogoutofwell.yullfrogapplication.TypefaceManager;

import org.w3c.dom.Text;

/**
 * Created by Tacademy on 2016-05-13.
 */
public class TitleViewHolder extends RecyclerView.ViewHolder {

    TextView titleView;

    public TitleViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView)itemView;
    }

    public void setTitle(String title){
        titleView.setText(title);
    }
}
