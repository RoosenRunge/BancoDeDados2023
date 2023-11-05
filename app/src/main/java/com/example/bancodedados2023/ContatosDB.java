package com.example.bancodedados2023;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class ContatosDB extends SQLiteOpenHelper {

    //2 - criando constantes para formação o "statement" de criação da tabela do banco de dados
    // e outras constantes que serão usadas

    public static final String TAG = "sql";
    public static final String NOME_BANCO = "MeuBancodeDados.db";
    public static final int VERSAO_BANCO = 1;
    public static final String TABLE_NAME = "contatos";
    public static final String COLUNA0 = "_id";
    public static final String COLUNA1 = "nome";
    public static final String COLUNA2 = "telefone";
    public static final String COLUNA3 = "email";

    // 3 - criação do "statement que define a tabela do banco de dados"
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " +TABLE_NAME + " ("
                    + COLUNA0 +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUNA1 + " TEXT,"
                    + COLUNA2 + " INT,"
                    + COLUNA3 + " TEXT )";


    // 1 - criando construtor do banco de dados
    public ContatosDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // 4 - criação da tabela de contatos
        db.execSQL(SQL_CREATE_TABLE);

        //Logcat para informar a criação da tabela
        Log.d(TAG, "Tabela"+TABLE_NAME+" criada com sucesso");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // caso mude a versão do bnco de dados, podemos executar alguma instrução SQL aqui, além de atualizar a versão do banco
    }


    //5 - Criando o metodo utilizado para salvar os contatos ou atualizar  contato existente
    //recebe um contato( objeto Contato) já existente para atualização ou um novo contato para cadastro

    // o método recebe como entrada um objeto da classe contato
    public long salvaContato(Contato contato){
        //lê o valor do id do objeto ( Se id = 0 cadastro/ Se id!=0 atualização)
        long id = contato.get_id();
        //abre a conexão com o banco de dados
        SQLiteDatabase db = getWritableDatabase();//abre a conexão com o banco de dados
        try{

            //cria a variável que aponta para o objeto ContentValues()  e que representa o conteudo do registro a ser alterado ou criado
            //em um formato que o banco de dados entende.
            ContentValues valores = new ContentValues();
            //transfere os valores das variáveis de instancia do objeto para a variável valor na forma (chave, valor)
            valores.put(COLUNA1,contato.getNome());
            valores.put(COLUNA2, contato.getTelefone());
            valores.put(COLUNA3, contato.getEmail());
            if(id!=0){//se já existe este contato e queremos simplesmente atualizá-lo
                // String _id = String.valueOf(id);
                //String[] whereArgs = new String[]{_id};
                int count = db.update(TABLE_NAME, valores, "_id =?",new String[]{String.valueOf(id)});
                return count; // retorna o numero de linhas alteradas.

            }
            else{//se não existe o contato e vamos incluí-lo na tabela.
                id = db.insert(TABLE_NAME,null,valores);
                return id; //retorna o ID da nova linha inserida ou -1 se ocorrer erro

            }
        }finally{
            db.close();//fecha a conexão
        }
    }


    //6 - metodo utilizado para apagar um contato
    //Este método recebe uma String com o nome do contato

    public int apagaContato(String nomeContato) {
        SQLiteDatabase db = getWritableDatabase();//abre a conexão com o banco de dados
        try {
            //apago o registro onde na coluna nome o valor do registro corresponde a String passada.
            int count = db.delete(TABLE_NAME, "nome=?", new String[]{nomeContato});
            Log.i(TAG, "deletou registro =>" + count);
            return count;//retorna zero se não houver contato registro que coincida com o valor passado para o método.
        } finally {
            db.close();//fecha a conexão
        }
    }



    //7 - metodo que devolve um ArrayList com todos os contatos da agenda.
    public ArrayList<Contato> findAll() {
        SQLiteDatabase db = getWritableDatabase();
        //Declarando o ArrayList de objetos do tipo Contato.
        ArrayList<Contato> lista = new ArrayList<>();
        try {
            //colocando o valor dos campos em null, retorna todos os registros da tabela.
            Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {//move o cursor para o primeiro registro
                //laço do while para correr todos os registros e formar o Array que representa a lista de registros.
                do {
                    int id = c.getInt(c.getColumnIndexOrThrow("_id"));
                    String nome = c.getString(c.getColumnIndexOrThrow("nome"));
                    String email = c.getString(c.getColumnIndexOrThrow("email"));
                    int telefone = c.getInt(c.getColumnIndexOrThrow("telefone"));

                    Contato currentContact = new Contato(id, nome, telefone, email);
                    lista.add(currentContact);

                } while (c.moveToNext());//move para a próxima posição

            }
            return lista;//retorna o Array contendo os contatos
        } finally {
            db.close();//fecha a conexão
        }
    }


    // 8 - pesquisa um contato específico pelo nome do contato passado pela String nomeContato do método.
    //será utilizado para carregar o contato no processo de atualização

    public Contato pesquisaContato(String nomeContato) {
        SQLiteDatabase db = getWritableDatabase();
        String nome;
        String email;
        int telefone;
        int id;

        try {
            Cursor c = db.query(TABLE_NAME, null,"nome=?", new String[]{nomeContato}, null, null, null, null);
            if(c.moveToFirst()) {//verifica se o contato existe, se sim extrai os valores para criação  um objeto Contato com os valores
                id= c.getInt(c.getColumnIndexOrThrow("_id"));
                nome = c.getString(c.getColumnIndexOrThrow("nome"));
                email = c.getString(c.getColumnIndexOrThrow("email"));
                telefone = c.getInt(c.getColumnIndexOrThrow("telefone"));
                // Contato contato = new Contato(id, nome, telefone, email);
            }
            //Se não for encontrado, define  valores dummy para criação de um objeto
            //pois o método retorna um objeto do tipo contato
            else{
                id = 0;
                nome = "dummy";
                telefone = 0 ;
                email = "xxxxx";
            }
            Contato contato = new Contato(id, nome, telefone, email);
            return contato;
        } finally {
            db.close();
        }
    }

}


