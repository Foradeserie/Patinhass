package com.example.patinhas;

import java.util.List;

    public interface UsuarioCallback<T> {
        void onSuccess(T data);
        void onFailure(String error);
    }


// diz se deu certo a comunicação com o usuario ou não

