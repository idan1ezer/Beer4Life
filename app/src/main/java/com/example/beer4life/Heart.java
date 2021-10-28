package com.example.beer4life;

public class Heart {
    private int res = 0;
    private boolean isFull = true;

    public Heart(){ }

    public int getRes() {
        return res;
    }

    public Heart setRes(int res) {
        this.res = res;
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
