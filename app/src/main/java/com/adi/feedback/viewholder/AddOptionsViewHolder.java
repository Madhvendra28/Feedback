package com.adi.feedback.viewholder;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.adi.feedback.R;



/**
 * Created by Arshad on 08-03-2016.
 */
public class AddOptionsViewHolder extends RecyclerView.ViewHolder {


    public EditText etOption;


    public ImageView ivAdd;

    public AddOptionsViewHolder(View view) {
        super(view);


        etOption=view.findViewById(R.id.etOptions);
        ivAdd=view.findViewById(R.id.ivAdd);
    }

}
