package ifrn.tads.ddm.minisuap.models;

public class Class {

    private String sigla;
    private String descricao;
    private String observacao;
    private String horarios_de_aula;

    public Class(String sigla, String descricao, String observacao, String horarios_de_aula) {
        this.sigla = sigla;
        this.descricao = descricao;
        this.observacao = observacao;
        this.horarios_de_aula = horarios_de_aula;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getHorarios_de_aula() {
        return horarios_de_aula;
    }

    public void setHorarios_de_aula(String horarios_de_aula) {
        this.horarios_de_aula = horarios_de_aula;
    }

    @Override
    public String toString() {
        return (
            sigla + " - " + descricao
            + (observacao != null ? " - " + observacao + "\n" : "\n")
            + horarios_de_aula
        );
    }
}
