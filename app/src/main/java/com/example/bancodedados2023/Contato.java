package com.example.bancodedados2023;

// A classe contato simplesmente de define um objeto contato, que corresponde a um contato da agenda que possui _id,nome,telefone e email
//e disponibiliza os metodos getters e settters  das suas variáveis de instância
public class Contato {
    private int _id;
    private String nome;
    private int telefone;
    private String email;

    public Contato(int _id, String nome, int telefone, String email) {
        this._id = _id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return  "\n"+ "Nome :"+nome+ "\n"+
                "Telefone :"+telefone+"\n"+
                "email: "+email+"\n";


    }
}


