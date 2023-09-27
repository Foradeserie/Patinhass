package com.example.patinhas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestore;
    public class ConfiguracaoFirebase {

        private static DatabaseReference database;
        private static FirebaseAuth auth;
        private static FirebaseFirestore firestore;

        //Retorna as instancias dos Firebases
        public static FirebaseFirestore getFirebaseFirestore() {
            if (firestore == null) {
                firestore = FirebaseFirestore.getInstance();        //retorna a instancia do firestore
            }
            return firestore;
        }
        public static FirebaseAuth getFirebaseAuth(){
            if ( auth == null ){
                auth = FirebaseAuth.getInstance();                  //retorna a instancia do serviço de autenticação do usuario
            }
            return auth;
        }

        }


