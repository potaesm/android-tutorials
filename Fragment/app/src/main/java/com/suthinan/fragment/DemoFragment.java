package com.suthinan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DemoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ListView listView = view.findViewById(R.id.list_view);
//        String[] strings = {"Cupcake", "Donuts", "Eclairs", "Froyo", "GingerBread", "HoneyComb", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Nougat", "Oreo"};
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strings);
//         listView.setAdapter(arrayAdapter);

        CustomAdapter adapter = new CustomAdapter(getContext(), getAndroidVersions());
        listView.setAdapter(adapter);
        return view;
    }
    private ArrayList<AndroidVersion> getAndroidVersions() {
        AndroidVersion version = new AndroidVersion();
        version.setVersionNum(4.1);
        version.setVersionName("Ice Cream Sandwich");

        AndroidVersion version1 = new AndroidVersion();
        version1.setVersionNum(4.2);
        version1.setVersionName("Jelly Bean");

        AndroidVersion version2 = new AndroidVersion();
        version2.setVersionNum(4.4);
        version2.setVersionName("Kit Kat");

        AndroidVersion version3 = new AndroidVersion();
        version3.setVersionNum(5.0);
        version3.setVersionName("Lollipop");

        AndroidVersion version4 = new AndroidVersion();
        version4.setVersionNum(6.0);
        version4.setVersionName("Marshmallow");

        AndroidVersion version5 = new AndroidVersion();
        version5.setVersionNum(7.0);
        version5.setVersionName("Nougat");

        ArrayList<AndroidVersion> versionArrayList = new ArrayList<>();
        versionArrayList.add(version);
        versionArrayList.add(version1);
        versionArrayList.add(version2);
        versionArrayList.add(version3);
        versionArrayList.add(version4);
        versionArrayList.add(version4);

        return versionArrayList;
    }
}
