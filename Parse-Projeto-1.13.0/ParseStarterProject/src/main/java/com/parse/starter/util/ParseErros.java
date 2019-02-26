package com.parse.starter.util;

import android.content.Intent;

import java.util.HashMap;

/**
 * Created by Felipe on 26/02/2019.
 */
public class ParseErros {

    private HashMap<Integer, String> erros;

    public ParseErros() {
        this.erros = new HashMap<>();
        this.erros.put(202, "Usuário já existe, escolhe um outro nome de usuário!");
        this.erros.put(201, "A senha não foi preenchida!");
    }

    public String getErro(int codErro) {
        return this.erros.get(codErro);
    }
}
