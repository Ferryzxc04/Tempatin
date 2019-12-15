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

public class Main2Activity extends AppCompatActivity {

    Button register1;
    TextView login1,errorInputNamaDepan1,errorInputNamaBelakang1,errorInputEmail1,errorInputPass1;
    EditText email,password,namaDepan,namaBelakang;
    ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        namaDepan = findViewById(R.id.inputNamaDepan1);
        namaBelakang = findViewById(R.id.inputNamaBelakang1);
        email = findViewById(R.id.inputEmail1);
        password = findViewById(R.id.inputPass1);

        errorInputNamaDepan1 = findViewById(R.id.errorInputNamaDepan1);
        errorInputNamaBelakang1 = findViewById(R.id.errorInputNamaBelakang1);
        errorInputEmail1 = findViewById(R.id.errorInputEmail1);
        errorInputPass1 = findViewById(R.id.errorInputPass1);

        register1 = findViewById(R.id.register);
        login1 = findViewById(R.id.btnLogin);

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masuk = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(masuk);
            }
        });

        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetError();
                daftarUser(namaDepan.getText().toString(),namaBelakang.getText().toString(),email.getText().toString(),password.getText().toString());
            }
        });

    }

    private void daftarUser(String namaDepan, String namaBelakang, String email, String password){
        if(namaDepan.length() == 0){
            errorInputNamaDepan1.setText("Masukan nama depan anda");
        }

        if(namaBelakang.length() == 0){
            errorInputNamaBelakang1.setText("Masukan nama belakang anda");
        }

        if(email.length() == 0){
            errorInputEmail1.setText("Masukan email anda");
        }

        if(password.length() == 0){
            errorInputPass1.setText("Masukan password anda");
        }

        if(namaDepan.length() != 0 && namaBelakang.length() != 0 && email.length() != 0 && password.length() != 0){
            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            JsonObject dataRegister = new JsonObject();
            dataRegister.addProperty("email",email);
            dataRegister.addProperty("password", password);
            dataRegister.addProperty("nama_depan", namaDepan);
            dataRegister.addProperty("nama_belakang", namaBelakang);

            Call<responseAuth> insertRegisterCall = mApiInterface.postRegister(dataRegister);
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
                                Intent masuk = new Intent(Main2Activity.this, MainActivity.class);
                                startActivity(masuk);
                                break;
                        }
                    }catch (Exception e){

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
        errorInputNamaDepan1.setText(null);
        errorInputNamaBelakang1.setText(null);
        errorInputEmail1.setText(null);
        errorInputPass1.setText(null);
    }
}
