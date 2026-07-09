package model;

public class Processo {
    private int pid;
    private String nome;
    private boolean acessandoMemoriaDoJogo;

    public Processo(int pid, String nome) {
        this.pid = pid;
        this.nome = nome;
        this.acessandoMemoriaDoJogo = false;
    }

    public Processo(int pid, String nome, boolean acessandoMemoriaDoJogo) {
        this.pid = pid;
        this.nome = nome;
        this.acessandoMemoriaDoJogo = acessandoMemoriaDoJogo;
    }

    public int getPid() { return pid; }
    public String getNome() { return nome; }
    public boolean isAcessandoMemoriaDoJogo() { return acessandoMemoriaDoJogo; }
    public void setAcessandoMemoriaDoJogo(boolean acessandoMemoriaDoJogo) { this.acessandoMemoriaDoJogo = acessandoMemoriaDoJogo; }
}
