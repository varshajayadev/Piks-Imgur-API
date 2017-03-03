package com.varshajayadev.piks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by varshajayadev on 2/25/17.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {
    public TextView description;
    public ImageView image;
    public TextView thumbsupText;
    public TextView thumbsdownText;

    public ImageViewHolder(View itemView) {
        super(itemView);

        description = (TextView) itemView.findViewById(R.id.description);
        image = (ImageView)itemView.findViewById(R.id.image);
        thumbsupText = (TextView) itemView.findViewById(R.id.thumbsupText);
        thumbsdownText = (TextView) itemView.findViewById(R.id.thumbsdownText);
    }
}
