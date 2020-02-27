package com.example.fieldmapping;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.util.AttributeSet;
        import android.util.Log;
        import android.view.View;

        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.List;

        import static android.content.Context.MODE_PRIVATE;


public class DrawView extends View {
    Paint paint = new Paint();
    List<Double> ans = getArr();

    public DrawView(Context context, AttributeSet attrs) {
        super(context,attrs);
        paint.setColor(Color.BLUE);

        paint.setStrokeWidth(10.0F);
    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        SharedPreferences member;
        SharedPreferences prefs2;
        SharedPreferences.Editor memEdit;
        member = getContext().getSharedPreferences("member", MODE_PRIVATE);
        String field_size=member.getString("fieldsize","0.00");
        double fieldsize=Double.valueOf(field_size);
        //double fieldsize=0.07;
        Log.d("fieldsize",String.valueOf(fieldsize));
        if (fieldsize>=2.0) {

            for (int i = 0; i + 2 < ans.size(); i += 2) {
                Log.i("BG_DEBUG", "Arr: " + new Float(ans.get(i) * 320000) + " " +
                        new Float(ans.get(i + 1) * 320000) + " " +
                        new Float(ans.get(i + 2) * 320000) + " " +
                        new Float(ans.get(i + 3) * 320000) + " ");

                canvas.drawLine(15 + new Float(ans.get(i + 0) * 130000),
                        15 + new Float(ans.get(i + 1) * 130000),
                        15 + new Float(ans.get(i + 2) * 130000),
                        15 + new Float(ans.get(i + 3) * 130000), paint);
            }
        }
        else if (fieldsize<=2.0 && fieldsize>1.5) {

            for (int i = 0; i + 2 < ans.size(); i += 2) {
                Log.i("BG_DEBUG", "Arr: " + new Float(ans.get(i) * 320000) + " " +
                        new Float(ans.get(i + 1) * 320000) + " " +
                        new Float(ans.get(i + 2) * 320000) + " " +
                        new Float(ans.get(i + 3) * 320000) + " ");

                canvas.drawLine(15 + new Float(ans.get(i + 0) * 200000),
                        15 + new Float(ans.get(i + 1) * 200000),
                        15 + new Float(ans.get(i + 2) * 200000),
                        15 + new Float(ans.get(i + 3) * 200000), paint);
            }
        }
        else if (fieldsize<=1.5 && fieldsize>1.0) {



            for (int i = 0; i + 2 < ans.size(); i += 2) {
                Log.i("BG_DEBUG", "Arr: " + new Float(ans.get(i) * 320000) + " " +
                        new Float(ans.get(i + 1) * 320000) + " " +
                        new Float(ans.get(i + 2) * 320000) + " " +
                        new Float(ans.get(i + 3) * 320000) + " ");

                canvas.drawLine(15 + new Float(ans.get(i + 0) * 320000),
                        15 + new Float(ans.get(i + 1) * 320000),
                        15 + new Float(ans.get(i + 2) * 320000),
                        15 + new Float(ans.get(i + 3) * 320000), paint);
            }
        }
        else if (fieldsize<=1.0 && fieldsize>0.5) {

            for (int i = 0; i + 2 < ans.size(); i += 2) {
                Log.i("BG_DEBUG", "Arr: " + new Float(ans.get(i) * 320000) + " " +
                        new Float(ans.get(i + 1) * 320000) + " " +
                        new Float(ans.get(i + 2) * 320000) + " " +
                        new Float(ans.get(i + 3) * 320000) + " ");

                canvas.drawLine(15 + new Float(ans.get(i + 0) * 450000),
                        15 + new Float(ans.get(i + 1) * 450000),
                        15 + new Float(ans.get(i + 2) * 450000),
                        15 + new Float(ans.get(i + 3) * 450000), paint);
            }
        }
        else if (fieldsize<=0.5 && fieldsize>=0.1) {

            for (int i = 0; i + 2 < ans.size(); i += 2) {
                Log.i("BG_DEBUG", "Arr: " + new Float(ans.get(i) * 320000) + " " +
                        new Float(ans.get(i + 1) * 320000) + " " +
                        new Float(ans.get(i + 2) * 320000) + " " +
                        new Float(ans.get(i + 3) * 320000) + " ");

                canvas.drawLine(15 + new Float(ans.get(i + 0) * 800000),
                        15 + new Float(ans.get(i + 1) * 800000),
                        15 + new Float(ans.get(i + 2) * 800000),
                        15 + new Float(ans.get(i + 3) * 800000), paint);
            }
        }
        else if (fieldsize<0.1) {

            for (int i = 0; i + 2 < ans.size(); i += 2) {
                Log.i("BG_DEBUG", "Arr: " + new Float(ans.get(i) * 320000) + " " +
                        new Float(ans.get(i + 1) * 320000) + " " +
                        new Float(ans.get(i + 2) * 320000) + " " +
                        new Float(ans.get(i + 3) * 320000) + " ");

                canvas.drawLine(15 + new Float(ans.get(i + 0) * 2560000),
                        15 + new Float(ans.get(i + 1) * 2560000),
                        15 + new Float(ans.get(i + 2) * 2560000),
                        15 + new Float(ans.get(i + 3) * 2560000), paint);
            }
        }


    }
    public   List<Double> getArr(){
        Log.d("preffff","gotten");
        final List <Double>myArr;
        SharedPreferences member;
        member = getContext().getSharedPreferences("member", MODE_PRIVATE);
        String z = member.getString("latlongs", "NULL,NULL");
        //String z="6.426596666666668,3.472178333333333,6.426625,3.472173333333333,6.426605,3.472131666666667,6.426633333333332,3.4721233333333337,6.426665000000001,3.4721066666666665,6.42669,3.4720933333333335,6.426706666666666,3.4721233333333337,6.426728333333333,3.4721466666666667,6.426761666666666,3.472156666666667,6.426778333333334,3.4721833333333336,6.426801666666667,3.4722066666666667,6.426810000000001,3.472236666666667,6.426805,3.472268333333333,6.426793333333334,3.472296666666667,6.426778333333334,3.4723283333333335,6.426758333333334,3.47235,6.426731666666666,3.4723749999999995,6.426715,3.4724049999999997,6.426681666666667,3.4724016666666673,6.426663333333333,3.472378333333333,6.426631666666666,3.4723699999999997,6.426620000000001,3.4723433333333333,6.4266016666666665,3.4723216666666663,6.426591666666667,3.4722866666666667,6.4265799999999995,3.47226,6.426548333333333,3.4722266666666664,6.426543333333332,3.4721983333333335,6.426566666666668,3.4721833333333336";
        myArr=new ArrayList<>();
        String []latlongs2=z.split(",");
        Integer w=0;
        Log.d("latlongsss",z.toString());
        if (latlongs2.length>5) {
            for (w = 0; w < latlongs2.length; w++) {

                myArr.add(Double.valueOf(latlongs2[w]));

            }
        }
        else {
            //Toast.makeText(,"field too small",Toast.LENGTH_LONG).show();
            //Intent intent=new Intent(getApplicationContext(), locationSyncController.class);
            //startActivity(intent);
        }

        Log.d("arrrry",myArr.toString());
        double max = Collections.max(myArr);
        Log.i("BG_DEBUG", "getArr: " + max);
        ArrayList<Double> ans2 =  new ArrayList<>(0);
        if (myArr.size() > 5) {
            for (w = 0; w < myArr.size(); w++) {
                if (w == 1 || w % 2 != 0) {
                    ans2.add(Double.valueOf(myArr.get(w)));

                }
            }
        }

        double max2 = Collections.max(ans2);
        Log.i("BG_DEBUG", "getArr: " + max2);
        ArrayList<Double> ans = new ArrayList<>(0);
        for (w = 0; w < myArr.size(); w++) {
            if (w == 0 || w % 2 == 0) {
                ans.add(max - Double.valueOf(myArr.get(w)));
            } else {
                ans.add(max2 - Double.valueOf(myArr.get(w)));
            }
        }
        return ans;

    }
}
