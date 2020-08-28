package com.example.projecttwo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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

import static android.widget.Toast.LENGTH_LONG;

public class Audios extends Fragment {
    public Audios() {}

    RecyclerView rv;
    String urlins="audio?page=";
    String url="audio";
    List<Result2> Data;
    static TextView tv1;
    static TextView tv2;
    static TextView tv3;
    static ImageView iv;
    static MediaPlayer mp;
    static Handler handler=new Handler();
    static SeekBar sb;
    ImageButton ib;
    EditText et;
    String fet;
    int nxt=1;
    int pageNumber=1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragView =inflater.inflate(R.layout.fragment_audios, container, false);

        sb=(SeekBar)InputFragView.findViewById(R.id.sb);
        sb.setMax(100);
        rv=(RecyclerView)InputFragView.findViewById(R.id.recyclerView);
        nxt=1;
        pageNumber=1;
        url=urlins+Integer.toString(pageNumber);
        pageNumber++;
        getAudioDataList();


        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (! recyclerView.canScrollVertically(1)&&nxt==1) {
                    url=urlins+Integer.toString(pageNumber);
                    pageNumber++;
                    getAudioDataList();
                }
                /*if(!recyclerView.canScrollVertically(-1)&&pageNumber>2){
                    pageNumber=pageNumber-2;
                    url=urlins+Integer.toString(pageNumber);
                    pageNumber++;
                    getVideoDataList();
                }*/
            }
        });
        getAudioDataList();
        tv1=(TextView)InputFragView.findViewById(R.id.title);
        tv2=(TextView)InputFragView.findViewById(R.id.textCurrentTime);
        tv3=(TextView)InputFragView.findViewById(R.id.textTotalDuration);
        iv=(ImageView)InputFragView.findViewById(R.id.imagePlayPause);
        mp=new MediaPlayer();
        /*mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                if(percent==100){
                    pd.dismiss();
                }
            }
        });*/
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    handler.removeCallbacks(updater);
                    mp.pause();
                    iv.setImageResource(R.drawable.ic_play);
                }else{
                    iv.setImageResource(R.drawable.ic_pause);
                    mp.start();
                    updateSeekBar();
                }
            }
        });

        ib=(ImageButton) InputFragView.findViewById(R.id.imageButton);
        et=(EditText) InputFragView.findViewById(R.id.editText);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearch();

            }
        });
        //getAudioDataList();
        return InputFragView;
    }

    public void goToSearch(){
        fet=et.getText().toString();
        if(fet.length()>0){
            Intent intent=new Intent(getActivity(),AudioSearch.class);
            intent.putExtra("urli",fet);
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(), "What do you wanna search?", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAudioDataList(){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        ApiSVL.getClient().getVideosList(url).enqueue(new Callback<VideoListResponse2>() {
            @Override
            public void onResponse(Call<VideoListResponse2> call, Response<VideoListResponse2> response) {
                progressDialog.dismiss();
                if(pageNumber==2){
                    Data = response.body().getResults();
                }else{
                    Data.addAll(response.body().getResults());
                }
                if(response.body().getNext()==null){
                    nxt=0;
                }

                setDataInRecyclerView();
            }

            @Override
            public void onFailure(Call<VideoListResponse2> call, Throwable t) {
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
        if(pageNumber==2){
            rv.setAdapter(audioAdapter); // set the Adapter to RecyclerView
        }else{
            audioAdapter.notifyDataSetChanged();
            rv.scrollToPosition((pageNumber-2)*10);
        }
    }

    public static void onClickCalled(String url, String Title, final Context context)  {
        // display a progress dialog
        //final ProgressDialog pd = new ProgressDialog(context);
        //pd.setCancelable(false); // set cancelable to false
        //pd.setMessage("Please Wait"); // set message
        //pd.show(); // show progress dialog
        Toast.makeText(context,"Setting Music Player.. Please Wait",LENGTH_LONG);
        tv1.setText(Title);
        mp.reset();
        iv.setImageResource(R.drawable.ic_pause);
        try {
            mp.setDataSource(url);
            mp.prepare();
            tv3.setText(millisecondsToTimer(mp.getDuration()));
            mp.start();
            updateSeekBar();
            /*if(mp.isPlaying()){
                pd.dismiss();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Runnable updater=new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration=mp.getCurrentPosition();
            tv2.setText(millisecondsToTimer(currentDuration));
        }
    };

    private static void updateSeekBar(){
        if(mp.isPlaying()){
            sb.setProgress((int)(((float)mp.getCurrentPosition()/mp.getDuration())*100));
            handler.postDelayed(updater,1000);
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
