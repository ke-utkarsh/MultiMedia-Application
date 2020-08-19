package com.example.projecttwo;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Audios extends Fragment {
    public Audios() {}

    RecyclerView rv;
    String url="audio_database";
    List<Result> Data;
    static TextView tv1;
    TextView tv2;
    static TextView tv3;
    static ImageView iv;
    static MediaPlayer mp;
    Handler handler=new Handler();
    SeekBar sb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragView =inflater.inflate(R.layout.fragment_audios, container, false);

        sb=(SeekBar)InputFragView.findViewById(R.id.sb);
        sb.setMax(100);
        rv=(RecyclerView)InputFragView.findViewById(R.id.recyclerView);
        tv1=(TextView)InputFragView.findViewById(R.id.title);
        tv2=(TextView)InputFragView.findViewById(R.id.textCurrentTime);
        tv3=(TextView)InputFragView.findViewById(R.id.textTotalDuration);
        iv=(ImageView)InputFragView.findViewById(R.id.imagePlayPause);
        mp=new MediaPlayer();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.pause();
                    iv.setImageResource(R.drawable.ic_play);
                }else{
                    iv.setImageResource(R.drawable.ic_pause);
                    mp.start();
                }
            }
        });
        getAudioDataList();
        return InputFragView;
    }
    public void getAudioDataList(){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        ApiSVL.getClient().getVideosList(url).enqueue(new Callback<VideoListResponse>() {
            @Override
            public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
                progressDialog.dismiss();
                Data = response.body().getResults();
                setDataInRecyclerView();
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
        AudioAdapter audioAdapter = new AudioAdapter(getActivity(), Data);
        rv.setAdapter(audioAdapter); // set the Adapter to RecyclerView
    }

    public static void onClickCalled(String url, String Title)  {
        tv1.setText(Title);
        mp.reset();
        iv.setImageResource(R.drawable.ic_pause);
        try {
            mp.setDataSource(url);
            mp.prepare();
            tv3.setText(millisecondsToTimer(mp.getDuration()));
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static String millisecondsToTimer(long milliSeconds){
        String timer="";
        String secondsString="";
        int hours=(int) (milliSeconds/(1000*60*60));
        int minutes=(int) (milliSeconds%(1000*60*60))/(1000*60);
        int seconds=(int) ((milliSeconds%(1000*60*60))%(1000*60)/1000);
        if(hours>0){
            timer=hours+":";
        }
        if(seconds<10){
            secondsString="0"+seconds;
        }else{
            secondsString=""+seconds;
        }

        timer=timer+minutes+":"+secondsString;
        return timer;
    }
}
