package com.example.projecttwo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    Context context;
    List<Result> videoListResponseData;
    public UsersAdapter(Context context, List<Result> userListResponseData) {
        this.videoListResponseData = userListResponseData;
        this.context = context;
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
    }
    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_items, null);
        UsersViewHolder usersViewHolder = new UsersViewHolder(view);
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        return usersViewHolder;
    }
    @Override
    public void onBindViewHolder(UsersViewHolder holder, final int position) {
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        String web=videoListResponseData.get(position).getUrl();
        holder.webview.setWebViewClient(new WebViewClient());
        holder.webview.getSettings().setJavaScriptEnabled(true);
        holder.webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        holder.webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        holder.webview.getSettings().setMediaPlaybackRequiresUserGesture(true);
        holder.webview.setWebChromeClient(new WebChromeClient());
        holder.webview.loadUrl(web);

    }
    @Override
    public int getItemCount() {
        return videoListResponseData.size(); // size of the list items
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {
        WebView webview;
        public UsersViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            webview=(WebView) itemView.findViewById(R.id.wv);
        }
    }
}

