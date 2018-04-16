package ifrn.tads.ddm.minisuap.models;

import com.orm.SugarRecord;

public class Contact extends SugarRecord {

    private String matricula;
    private String nome;
    private String email;

    public Contact() {
        super();
    }

    public Contact(String matricula, String nome, String email) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
