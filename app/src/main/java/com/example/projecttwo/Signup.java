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

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends Fragment {
    EditText Username;
    EditText Email;
    EditText Password;
    EditText Conpass;
    Button btn;
    String email="";
    String username="";
    String password="";
    String conpass="";
    SignUpResponse signUpResponsesData;

    public Signup(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.fragment_signup, container, false);
        Username=(EditText) InputFragmentView.findViewById(R.id.username);
        Email=(EditText)InputFragmentView.findViewById(R.id.emails);
        Password=(EditText)InputFragmentView.findViewById(R.id.pass);
        Conpass=(EditText)InputFragmentView.findViewById(R.id.conpass);
        btn=(Button)InputFragmentView.findViewById(R.id.btnsin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=Email.getText().toString().trim();
                password=Password.getText().toString().trim();
                username=Username.getText().toString().trim();
                conpass=Conpass.getText().toString().trim();
                if(verify()){
                    signup();
                }
            }
        });
        return InputFragmentView;
    }
    public boolean verify(){
        if(!password.equals(conpass)){
            Toast.makeText(getActivity(), "Passwords Missmatched", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length()<4){
            Toast.makeText(getActivity(), "Passwords length too short", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!(email.contains("@")&&email.contains(".com"))){
            Toast.makeText(getActivity(), "Email must be of abc@xyz format", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(username.length()==0){
            Toast.makeText(getActivity(), "Username Can't be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void signup(){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        // enqueue is used for callback response and error
        Api.getClient().registration(username,password,email).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                signUpResponsesData = response.body();
                Toast.makeText(getActivity(), "Ta Da.. Signup Completed.. Now you can Login!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                openLogin();
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                String s= t.getStackTrace().toString();
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void openLogin(){
        ViewPager vp=(ViewPager)getActivity().findViewById(R.id.viewPagerS);
        vp.setCurrentItem(0);
    }
}
