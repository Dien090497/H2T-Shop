package com.h2tshop.h2tshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.h2tshop.h2tshop.R;

public class AboutFragment extends Fragment {

    private View viewRoot;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewRoot =inflater.inflate(R.layout.fragment_about,container,false);
        intView();
        return viewRoot;
    }

    private void intView() {

    }
}
