package com.example.fieldmapping;


//RECYCLER VIEW Adapter of active fields

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import java.util.ArrayList;
        import java.util.List;

        import androidx.recyclerview.widget.RecyclerView;

        import static android.content.Context.MODE_PRIVATE;

public class remapreasonrecycler extends RecyclerView.Adapter<remapreasonrecycler.ViewHolder>  {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mContext;
    SharedPreferences member;
    SharedPreferences prefs;
    SharedPreferences.Editor memEdit;
    remapreasonmodel model;
    private List<remapreasonmodel> member_list;
    private List<remapreasonmodel> examplelistfull;
    // itemsCopy.addAll(items);

    public remapreasonrecycler(ArrayList<remapreasonmodel> memberList, Context context) {
        this.member_list=memberList;
        examplelistfull= new ArrayList<>(memberList);
        mContext=context;
        //setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.remapreasonrecycler,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {
        model = member_list.get(position);
        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(model.getReason());
    }


    @Override
    public int getItemCount() {
        return member_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView imageName;
        RelativeLayout parentLayout;
        public ViewHolder (View itemView)
        {
            super(itemView);
            imageName=itemView.findViewById(R.id.reason);
            parentLayout=itemView.findViewById(R.id.parent_layout);
            parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    final TextView reasontext = view.findViewById(R.id.reason);
                    final String reasont = reasontext.getText().toString();
                    member = mContext.getSharedPreferences("member", MODE_PRIVATE);
                    SharedPreferences.Editor editor = member.edit();
                    editor.putString("remap_flag",reasont);
                    Log.d("meeee",reasont);
                    editor.commit();
                    Intent intent= new Intent(mContext,TGhome.class);
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }


}

