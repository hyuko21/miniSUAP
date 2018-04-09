package ifrn.tads.ddm.minisuap.models;

public class Student {
    
    private String matricula;
    private String nome;
    private String email;
    private String curso;
    private String campus;
    private String situacao;

    public Student(String matricula, String nome, String email, String curso, String campus, String situacao) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.curso = curso;
        this.campus = campus;
        this.situacao = situacao;
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getsituacao() {
        return situacao;
    }

    public void setsituacao(String situacao) {
        this.situacao = situacao;
    }
}
