package com.example.administrator.colorfulbanner.views;

public class UpdateSAEvent {
    private boolean scroll=false;
    public UpdateSAEvent(boolean scroll){
        this.scroll=scroll;
    }

    public boolean isScroll() {
        return scroll;
    }

    public void setScroll(boolean scroll) {
        this.scroll = scroll;
    }
}
