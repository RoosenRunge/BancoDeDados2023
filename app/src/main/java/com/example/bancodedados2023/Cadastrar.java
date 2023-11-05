package com.example.bancodedados2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastrar extends AppCompatActivity {
    //variável corresponde ao botão de cadastrar
    private Button btnCadastrar;

    //variáveis correspondente aos campoos que serão preenchido para o cadastro do contato
    private EditText nome;
    private EditText telefone;
    private EditText email;

    //Variável criada para apontar para o Banco de dados
    private ContatosDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        //associando a variável ao um novo objeto  da classe que implementa o banco de dados.
        db = new ContatosDB(this);

        //"linkando" as váriaveis com as Views no XML
        btnCadastrar = findViewById(R.id.castrarCXML);
        nome =  findViewById(R.id.nomeCXML);
        telefone = findViewById(R.id.telefoneCXML);
        email =  findViewById(R.id.emailCXML);



//implementando o Listener do botão.
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verifica se houve tentativa de cadastro sem preenchimento de todos os campos
                if (nome.getText().length() == 0 ||email.getText().length() == 0||telefone.getText().length() == 0) {
                    Toast.makeText(Cadastrar.this, "Por favor preencha todos os campos!", Toast.LENGTH_SHORT).show();

                } else {
                    //Convertendo os  conteúdos dos EditText para variáveis do JAVA
                    String mail = email.getText().toString();
                    String name = nome.getText().toString();
                    int phone = Integer.parseInt(telefone.getText().toString());
                    //cria um novo objeto da classe cadastro com as informações para serem inseridas na tabela do banco dados
                    Contato cadastro = new Contato(0, name, phone, mail);
                    // chama o método salavaContato do ContatosDB para a inserção na tabela
                    long id = db.salvaContato(cadastro);
                    if (id != -1)
                        Toast.makeText(Cadastrar.this, "cadastro realizado!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Cadastrar.this, "Ops! não foi possível cadastrar o contato.", Toast.LENGTH_LONG).show();

                    //limpa as caixa de texto para um novo cadastro.
                    nome.setText("");
                    telefone.setText("");
                    email.setText("");
                }
            }
        });
    }
}
