package com.example.fieldmapping;
        import android.annotation.SuppressLint;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.text.InputFilter;
        import android.text.InputType;
        import android.util.Log;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;

        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

public class mappedFieldInformation extends AppCompatActivity {
    DrawView drawView;
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;
    TextView fieldSize;
    TextView tempID;
    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this,null);
        setContentView(R.layout.activity_mapped_field_information);
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        fieldSize=findViewById(R.id.fieldSize);
        tempID=findViewById(R.id.temp_id);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        mydate  = dateFormat.format(date);
        fieldSize.setText(member.getString("fieldsize","0.00"));
        tempID.setText(member.getString("unique_id","000F"));

    }
    @Override
    public void onResume()
    {
        super.onResume();

    }




    public void finish(View v)
    {
        memEdit.putString("remap_flag","0");
        memEdit.commit();
        Intent fp=new Intent(getApplicationContext(), fielddescription.class);
        startActivity(fp);
        finish();
    }
    public void remap(View v)
    {
        memEdit.putString("remap_flag","1");
        memEdit.commit();
        Intent fp=new Intent(getApplicationContext(), remapreason.class);
        startActivity(fp);
    }
    @Override
    public void onBackPressed()
    {


    }
}