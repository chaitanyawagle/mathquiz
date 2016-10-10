package com.chaitanya.mathquiz;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class RetainFragment extends Fragment{

    Quiz data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Quiz getData() {
        return data;
    }

    public void setData(Quiz data) {
        this.data = data;
    }
}
