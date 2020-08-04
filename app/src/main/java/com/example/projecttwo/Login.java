package com.example.projecttwo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Fragment {
    String Token;
    EditText Username, Password;
    String username="";
    String password="";
    Button btnlog;
    LogInResponse logInResponsesData;
    public Login(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View InputFragmentView2= inflater.inflate(R.layout.fragment_login, container, false);
        Username=(EditText)InputFragmentView2.findViewById(R.id.username);
        Password=(EditText)InputFragmentView2.findViewById(R.id.password);
        btnlog=(Button)InputFragmentView2.findViewById(R.id.btnlog);
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=Username.getText().toString().trim();
                password=Password.getText().toString().trim();
                if(verify()){
                    login();
                }
            }
        });
        return InputFragmentView2;
    }
    public boolean verify(){
        if(username.length()==0){
            Toast.makeText(getActivity(), "Username Can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length()<4){
            Toast.makeText(getActivity(), "Password too short", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void login(){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        // enqueue is used for callback response and error
        Api2.getClient().register(username,password).enqueue(new Callback<LogInResponse>() {
            @Override
            public void onResponse(Call<LogInResponse> call, Response<LogInResponse> response) {
                logInResponsesData=response.body();
                Token=logInResponsesData.getToken();
                Toast.makeText(getActivity(), Token, Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LogInResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
    public void goHome(){
        ViewPager vp=(ViewPager)getActivity().findViewById(R.id.viewPager);
        vp.setCurrentItem(0);
    }
}
