package com.banksecure.infra.DAO;

import com.banksecure.domain.Funcionario;
import com.banksecure.exception.EstruturaBancoException;
import com.banksecure.infra.db.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private Connection con = new ConnectionFactory().getConnection();

        public void inicializaTabelas () {
        this.createTable();
        this.populaRegistros();
    }

        private void populaRegistros () {
        try {
            Funcionario funcionario = new Funcionario(1L, "diflale", "1234");
            Funcionario funcionario2 = new Funcionario(2L, "jejairoy", "4321");

            this.save(funcionario);
            this.save(funcionario2);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        public void createTable () {
        try {
            String sqlDaTabela = """
                    CREATE TABLE IF NOT EXISTS funcionarios(
                    id INT PRIMARY KEY,
                    usuario VARCHAR(50) NOT NULL,
                    senha VARCHAR(50) NOT NULL
                    );
                        """;
            try (Statement stmt = con.createStatement()) {
                stmt.execute(sqlDaTabela);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

        public List<Funcionario> getAll () {
        try {
            try (Statement stmt = con.createStatement();
                 ResultSet result = stmt.executeQuery("SELECT * FROM funcionarios f ORDER BY f.id")) {
                List<Funcionario> funcionarios = new ArrayList<>();
                while (result.next()) {
                    funcionarios.add(new Funcionario(
                            result.getLong("id"),
                            result.getString("usuario"),
                            result.getString("senha")));
                }
                if (funcionarios.isEmpty()) {
                    throw new EstruturaBancoException("Nenhum funcionário encontrado no banco de dados.");
                }
                return funcionarios;
            }

        } catch (Exception e) {
            throw new EstruturaBancoException("Erro ao buscar funcionários no banco de dados.");
        }
    }
        public void save (Funcionario funcionario){
        if (funcionario == null) return;
        try {
            String sql = "MERGE INTO funcionarios (id, usuario, senha) KEY(id) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setLong(1, funcionario.getId());
                pstmt.setString(2, funcionario.getUsuario());
                pstmt.setString(3, funcionario.getSenha());
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        public Funcionario getById (Long funcionarioId){
        try {
            String sql = "SELECT * FROM funcionarios f WHERE f.id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setLong(1, funcionarioId);
                try (ResultSet result = pstmt.executeQuery()) {
                    if (result.next()) {
                        return new Funcionario(
                                result.getLong("id"),
                                result.getString("usuario"),
                                result.getString("senha"));
                    }
                }
            }
            throw new RuntimeException("Funcionário não encontrado no banco de dados." + funcionarioId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionário no banco de dados.");
        }
    }
        public boolean validaLogin (String usuario, String senha){
        String sql = "SELECT * FROM funcionarios f WHERE f.usuario = ? AND f.senha = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, senha);
            try (ResultSet result = pstmt.executeQuery()) {
                return result.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
