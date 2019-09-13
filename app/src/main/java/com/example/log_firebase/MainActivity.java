package com.example.log_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private EditText textemail, textpassword;
    private Button btn_registrar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth =  FirebaseAuth.getInstance();
        textemail = (EditText) findViewById(R.id.txt_correo);
        textpassword = (EditText) findViewById(R.id.txt_contraseña);
        btn_registrar = (Button) findViewById(R.id.button_registrar);
        progressDialog = new ProgressDialog(this);
    }

    private void registrarUsuario()
    {
        String email = textemail.getText().toString().trim();
        String pass = textpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "debes igresar un email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(this, "Debes ingresar una contraseña puñetas", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("se esta realizando registro en linea");
        progressDialog.show();

        //creando la autenticacion

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                           Toast.makeText(MainActivity.this, "Se a creado tu asquerosa cuenta", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(MainActivity.this, "ya existe tu cuenta asquerosa", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "No se pudo crear tu asquerosa cuenta", Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public void onClick(View view)
    {
        registrarUsuario();
    }

}
