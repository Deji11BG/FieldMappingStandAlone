package com.example.fieldmapping;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class TFMHomePresenter implements TFMHomePresenterInterface {
    private TFMHomeInterface viewObject;
    private modelmemberinfo membermodel;
    public TFMHomePresenter(TFMHomeInterface viewObject){
        this.viewObject = viewObject;
        membermodel = new modelmemberinfo();

    }
    public TFMHomePresenter(){
        membermodel = new modelmemberinfo();
    }

    @Override
    public void hideFeature(View view) {
        viewObject.hideView(view);
    }

    @Override
    public void showFeature(View view) {
        viewObject.showView(view);
    }
    @Override
    public void loadPreviousActivity() {
        viewObject.loadPreviousActivity();
    }


    @Override
    public List<modelmemberinfo> getSearchParameters(CharSequence constraint, List<modelmemberinfo> total_leader_list) {
        String charString = constraint.toString().trim();

        List<modelmemberinfo> mFilteredList;

        if(charString.isEmpty()){

            mFilteredList = total_leader_list;
        }else{
            List<modelmemberinfo> filteredList = new ArrayList<>();

            for(modelmemberinfo leaders : total_leader_list){

                if(
                        leaders.getFirst_name().toLowerCase().contains(charString.toLowerCase()) ||
                        leaders.getLast_name().toLowerCase().contains(charString.toLowerCase()) ||
                        ((leaders.getFirst_name().toLowerCase())+" "+(leaders.getLast_name().toLowerCase())).contains(charString.toLowerCase())){

                    filteredList.add(leaders);
                }
            }

            mFilteredList = filteredList;
        }

        return mFilteredList;
    }
    @Override
    public void textWatcher(EditText editText, tghomerecycler adapter) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null){
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}

