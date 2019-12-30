package com.example.administrator.colorfulbanner.views;

public class MomentsPageScrollEvent {
    float positionOffset;
    boolean left;
    int position;
    public MomentsPageScrollEvent(float positionOffset,int position){
        this.positionOffset=positionOffset;
//        this.left=left;
        this.position=position;
    }

    public float getPositionOffset() {
        return positionOffset;
    }

    public boolean isLeft() {
        return left;
    }

    public int getPosition() {
        return position;
    }
}
