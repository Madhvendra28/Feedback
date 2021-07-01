package com.adi.feedback.viewholder;


import android.view.View;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.adi.feedback.R;


/**
 * Created by Arshad on 08-03-2016.
 */
public class OptionsViewHolder extends RecyclerView.ViewHolder {


    public RadioButton cbOption;

    public OptionsViewHolder(View view) {
        super(view);
        cbOption=view.findViewById(R.id.cbOptions);

    }

}
