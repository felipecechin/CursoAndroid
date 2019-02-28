package com.parse.starter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe on 28/02/2019.
 */
public class UsuariosAdapter extends ArrayAdapter<ParseUser> {

    private Context context;
    private ArrayList<ParseUser> usuarios;

    public UsuariosAdapter(Context context, ArrayList<ParseUser> objects) {
        super(context, 0, objects);
        this.context = context;
        usuarios = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        //verifica se nao existe o objeto view criado,
        // pois a view utilizada é armazenada no cache do android e fica na variavel
        // convertView

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_usuarios, parent, false);

        }

        TextView username = (TextView) view.findViewById(R.id.text_username);

        ParseUser parseUser = usuarios.get(position);
        username.setText(parseUser.getUsername());

        return view;
    }
}
