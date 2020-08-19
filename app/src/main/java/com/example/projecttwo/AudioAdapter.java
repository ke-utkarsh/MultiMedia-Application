package com.example.projecttwo;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {
    Context context;
    List<Result> Data;


    public AudioAdapter(Context context, List<Result> userListResponseData) {
        this.Data = userListResponseData;
        this.context = context;
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
    }
    @Override
    public AudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(context).inflate(R.layout.audio_list_item, null);
        AudioViewHolder audioViewHolder = new AudioViewHolder(view);
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        return audioViewHolder;
    }
    @Override
    public void onBindViewHolder(final AudioViewHolder holder, final int position) {
        holder.tv1.setText(Data.get(position).getTitle());
        holder.tv2.setText(Data.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Audios.onClickCalled(Data.get(position).getUrl(),Data.get(position).getTitle());
            }
        });
    }
    @Override
    public int getItemCount() {
        return Data.size(); // size of the list items
    }

    class AudioViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        TextView tv2;

        public AudioViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv1=(TextView) itemView.findViewById(R.id.tv1);
            tv2=(TextView) itemView.findViewById(R.id.tv2);
        }
    }
}

