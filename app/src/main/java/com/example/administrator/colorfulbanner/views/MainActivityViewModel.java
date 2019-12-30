package com.example.administrator.colorfulbanner.views;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {
    MutableLiveData<ArrayList<Integer>> colorlist=new MutableLiveData<>();

    public MutableLiveData<ArrayList<Integer>> getColorlist() {
        return colorlist;
    }

    public void setColorlist(MutableLiveData<ArrayList<Integer>> colorlist) {
        this.colorlist = colorlist;
    }
}
