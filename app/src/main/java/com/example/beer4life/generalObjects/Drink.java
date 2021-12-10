package com.example.beer4life.generalObjects;

import android.widget.ImageView;

public class Drink {
    private ImageView img;
    private boolean isBeer = true;

    public Drink() { }

    public ImageView getImg() {
        return img;
    }

    public Drink setImg(ImageView img) {
        this.img = img;
        return this;
    }

    public boolean isBeer() {
        return isBeer;
    }

    public Drink setBeer(boolean beer) {
        isBeer = beer;
        return this;
    }
}
