package com.example.projecttwo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    Context context;
    List<Result> videoListResponseData;
    int pageNumber;
    int pos=-1;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public UsersAdapter(Context context, List<Result> userListResponseData) {
        this.videoListResponseData = userListResponseData;
        this.context = context;
        //this.pageNumber=pageNumber;
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
    }
    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_items, null);
        UsersViewHolder usersViewHolder = new UsersViewHolder(view);
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        return usersViewHolder;
    }
    @Override
    public void onBindViewHolder(UsersViewHolder holder, final int position) {
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        String web=videoListResponseData.get(position).getUrl();
        int l=web.length();
        if(l>12){
            try{
                String url="https://img.youtube.com/vi/"+web.substring(l-11,l)+"/mqdefault.jpg";
                Picasso.with(context).load(url).into(holder.img);
            }catch(Exception e){
                Toast.makeText(context, "Can't Load", Toast.LENGTH_SHORT).show();
            }
            holder.tv1.setText(videoListResponseData.get(position).getTitle());
        }

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,vdo.class);
                intent.putExtra("url",videoListResponseData.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,vdo.class);
                intent.putExtra("url",videoListResponseData.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return videoListResponseData.size(); // size of the list items
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv1;
        public UsersViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv1=(TextView) itemView.findViewById(R.id.tv1);
            img=(ImageView) itemView.findViewById(R.id.imgView);
        }
    }
}

