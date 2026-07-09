package view;

import controller.AnticheatController;
import model.Alerta;
import model.Jogador;
import model.Processo;
import util.MensagemUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.border.TitledBorder;

public class TelaPrincipal extends JFrame {
    private final AnticheatController controller = new AnticheatController();
    private JTextField campoId;
    private JTextField campoNickname;
    private JTextArea areaLog;
    private JTable tabelaJogadores;
    private JTable tabelaProcessos;
    private JTable tabelaAlertas;

    public TelaPrincipal() {
        setTitle("GambittVF Anticheat - MVC + DAO + Swing");
        setSize(950, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(35,35,35));
        criarInterface();
        carregarTabelas();
    }

    private void criarInterface() {
        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Cadastro", criarPainelCadastro());
        abas.addTab("Jogadores", criarPainelJogadores());
        abas.addTab("Processos", criarPainelProcessos());
        abas.addTab("Alertas", criarPainelAlertas());
        add(abas);
    }

    private JPanel criarPainelCadastro() {
        JPanel painel = new JPanel(new BorderLayout(10,10));
        painel.setBackground(new Color(45,45,45));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(new Color(45,45,45));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        TitledBorder borda = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Cadastrar Jogador"
        );
        borda.setTitleColor(Color.WHITE);
        formulario.setBorder(borda);

        campoId = new JTextField();
        campoNickname = new JTextField();

        campoId.setPreferredSize(new Dimension(350, 30));
        campoNickname.setPreferredSize(new Dimension(350, 30));

        JButton botaoCadastrar = new JButton("Cadastrar Jogador");
        botaoCadastrar.setBackground(new Color(46,204,113));
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFocusPainted(false);
        botaoCadastrar.setFont(new Font("Segoe UI", Font.BOLD,14));

        JButton botaoEscanear = new JButton("Escanear Processos");
        botaoEscanear.setBackground(new Color(52,152,219));
        botaoEscanear.setForeground(Color.WHITE);
        botaoEscanear.setFocusPainted(false);
        botaoEscanear.setFont(new Font("Segoe UI", Font.BOLD,14));

        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBackground(new Color(231,76,60));
        botaoCancelar.setForeground(Color.WHITE);
        botaoCancelar.setFocusPainted(false);
        botaoCancelar.setFont(new Font("Segoe UI", Font.BOLD,14));

        JLabel lblId = new JLabel("ID:");
        lblId.setForeground(Color.WHITE);

        JLabel lblNick = new JLabel("Nickname:");
        lblNick.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formulario.add(lblId, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        formulario.add(campoId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formulario.add(lblNick, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        formulario.add(campoNickname, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        formulario.add(botaoCadastrar, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        formulario.add(botaoCancelar, gbc);

        areaLog = new JTextArea();
        areaLog.setBackground(new Color(25,25,25));
        areaLog.setForeground(Color.GREEN);
        areaLog.setCaretColor(Color.WHITE);
        areaLog.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaLog.setEditable(false);

        painel.add(formulario, BorderLayout.NORTH);
        painel.add(new JScrollPane(areaLog), BorderLayout.CENTER);
        painel.add(botaoEscanear, BorderLayout.SOUTH);

        botaoCadastrar.addActionListener(e -> cadastrarJogador());
        botaoEscanear.addActionListener(e -> escanearProcessos());
        botaoCancelar.addActionListener(e -> {
            campoId.setText("");
            campoNickname.setText("");
        });

        return painel;
    }


    private JPanel criarPainelJogadores() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        tabelaJogadores = new JTable();
        tabelaJogadores.getTableHeader().setBackground(new Color(52,152,219));
        tabelaJogadores.getTableHeader().setForeground(Color.WHITE);
        tabelaJogadores.setRowHeight(28);

        JButton botaoAtualizar = new JButton("Atualizar");
        botaoAtualizar.setBackground(new Color(52,152,219));
        botaoAtualizar.setForeground(Color.WHITE);
        botaoAtualizar.setFocusPainted(false);

        JButton botaoBanidos = new JButton("Mostrar Somente Banidos");
        botaoBanidos.setBackground(new Color(243,156,18));
        botaoBanidos.setForeground(Color.WHITE);
        botaoBanidos.setFocusPainted(false);

        JButton botaoDesbanir = new JButton("Desbanir Selecionado");
        botaoDesbanir.setBackground(new Color(231,76,60));
        botaoDesbanir.setForeground(Color.WHITE);
        botaoDesbanir.setFocusPainted(false);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(botaoAtualizar); botoes.add(botaoBanidos); botoes.add(botaoDesbanir);
        painel.add(new JScrollPane(tabelaJogadores), BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);
        botaoAtualizar.addActionListener(e -> carregarJogadores());
        botaoBanidos.addActionListener(e -> carregarBanidos());
        botaoDesbanir.addActionListener(e -> desbanirJogadorSelecionado());
        return painel;
    }

    private JPanel criarPainelProcessos() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        tabelaProcessos = new JTable();
        tabelaJogadores.getTableHeader().setBackground(new Color(52,152,219));
        tabelaJogadores.getTableHeader().setForeground(Color.WHITE);
        tabelaJogadores.setRowHeight(28);
        JButton botaoAtualizar = new JButton("Atualizar Processos");
        botaoAtualizar.setBackground(new Color(52,152,219));
        botaoAtualizar.setForeground(Color.WHITE);
        painel.add(new JScrollPane(tabelaProcessos), BorderLayout.CENTER);
        painel.add(botaoAtualizar, BorderLayout.SOUTH);
        botaoAtualizar.addActionListener(e -> carregarProcessos());
        return painel;
    }

    private JPanel criarPainelAlertas() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        tabelaAlertas = new JTable();
        tabelaJogadores.getTableHeader().setBackground(new Color(52,152,219));
        tabelaJogadores.getTableHeader().setForeground(Color.WHITE);
        tabelaJogadores.setRowHeight(28);
        JButton botaoAtualizar = new JButton("Atualizar Alertas");
        botaoAtualizar.setBackground(new Color(52,152,219));
        botaoAtualizar.setForeground(Color.WHITE);
        painel.add(new JScrollPane(tabelaAlertas), BorderLayout.CENTER);
        painel.add(botaoAtualizar, BorderLayout.SOUTH);
        botaoAtualizar.addActionListener(e -> carregarAlertas());
        return painel;
    }

    private void cadastrarJogador() {
        try {
            int id = Integer.parseInt(campoId.getText().trim());
            controller.cadastrarJogador(id, campoNickname.getText());
            campoId.setText(""); campoNickname.setText("");
            areaLog.setText("Jogador cadastrado com sucesso.");
            carregarJogadores();
            MensagemUtil.sucesso(this, "Jogador cadastrado com sucesso.");
        } catch (NumberFormatException e) { MensagemUtil.erro(this, "O ID deve ser um número inteiro.");
        } catch (IllegalArgumentException e) { MensagemUtil.erro(this, e.getMessage());
        } catch (SQLException e) { MensagemUtil.erro(this, "Erro ao cadastrar jogador: " + e.getMessage()); }
    }

    private void escanearProcessos() {
        try { areaLog.setText(controller.escanearProcessosSimulados()); carregarTabelas();
        } catch (SQLException e) { MensagemUtil.erro(this, "Erro ao escanear processos: " + e.getMessage()); }
    }

    private void carregarTabelas() { carregarJogadores(); carregarProcessos(); carregarAlertas(); }
    private void carregarJogadores() { try { preencherTabelaJogadores(controller.listarJogadores()); } catch (SQLException e) { MensagemUtil.erro(this, "Erro ao carregar jogadores: " + e.getMessage()); } }
    private void carregarBanidos() { try { preencherTabelaJogadores(controller.listarBanidos()); } catch (SQLException e) { MensagemUtil.erro(this, "Erro ao carregar banidos: " + e.getMessage()); } }

    private void preencherTabelaJogadores(List<Jogador> jogadores) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID"); modelo.addColumn("Nickname"); modelo.addColumn("Status");
        for (Jogador j : jogadores) modelo.addRow(new Object[]{j.getId(), j.getNickname(), j.isBanido() ? "BANIDO" : "ATIVO"});
        tabelaJogadores.setModel(modelo);
    }

    private void carregarProcessos() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("PID"); modelo.addColumn("Nome"); modelo.addColumn("Acessando Memória");
            for (Processo p : controller.listarProcessos()) modelo.addRow(new Object[]{p.getPid(), p.getNome(), p.isAcessandoMemoriaDoJogo() ? "SIM" : "NÃO"});
            tabelaProcessos.setModel(modelo);
        } catch (SQLException e) { MensagemUtil.erro(this, "Erro ao carregar processos: " + e.getMessage()); }
    }

    private void carregarAlertas() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID"); modelo.addColumn("Jogador"); modelo.addColumn("Processo"); modelo.addColumn("Gravidade"); modelo.addColumn("Data");
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            for (Alerta a : controller.listarAlertas()) modelo.addRow(new Object[]{a.getIdAlerta(), a.getJogador().getNickname(), a.getProcessoSuspeito().getNome(), a.getGravidade(), a.getDataAlerta()!=null ? a.getDataAlerta().format(f) : ""});
            tabelaAlertas.setModel(modelo);
        } catch (SQLException e) { MensagemUtil.erro(this, "Erro ao carregar alertas: " + e.getMessage()); }
    }

    private void desbanirJogadorSelecionado() {
        int linha = tabelaJogadores.getSelectedRow();
        if (linha == -1) { MensagemUtil.erro(this, "Selecione um jogador na tabela."); return; }
        int id = Integer.parseInt(tabelaJogadores.getValueAt(linha, 0).toString());
        try { controller.atualizarStatusJogador(id, false); carregarJogadores(); MensagemUtil.sucesso(this, "Jogador desbanido com sucesso.");
        } catch (SQLException e) { MensagemUtil.erro(this, "Erro ao desbanir jogador: " + e.getMessage()); }
    }
}
