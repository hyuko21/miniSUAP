package ifrn.tads.ddm.minisuap.models;

import com.orm.SugarRecord;

public class Contact extends SugarRecord {

    private String matricula;
    private String nome_usual;
    private String email;

    public Contact(String matricula, String nome_usual, String email) {
        this.matricula = matricula;
        this.nome_usual = nome_usual;
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome_usual() {
        return nome_usual;
    }

    public void setNome_usual(String nome_usual) {
        this.nome_usual = nome_usual;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
