package ifrn.tads.ddm.minisuap.models;

import com.orm.SugarRecord;

import java.util.List;

public class Student extends SugarRecord {
    
    private String matricula;
    private String nome;
    private String curso;
    private String campus;
    private String situacao;
    private Student student;

    public Student(String matricula, String nome, String curso, String campus, String situacao) {
        this.matricula = matricula;
        this.nome = nome;
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

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getContacts() {
        String[] id = {"" + getId()};
        return Student.find(Student.class, "student = ?", id);
    }
}
