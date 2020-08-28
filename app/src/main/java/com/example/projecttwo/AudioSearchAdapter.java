package com.example.projecttwo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AudioSearchAdapter extends RecyclerView.Adapter<AudioSearchAdapter.AudioSearchViewHolder> {
    Context context;
    List<Result2> Data;


    public AudioSearchAdapter(Context context, List<Result2> userListResponseData) {
        this.Data = userListResponseData;
        this.context = context;
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
    }
    @Override
    public AudioSearchAdapter.AudioSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(context).inflate(R.layout.audio_list_item, null);
        AudioSearchAdapter.AudioSearchViewHolder audioSearchViewHolder = new AudioSearchAdapter.AudioSearchViewHolder(view);
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        return audioSearchViewHolder;
    }
    @Override
    public void onBindViewHolder(AudioSearchViewHolder holder,final int position){
        holder.tv1.setText(Data.get(position).getTitle());
        holder.tv2.setText(Data.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioSearch.onKhaleed(Data.get(position).getUrl(),Data.get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return Data.size(); // size of the list items
    }

    class AudioSearchViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        TextView tv2;

        public AudioSearchViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv1=(TextView) itemView.findViewById(R.id.tv1);
            tv2=(TextView) itemView.findViewById(R.id.tv2);
        }
    }
}
