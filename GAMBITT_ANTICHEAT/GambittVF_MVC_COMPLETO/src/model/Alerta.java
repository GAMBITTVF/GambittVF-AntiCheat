package model;

import java.time.LocalDateTime;

public class Alerta {
    private int idAlerta;
    private Jogador jogador;
    private Processo processoSuspeito;
    private String gravidade;
    private LocalDateTime dataAlerta;

    public Alerta(Jogador jogador, Processo processoSuspeito, String gravidade) {
        this.jogador = jogador;
        this.processoSuspeito = processoSuspeito;
        this.gravidade = gravidade;
    }

    public Alerta(int idAlerta, Jogador jogador, Processo processoSuspeito, String gravidade, LocalDateTime dataAlerta) {
        this.idAlerta = idAlerta;
        this.jogador = jogador;
        this.processoSuspeito = processoSuspeito;
        this.gravidade = gravidade;
        this.dataAlerta = dataAlerta;
    }

    public int getIdAlerta() { return idAlerta; }
    public Jogador getJogador() { return jogador; }
    public Processo getProcessoSuspeito() { return processoSuspeito; }
    public String getGravidade() { return gravidade; }
    public LocalDateTime getDataAlerta() { return dataAlerta; }
}
