package dao;

import model.Processo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessoDAO {
    public void salvar(Processo processo) throws SQLException {
        String sql = "INSERT INTO processo(pid, nome, acessando_memoria) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE nome = VALUES(nome), acessando_memoria = VALUES(acessando_memoria)";
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, processo.getPid());
            stmt.setString(2, processo.getNome());
            stmt.setBoolean(3, processo.isAcessandoMemoriaDoJogo());
            stmt.executeUpdate();
        }
    }

    public List<Processo> listarTodos() throws SQLException {
        String sql = "SELECT pid, nome, acessando_memoria FROM processo ORDER BY pid";
        List<Processo> processos = new ArrayList<>();
        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) processos.add(new Processo(rs.getInt("pid"), rs.getString("nome"), rs.getBoolean("acessando_memoria")));
        }
        return processos;
    }
}
