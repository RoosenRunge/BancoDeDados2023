package com.example.bancodedados2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Atualizar extends AppCompatActivity {
    //variáveis correspondes aos botões
    private Button btnAtualiza;
    private Button btnCarrega;

    //variáveis correspondente aos campoos que serão preenchido para atualização
    private EditText nome;
    private EditText telefone;
    private EditText email;

    private ContatosDB db;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);

        //Variável criada para apontar para o Banco de dados
        db = new ContatosDB(this);

        //"linkando" as váriaveis com as Views no XML
        btnAtualiza =(Button) findViewById(R.id.btAtualizaXML);
        btnCarrega = (Button) findViewById(R.id.btCarregaXML);
        nome = (EditText) findViewById(R.id.nomeATXML);
        telefone=(EditText)findViewById(R.id.telefoneATXML);
        email =(EditText)findViewById(R.id.emailATXML);

        //listerner do botão carrega para capturar o nome do contato fornecido pelo usuário
        //Se o contato não existe limpa os campos e devolve um toast.
        //Se o contato existe caputara as informações do contato e exibe para o usuário
        btnCarrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeAtualiza = nome.getText().toString();
                // chama o método pesquisaContato  para verificar se "nome" (registro) digitado pelo usuário existe
                // se existir o banco devolve um objeto da classe Contato com as informações do registro.
                contato = db.pesquisaContato(nomeAtualiza);
                // se não existe
                if(contato.get_id()==0)
                { Toast.makeText(Atualizar.this,"Contato inexistente!",Toast.LENGTH_LONG).show();
                    nome.setText("");
                    telefone.setText("");
                    email.setText("");}
                //recebe o contato exibe nos campos para o usuário
                else {
                    nome.setText(contato.getNome());
                    telefone.setText(String.valueOf(contato.getTelefone()));
                    email.setText(contato.getEmail());
                }
            }
        });





        //listener do botão atualiza para processar a atualização do registro na tabela
        btnAtualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verfica se não foi solicitado acidentalmente para atualizar sem ter carregado previamente um contato.
                //ou após um retorno de contato inexistente.
                if(nome.getText().length()==0){
                    Toast.makeText(Atualizar.this, "Por favor insira o nome do contato!" , Toast.LENGTH_SHORT).show();

                }
                //captura dos campos da tela os valores e modifica os valores dos campos do objeto contato retornado
                else{
                    String name = nome.getText().toString();
                    String mail =email.getText().toString();
                    int phone =Integer.parseInt(telefone.getText().toString());
                    contato.setNome(name);
                    contato.setEmail(mail);
                    contato.setTelefone(phone);
                    //chama o método salva contato para a atualiação
                    long id = db.salvaContato(contato);
                    //mensagens de retorno da operação de atualização
                    if(id!=-1)
                        Toast.makeText(Atualizar.this,"atualização realizada!",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Atualizar.this,"Ops! não foi possível atualizar o contato.",Toast.LENGTH_LONG).show();

                    nome.setText("");
                    telefone.setText("");
                    email.setText("");
                }}
        } );
    }

}
