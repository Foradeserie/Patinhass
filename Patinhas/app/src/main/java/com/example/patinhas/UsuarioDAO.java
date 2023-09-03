//package com.example.patinhas;
//
//import android.util.Log;
//import android.widget.Toast;
//import android.content.Context;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UsuarioDAO implements UsuarioCallback  {
//    private FirebaseFirestore db;
//    private CollectionReference usuariosCollection;
//
//    public UsuarioDAO() {
//        db = FirebaseFirestore.getInstance();
//        usuariosCollection = db.collection("usuarios");
//
//
//    }
//
//    public void inserir(Login login, final UsuarioCallback.Callback<String> callback, final Context context) {
//        // Verifique se o email já está registrado antes de inserir
//        usuariosCollection.whereEqualTo("email", login.getEmail())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            if (task.getResult().isEmpty()) {
//                                // O email não está registrado, então insira
//                                usuariosCollection.add(login)
//                                        .addOnSuccessListener(documentReference -> {
//                                            callback.onCallback("Usuário inserido com ID: " + documentReference.getId());
//                                        })
//                                        .addOnFailureListener(e -> {
//                                            callback.onCallback("Erro ao inserir usuário: " + e.getMessage());
//                                        });
//                            } else {
//                                callback.onCallback("Este email já está cadastrado!");
//                            }
//                        } else {
//                            Log.d("UsuarioDAO", "Erro ao verificar email duplicado", task.getException());
//                            callback.onCallback("Erro ao verificar email duplicado: " + task.getException().getMessage());
//                        }
//                    }
//                });
//    }
//
//
//    public void verificarLogin(String email, String senha, final Context context) {
//        usuariosCollection.whereEqualTo("email", email)
//                .whereEqualTo("senha", senha)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            if (!task.getResult().isEmpty()) {
//                                // Encontrou um usuário com o email e senha correspondentes
//                                Toast.makeText(context, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(context, "Email ou senha incorretos.", Toast.LENGTH_SHORT).show();
//                            }
//                        } else {
//                            Log.d("UsuarioDAO", "Erro ao verificar login", task.getException());
//                        }
//                    }
//                });
//    }
//
//    public void obterTodos(final UsuarioCallback<List<Login>> callback) {
//        usuariosCollection.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            List<Login> usuarios = new ArrayList<>();
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Login usuario = document.toObject(Login.class);
//                                usuarios.add(usuario);
//                            }
//                            callback.onCallback(usuarios);
//                        } else {
//                            Log.d("UsuarioDAO", "Erro ao obter todos os usuários", task.getException());
//                            callback.onCallback(null);
//                        }
//                    }
//                });
//    }
//}
