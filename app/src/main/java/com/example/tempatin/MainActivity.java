package com.example.tempatin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {

    Button login, register;
    EditText email,password;
    TextView errorInputEmail1,errorInputPass1;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        register =  findViewById(R.id.register);

        email = findViewById(R.id.inputEmail1);
        password = findViewById(R.id.inputPass1);

        errorInputEmail1 = findViewById(R.id.errorInputEmail1);
        errorInputPass1 = findViewById(R.id.errorInputPass1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetError();
                loginUser(email.getText().toString(),password.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daftar = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(daftar);
            }
        });
    }

    private void loginUser(String email,String password){
        if(email.length() == 0){
            errorInputEmail1.setText("Masukan email anada");
        }

        if(password.length() == 0){
            errorInputPass1.setText("Masukan password anda");
        }

        if(email.length() != 0 && password.length() != 0){
            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            JsonObject dataLogin = new JsonObject();
            dataLogin.addProperty("email",email);
            dataLogin.addProperty("password", password);


            Call<responseAuth> insertRegisterCall = mApiInterface.postLogin(dataLogin);
            insertRegisterCall.enqueue(new Callback<responseAuth>() {

                @Override
                public void onResponse(Call<responseAuth> call, Response<responseAuth> response) {
                    int status = Integer.parseInt(response.body().getStatus());
                    JsonObject pesan = response.body().getPesan();
                    try {
                        switch (status){
                            case 0:
                                if(pesan.get("email") != null){
                                    String email = pesan.get("email").getAsJsonArray().get(0).getAsString();
                                    errorInputEmail1.setText(email);
                                }

                                if(pesan.get("password") != null){
                                    String password = pesan.get("password").getAsJsonArray().get(0).getAsString();
                                    errorInputPass1.setText(password);
                                }
                                break;
                            case 1:
                                Toast.makeText(MainActivity.this,
                                        "Sukses Login", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this,
                                e.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<responseAuth> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private void resetError() {
        errorInputEmail1.setText(null);
        errorInputPass1.setText(null);
    }
}
