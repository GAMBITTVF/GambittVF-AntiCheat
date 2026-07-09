package dao;

import model.Jogador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAO {
    public void salvar(Jogador jogador) throws SQLException {
        String sql = "INSERT INTO jogador(id, nickname, banido) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE nickname = VALUES(nickname), banido = VALUES(banido)";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jogador.getId());
            stmt.setString(2, jogador.getNickname());
            stmt.setBoolean(3, jogador.isBanido());
            stmt.executeUpdate();
        }
    }

    public void atualizarStatus(int id, boolean banido) throws SQLException {
        String sql = "UPDATE jogador SET banido = ? WHERE id = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, banido);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public List<Jogador> listarTodos() throws SQLException {
        String sql = "SELECT id, nickname, banido FROM jogador ORDER BY id";
        List<Jogador> jogadores = new ArrayList<>();
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) jogadores.add(new Jogador(rs.getInt("id"), rs.getString("nickname"), rs.getBoolean("banido")));
        }
        return jogadores;
    }

    public List<Jogador> listarBanidos() throws SQLException {
        String sql = "SELECT id, nickname, banido FROM jogador WHERE banido = true ORDER BY id";
        List<Jogador> jogadores = new ArrayList<>();
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) jogadores.add(new Jogador(rs.getInt("id"), rs.getString("nickname"), rs.getBoolean("banido")));
        }
        return jogadores;
    }
}
