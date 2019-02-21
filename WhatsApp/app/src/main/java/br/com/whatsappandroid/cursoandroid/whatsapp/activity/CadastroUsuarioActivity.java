package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = findViewById(R.id.edit_cadastro_nome);
        email = findViewById(R.id.edit_cadastro_email);
        senha = findViewById(R.id.edit_cadastro_senha);
        botaoCadastrar = findViewById(R.id.bt_cadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        boolean erro = verificarCampos(usuario);
        if (!erro) {
            autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_LONG).show();
                        String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                        usuario.setId(identificadorUsuario);
                        usuario.salvar();
                        Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
                        preferencias.salvarDados(identificadorUsuario);

                        abrirLoginUsuario();
                    } else {
                        String erroExcecao = "";
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números!";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail!";
                        } catch (FirebaseAuthUserCollisionException e) {
                            erroExcecao = "Esse email já está em uso.";
                        } catch (Exception e) {
                            erroExcecao = "Erro ao efetuar o cadastro.";
                            e.printStackTrace();
                        }
                        Toast.makeText(CadastroUsuarioActivity.this, erroExcecao, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void abrirLoginUsuario() {
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean verificarCampos(Usuario usuario) {
        boolean erro = false;
        if (usuario.getNome().isEmpty() || usuario.getNome() == null) {
            Toast.makeText(CadastroUsuarioActivity.this, "Preencha o campo nome.", Toast.LENGTH_SHORT).show();
            erro = true;
        } else if (usuario.getEmail().isEmpty() || usuario.getEmail() == null) {
            Toast.makeText(CadastroUsuarioActivity.this, "Preencha o campo e-mail.", Toast.LENGTH_SHORT).show();
            erro = true;
        } else if (usuario.getSenha().isEmpty() || usuario.getSenha() == null) {
            Toast.makeText(CadastroUsuarioActivity.this, "Preencha o campo senha.", Toast.LENGTH_SHORT).show();
            erro = true;
        }
        return erro;
    }
}
