package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

import br.com.whatsappandroid.cursoandroid.whatsapp.Manifest;
import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Permissao;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Preferencias;

public class LoginActivity extends AppCompatActivity {

    private EditText telefone;
    private EditText nome;
    private EditText codPais;
    private EditText codArea;
    private Button cadastrar;
    private String[] permissoesNecessarias = new String[]{
            android.Manifest.permission.SEND_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1, this, permissoesNecessarias);

        telefone = findViewById(R.id.edit_telefone);
        nome = findViewById(R.id.edit_nome);
        codPais = findViewById(R.id.edit_cod_pais);
        codArea = findViewById(R.id.edit_cod_area);
        cadastrar = findViewById(R.id.bt_cadastrar);

        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskCodArea = new SimpleMaskFormatter("NN");

        MaskTextWatcher maskCodArea = new MaskTextWatcher(codArea, simpleMaskCodArea);
        MaskTextWatcher maskCodPais = new MaskTextWatcher(codPais, simpleMaskCodPais);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);

        telefone.addTextChangedListener(maskTelefone);
        codPais.addTextChangedListener(maskCodPais);
        codArea.addTextChangedListener(maskCodArea);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto = codPais.getText().toString()+codArea.getText().toString()+telefone.getText().toString();
                String telefoneSemFormatacao = telefoneCompleto.replace("+","");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-","");

                //Gerar token
                Random random = new Random();
                int numeroRandomico = random.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio = "WhatsApp Código de confirmação: "+token;

                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

//                HashMap<String, String> usuario = preferencias.getDadosUsuario();
//                Log.i("TOKEN", usuario.get("token"));

                boolean enviadoSMS = enviaSMS("+"+telefoneSemFormatacao, mensagemEnvio);

            }
        });
    }

    private boolean enviaSMS(String telefone, String mensagem) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int resultado:grantResults) {
            if (resultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
