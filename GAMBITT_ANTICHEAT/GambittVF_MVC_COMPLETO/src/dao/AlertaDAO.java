package dao;

import model.Alerta;
import model.Jogador;
import model.Processo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {
    public void salvar(Alerta alerta) throws SQLException {
        String sql = "INSERT INTO alerta(jogador_id, processo_pid, gravidade) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, alerta.getJogador().getId());
            stmt.setInt(2, alerta.getProcessoSuspeito().getPid());
            stmt.setString(3, alerta.getGravidade());
            stmt.executeUpdate();
        }
    }

    public List<Alerta> listarTodos() throws SQLException {
        String sql = "SELECT a.id_alerta, a.gravidade, a.data_alerta, j.id AS jogador_id, j.nickname, j.banido, " +
                "p.pid, p.nome AS processo_nome, p.acessando_memoria FROM alerta a " +
                "INNER JOIN jogador j ON a.jogador_id = j.id " +
                "INNER JOIN processo p ON a.processo_pid = p.pid ORDER BY a.id_alerta DESC";
        List<Alerta> alertas = new ArrayList<>();
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Jogador jogador = new Jogador(rs.getInt("jogador_id"), rs.getString("nickname"), rs.getBoolean("banido"));
                Processo processo = new Processo(rs.getInt("pid"), rs.getString("processo_nome"), rs.getBoolean("acessando_memoria"));
                Timestamp data = rs.getTimestamp("data_alerta");
                alertas.add(new Alerta(rs.getInt("id_alerta"), jogador, processo, rs.getString("gravidade"), data != null ? data.toLocalDateTime() : null));
            }
        }
        return alertas;
    }
}
