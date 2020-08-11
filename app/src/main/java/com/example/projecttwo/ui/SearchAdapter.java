package com.example.projecttwo.ui;

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

import com.example.projecttwo.R;
import com.example.projecttwo.Result;
import com.example.projecttwo.UsersAdapter;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context context;
    private List<Result> Data;
    public SearchAdapter(Context context, List<Result> searchListResponseData) {
        this.Data = searchListResponseData;
        this.context = context;
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
    }
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, null);
        SearchAdapter.SearchViewHolder searchViewHolder = new SearchViewHolder(view);
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder holder, final int position) {
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        String web=Data.get(position).getUrl();
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
        return Data.size(); // size of the list items
    }
    class SearchViewHolder extends RecyclerView.ViewHolder {
        WebView webview;
        SearchViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            webview=(WebView) itemView.findViewById(R.id.webv);
            Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        }
    }
}
