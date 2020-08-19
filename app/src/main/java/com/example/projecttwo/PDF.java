package com.example.projecttwo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PDF extends Fragment {
    public PDF() {}
    RecyclerView rv;
    String url="document_database";
    List<Result> Data;
    public static Intent browserIntent;
    static String pdf_url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View InputView= inflater.inflate(R.layout.fragment_pdf, container, false);
        rv=(RecyclerView) InputView.findViewById(R.id.recyclerView);
        getSearchDataList();
        return InputView;

    }
    public void getSearchDataList() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        ApiSVL.getClient().getVideosList(url).enqueue(new Callback<VideoListResponse>() {
            @Override
            public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
                progressDialog.dismiss();
                if(response.body().getCount()==0){
                    Toast.makeText(getActivity(), "Sorry, No result :(", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getActivity(), response.body().getCount().toString(), Toast.LENGTH_SHORT).show();
                    Data = response.body().getResults();

                    setDataInRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<VideoListResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });
    }
    public void setDataInRecyclerView(){
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        PdfAdapter pdfAdapter = new PdfAdapter(getActivity(), Data);
        rv.setAdapter(pdfAdapter); // set the Adapter to RecyclerView
    }

    /*public void onClickCalled(String url, String title){
        pdf_url="http://docs.google.com/gview?url="+url;
        getSomething();
        browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        startActivity(browserIntent);
        browserIntent.setDataAndType(Uri.parse(url), "application/pdf");

        Intent chooser = Intent.createChooser(browserIntent,title);
        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // optional

        startActivity(chooser);
        //startActivity(browserIntent);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(pdf_url);
        Intent intent=new Intent(MainActivity.this,ReaderActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }
    public void getSomething(){

    }*/

}
