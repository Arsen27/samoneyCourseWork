package com.example.samoney;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AddNewPageFragment extends Fragment {
    private int pageNumber;

    public static AddNewPageFragment newInstance(int page) {
        AddNewPageFragment fragment = new AddNewPageFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        fragment.setArguments(args);
        return fragment;
    }

    AddNewPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.add_new_fragment, container, false);
        TextView pageHeader=(TextView)result.findViewById(R.id.displayText);
        String header = "Фрагмент " + (pageNumber+1);
        pageHeader.setText(header);
        return result;
    }
}
