package com.example.fieldmapping;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import butterknife.BindView;

        import android.annotation.SuppressLint;
        import android.app.job.JobInfo;
        import android.app.job.JobScheduler;
        import android.content.ComponentName;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import com.google.android.material.card.MaterialCardView;
        import com.google.android.material.textview.MaterialTextView;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.concurrent.ExecutionException;

public class remapreason extends AppCompatActivity {
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;
    private String []reasonlist;
    private ArrayList<remapreasonmodel> memberList1=new ArrayList<>();
    private ArrayList<Map<String,String>> memberList3;
    private RecyclerView recyclerView;
    String first_name;
    String last_name;
    ImageView mImageView;
    Button next;
    TextView memberName,mem_id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remapreason);
        memberName=findViewById(R.id.txtName);
        member = getSharedPreferences("member", MODE_PRIVATE);
        memEdit = member.edit();
        recyclerView = findViewById(R.id.recyclerv_viewremapreason);
        mImageView = findViewById(R.id.ImageActive);
        memberName.setText(member.getString("first_name","Koye")+" "+member.getString("last_name","sodipo"));
        mem_id=findViewById(R.id.mem_id);
        mem_id.setText(member.getString("member_id","0001"));
        next=findViewById(R.id.next);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reasonlist=new String[]{"I made a mistake","The farmer changed his field","Field size is wrong",
                "Farmer changed the map field","Farmer wants to add more fields","Wrong coordinates",
                "Log growing activities"};
        loadRecyclerView();

    }
    private void recyclerController( ArrayList<Map<String,String>> wordList){
//        memberList1.clear();
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject = null;
        for(int i = 0; i<jsonArray.length();i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                memberList1.add(new remapreasonmodel(
                        jsonObject.getString("reason")
                ));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        remapreasonrecycler tfmAdapter = new remapreasonrecycler(memberList1,this);
        tfmAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tfmAdapter);
        tfmAdapter.notifyDataSetChanged();
    }
    public void loadRecyclerView(){
        Map<String,String > map = null;
        memberList3 = new ArrayList<>();
        for (String reasonind: reasonlist){
            map = new HashMap<>();
            map.put("reason",reasonind);
            memberList3.add(map);
        }
        Log.d("--HELLO--1",memberList3+"");
        recyclerController(memberList3);


    }

    public void nextReason(View v) {
        finish();
    }
    @Override
    public void onBackPressed()
    {


    }
}
