package com.example.projecttwo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AudioSearch extends AppCompatActivity {
    String urli;
    RecyclerView rv;
    List<Result2> Data;
    String code;
    String url;
    static TextView tv1;
    static TextView tv2;
    static TextView tv3;
    static ImageView iv;
    int pageNumber=1;
    int nxt=1;
    static MediaPlayer mp;
    static Handler handler=new Handler();
    static SeekBar sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.fragment_audios_search);

        sb=(SeekBar)findViewById(R.id.sb);
        sb.setMax(100);
        rv=(RecyclerView)findViewById(R.id.recyclerView);
        tv1=(TextView)findViewById(R.id.title);
        tv2=(TextView)findViewById(R.id.textCurrentTime);
        tv3=(TextView)findViewById(R.id.textTotalDuration);
        iv=(ImageView)findViewById(R.id.imagePlayPause);
        mp=new MediaPlayer();
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

        Intent intent=getIntent();
        urli=intent.getStringExtra("urli");
        url="search_audio?search="+urli;
        nxt=1;
        pageNumber=1;
        url=url+"&page="+pageNumber;
        pageNumber++;
        getSetGo();

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (! recyclerView.canScrollVertically(1)&&nxt==1) {
                    url="search_audio?search="+urli;
                    url=url+"&page="+pageNumber;
                    pageNumber++;
                    getSetGo();
                }
            }
        });
    }
    public void getSetGo(){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(AudioSearch.this);
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
                //Data = response.body().getResults();
                setAudioDataInRecyclerView();
            }

            @Override
            public void onFailure(Call<VideoListResponse2> call, Throwable t) {
                Toast.makeText(AudioSearch.this, t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });

    }

    public static void onKhaleed(String a, String b){
        tv1.setText(b);
        mp.reset();
        iv.setImageResource(R.drawable.ic_pause);
        try {
            mp.setDataSource(a);
            mp.prepare();
            tv3.setText(millisecondsToTimer(mp.getDuration()));
            mp.start();
            updateSeekBar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAudioDataInRecyclerView(){
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AudioSearch.this);
        rv.setLayoutManager(linearLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        AudioSearchAdapter audioSearchAdapter = new AudioSearchAdapter(AudioSearch.this, Data);
        if(pageNumber==2){
            rv.setAdapter(audioSearchAdapter); // set the Adapter to RecyclerView
        }else{
            audioSearchAdapter.notifyDataSetChanged();
            rv.scrollToPosition((pageNumber-2)*10);
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
