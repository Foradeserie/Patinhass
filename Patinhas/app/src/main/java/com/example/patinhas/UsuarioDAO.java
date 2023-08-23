package com.example.patinhas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Conexao conexao;
    private SQLiteDatabase db;

    public UsuarioDAO(Context context){
        conexao= new Conexao(context);
        db= conexao.getWritableDatabase();
    }

    public long inserir(Login login){
        ContentValues values = new ContentValues();
        values.put("nome", login.getNome());
        values.put("dataNascimento", login.getDataNascimento());
        values.put("email",login.getEmail());
        values.put("senha", login.getSenha());
        values.put("estado", login.getEstado());
        values.put("cidade", login.getCidade());
        return db.insert("login", null, values);
    }

    public List<Login> obterTodos(){
        List<Login> usuarios = new ArrayList<>();
        Cursor cursor=db.query("login", new String[]{"id", "nome","dataNascimento", "email", "senha", "estado", "cidade"},
        null,null,null,null,null);
        while (cursor.moveToNext()){
            Login usuario=new Login();
            usuario.setId(cursor.getLong(0));
            usuario.setNome(cursor.getString(1));
            usuario.setDataNascimento(cursor.getString(2));
            usuario.setEmail(cursor.getString(3));
            usuario.setSenha(cursor.getString(4));
            usuario.setEstado(cursor.getString(5));
            usuario.setCidade(cursor.getString(6));
            usuarios.add(usuario);
        }
        return usuarios;
    }
}
