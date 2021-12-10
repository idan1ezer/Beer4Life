package com.example.beer4life.generalObjects;

import android.widget.ImageView;

public class Heart {
    private ImageView img;
    private boolean isFull = true;

    public Heart(){ }

    public ImageView getImg() {
        return img;
    }

    public Heart setImg(ImageView img) {
        this.img = img;
        return this;
    }

    public boolean isFull() {
        return isFull;
    }

    public Heart setFull(boolean full) {
        isFull = full;
        return this;
    }
}
