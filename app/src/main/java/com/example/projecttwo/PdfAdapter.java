package com.example.projecttwo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {
    Context context;
    List<Result> Data;


    public PdfAdapter(Context context, List<Result> userListResponseData) {
        this.Data = userListResponseData;
        this.context = context;
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
    }
    @Override
    public PdfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(context).inflate(R.layout.audio_list_item, null);
        PdfViewHolder pdfViewHolder = new PdfViewHolder(view);
        //Toast.makeText(context, "Setting things", Toast.LENGTH_SHORT).show();
        return pdfViewHolder;
    }
    @Override
    public void onBindViewHolder(final PdfViewHolder holder, final int position) {
        holder.tv1.setText(Data.get(position).getTitle());
        holder.tv2.setText(Data.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=Data.get(position).getUrl();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setDataAndType(Uri.parse(url), "application/pdf");

                Intent chooser = Intent.createChooser(browserIntent,Data.get(position).getTitle());
                chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional

                context.startActivity(chooser);

            }

        });
    }
    @Override
    public int getItemCount() {
        return Data.size(); // size of the list items
    }

    class PdfViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        TextView tv2;

        public PdfViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            tv1=(TextView) itemView.findViewById(R.id.tv1);
            tv2=(TextView) itemView.findViewById(R.id.tv2);
        }
    }
}
