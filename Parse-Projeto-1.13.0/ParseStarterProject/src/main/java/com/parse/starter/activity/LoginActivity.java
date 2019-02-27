package com.parse.starter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editLoginUsuario;
    private EditText editLoginSenha;
    private Button botaoLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editLoginSenha = (EditText) findViewById(R.id.edit_login_senha);
        editLoginUsuario = (EditText) findViewById(R.id.edit_login_usuario);
        botaoLogar = (Button) findViewById(R.id.button_logar);



        verificarUsuarioLogado();

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = editLoginUsuario.getText().toString();
                String senha = editLoginSenha.getText().toString();

                verificarLogin(usuario, senha);
            }
        });
    }

    private void verificarLogin(String usuario, String senha) {
        ParseUser.logInInBackground(usuario, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e==null) {
                    Toast.makeText(LoginActivity.this, "Login realizado", Toast.LENGTH_SHORT).show();
                    abrirAreaPrincipal();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirCadastroUsuario(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    private void verificarUsuarioLogado() {
        if (ParseUser.getCurrentUser()!=null) {
            abrirAreaPrincipal();
        }
    }

    private void abrirAreaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
