package com.example.comprefacil.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comprefacil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Estamos na SingUp e vamos para Login
        View acb_entre_singup = findViewById(R.id.acb_entre_singup);
        acb_entre_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_entre = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent_entre);
            }
        });

        Button btnRegister =  findViewById(R.id.acb_cadastrar_singup);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText etNewNome =  findViewById(R.id.et_nome_singup);
                final String newNome = etNewNome.getText().toString();
                if(newNome.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Campo de login não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewEmail =  findViewById(R.id.et_email_singup);
                final String newEmail = etNewEmail.getText().toString();
                if(newEmail.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Campo de login não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewTelefone =  findViewById(R.id.et_telefone_singup);
                final String newTelefone = etNewTelefone.getText().toString();
                if(newEmail.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Campo de login não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewPassword =  findViewById(R.id.et_senha_singup);
                final String newPassword = etNewPassword.getText().toString();
                if(newPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Campo de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewPasswordCheck =  findViewById(R.id.et_confirmarsenha_singup);
                String newPasswordCheck = etNewPasswordCheck.getText().toString();
                if(newPasswordCheck.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Campo de checagem de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!newPassword.equals(newPasswordCheck)) {
                    Toast.makeText(SignUpActivity.this, "Senha não confere", Toast.LENGTH_LONG).show();
                    return;
                }

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "register.php", "POST", "UTF-8");
                        httpRequest.addParam("nome", newNome);
                        httpRequest.addParam("email", newEmail);
                        httpRequest.addParam("telefone", newTelefone);
                        httpRequest.addParam("senha", newPassword);

                        try {
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is, "UTF-8");
                            httpRequest.finish();

                            JSONObject jsonObject = new JSONObject(result);
                            final int success = jsonObject.getInt("success");
                            if(success == 1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "Novo usuario registrado com sucesso", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                });
                            }
                            else {
                                final String error = jsonObject.getString("error");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}