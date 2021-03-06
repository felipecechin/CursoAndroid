package projetofragmento.cursoandroid.com.projetofragmento;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botaoLogar;
    private Boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoLogar = findViewById(R.id.bt_logar);
        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (status) {

                    LoginFragment loginFragment = new LoginFragment();

                    fragmentTransaction.add(R.id.rl_container_fragmento, loginFragment);
                    fragmentTransaction.commit();

                    botaoLogar.setText("Cadastre-se");
                    status = false;
                } else {
                    CadastroFragment cadastroFragment = new CadastroFragment();
                    fragmentTransaction.add(R.id.rl_container_fragmento, cadastroFragment);
                    fragmentTransaction.commit();

                    botaoLogar.setText("Logar");
                    status = true;
                }

            }
        });

    }
}
