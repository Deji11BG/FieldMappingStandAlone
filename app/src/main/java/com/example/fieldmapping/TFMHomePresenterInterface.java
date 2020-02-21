package com.example.fieldmapping;

import android.view.View;
import android.widget.EditText;

import java.util.List;

public interface TFMHomePresenterInterface {

    void hideFeature(View view);
    void showFeature(View view);
    void loadPreviousActivity();
    List<modelmemberinfo> getSearchParameters(CharSequence constraint, List<modelmemberinfo> total_leader_list);
    void textWatcher(EditText editText, tghomerecycler adapter);

}
