package controller;

import dao.AlertaDAO;
import dao.JogadorDAO;
import dao.ProcessoDAO;
import model.Alerta;
import model.Jogador;
import model.Processo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnticheatController {
    private final JogadorDAO jogadorDAO = new JogadorDAO();
    private final ProcessoDAO processoDAO = new ProcessoDAO();
    private final AlertaDAO alertaDAO = new AlertaDAO();

    public void cadastrarJogador(int id, String nickname) throws SQLException {
        if (nickname == null || nickname.trim().isEmpty()) throw new IllegalArgumentException("O nickname não pode estar vazio.");
        jogadorDAO.salvar(new Jogador(id, nickname.trim()));
    }

    public String escanearProcessosSimulados() throws SQLException {
        StringBuilder log = new StringBuilder();
        List<Jogador> jogadores = jogadorDAO.listarTodos();
        if (jogadores.isEmpty()) return "Nenhum jogador cadastrado. Cadastre um jogador antes de escanear.";
        List<Processo> processos = criarProcessosSimulados();
        log.append("Iniciando varredura de processos... ");
        for (Processo processo : processos) {
            processoDAO.salvar(processo);
            log.append("Verificado: ").append(processo.getNome()).append(" ");
            if (processo.isAcessandoMemoriaDoJogo()) {
                log.append(" AMEAÇA DETECTADA: ").append(processo.getNome()).append("");
                for (Jogador jogador : jogadores) {
                    jogador.setBanido(true);
                    jogadorDAO.atualizarStatus(jogador.getId(), true);
                    alertaDAO.salvar(new Alerta(jogador, processo, "CRÍTICA"));
                    log.append("Jogador banido: ").append(jogador.getNickname()).append(" (ID: ").append(jogador.getId()).append(") ");
                }
            }
        }
        log.append(" Varredura finalizada.");
        return log.toString();
    }

    private List<Processo> criarProcessosSimulados() {
        List<Processo> processos = new ArrayList<>();
        processos.add(new Processo(1024, "discord.exe"));
        processos.add(new Processo(3048, "chrome.exe"));
        Processo cheat = new Processo(9999, "wallhack_injector.exe");
        cheat.setAcessandoMemoriaDoJogo(true);
        processos.add(cheat);
        return processos;
    }

    public void atualizarStatusJogador(int id, boolean banido) throws SQLException { jogadorDAO.atualizarStatus(id, banido); }
    public List<Jogador> listarJogadores() throws SQLException { return jogadorDAO.listarTodos(); }
    public List<Jogador> listarBanidos() throws SQLException { return jogadorDAO.listarBanidos(); }
    public List<Processo> listarProcessos() throws SQLException { return processoDAO.listarTodos(); }
    public List<Alerta> listarAlertas() throws SQLException { return alertaDAO.listarTodos(); }
}
