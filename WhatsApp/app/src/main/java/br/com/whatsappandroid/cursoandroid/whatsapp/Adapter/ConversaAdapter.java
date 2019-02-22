package br.com.whatsappandroid.cursoandroid.whatsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Contato;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Conversa;

/**
 * Created by Felipe on 22/02/2019.
 */
public class ConversaAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Conversa> conversas;

    public ConversaAdapter(Context context, ArrayList<Conversa> objects) {
        super(context, 0, objects);
        this.context = context;
        this.conversas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        //verifica se a lista esta vazia
        if (conversas!=null) {

            //inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta view a partir do xml
            view = inflater.inflate(R.layout.lista_conversa, parent, false);

            //recupera elemento para exibição
            TextView nomeContato = view.findViewById(R.id.tv_titulo);
            TextView mensagem = view.findViewById(R.id.tv_subtitulo);

            Conversa conversa = conversas.get(position);
            nomeContato.setText(conversa.getNome());
            mensagem.setText(conversa.getMensagem());
        }

        return view;
    }
}
