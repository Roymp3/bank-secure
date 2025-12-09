package com.banksecure.infra.DAO;

import com.banksecure.domain.Seguro;
import com.banksecure.enums.TipoDeSeguroEnum;
import com.banksecure.exception.BancoVazioException;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.exception.EstruturaBancoException;
import com.banksecure.infra.db.ConnectionFactory;
import com.banksecure.service.SeguroService;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeguroDAO {

    private SeguroService seguroService = new SeguroService();

    public void iniciaTabelas(){
         this.createTable();
         this.popularRegistros();

    }

    public void popularRegistros(){
        try{
            Seguro seguro1 = new Seguro(
                    TipoDeSeguroEnum.SEGURO_VIDA,
              "Cobertura completa de R$200.000,00 para segurança de vida",
              new BigDecimal("200000"),
              new BigDecimal("70")
        );
            Seguro seguro2 = new Seguro(
                    TipoDeSeguroEnum.SEGURO_RESIDENCIAL,
              "Cobertura de R$300.000,00 para danos à residência: casa, Av dos Estados, 678 (cep: 09092-300)",
              new BigDecimal("300000"),
              new BigDecimal("55")
        );
            Seguro seguro3 = new Seguro(
                    TipoDeSeguroEnum.SEGURO_AUTO,
              "Cobertura de R$20.000,00 para danos ao veículo: Kwid, ABC-1234",
              new BigDecimal("20000"),
              new BigDecimal("100")
        );

        this.save(seguro1);
        this.save(seguro2);
        this.save(seguro3);

        } catch (Exception e) {
            throw new EstruturaBancoException("Erro ao popular tabela de seguros");
    }
}
    public void createTable() {

        String sqlTabelaSeguro = """
            CREATE TABLE IF NOT EXISTS seguro (
            id INT AUTO_INCREMENT PRIMARY KEY,
            tipo VARCHAR(100) NOT NULL,
            descricao VARCHAR(300) NOT NULL,
            cobertura DECIMAL(15,2) NOT NULL,
            valorBase DECIMAL(10,2) NOT NULL
            );
            """;

        try (Connection con = new ConnectionFactory().getConnection();
             Statement stmt = con.createStatement()) {

            stmt.execute(sqlTabelaSeguro);

        } catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao criar tabela de seguros");
        }
    }

    public List<Seguro> getAll() {
        String sql = "SELECT * FROM seguro";

        try (Connection con = new ConnectionFactory().getConnection();
             Statement stmt = con.createStatement();
             ResultSet result = stmt.executeQuery(sql)) {

            List<Seguro> seguros = new ArrayList<>();
            while (result.next()) {
                seguros.add(new Seguro(
                        result.getLong("id"),
                        TipoDeSeguroEnum.valueOf(result.getString("tipo")),
                        result.getString("descricao"),
                        result.getBigDecimal("cobertura"),
                        result.getBigDecimal("valorBase")
                ));
            }
            if (seguros.isEmpty()) {
                throw new BancoVazioException();
            }

            return seguros;
        } catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao buscar registros na tabela de seguros");
        }
    }

    public Seguro save(Seguro seguro) {

        seguroService.validarSeguroDAO(seguro);

        String sqlInsert = "INSERT INTO seguro (tipo, descricao, cobertura, valorBase) VALUES (?, ?, ?, ?)";

        try (Connection con = new ConnectionFactory().getConnection();
             PreparedStatement prepared = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

            prepared.setString(1, seguro.getTipo().name());
            prepared.setString(2, seguro.getDescricao());
            prepared.setBigDecimal(3, seguro.getCobertura());
            prepared.setBigDecimal(4, seguro.getValorBase());
            prepared.executeUpdate();

            try (ResultSet rs = prepared.getGeneratedKeys()) {
                if (rs.next()) {
                    seguro.setId(rs.getLong(1));
                }
            }

        } catch (SQLException e) {
            throw new DadosInvalidosException("Dados invalidos: campos não podem ser vazios ou negativos.");
        }
        return seguro;
    }

    public Seguro getById(Long id) {

        try (Connection con = new ConnectionFactory().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM seguro WHERE id = " + id)) {

            List<Seguro> seguros = new ArrayList<>();

            while (rs.next()) {
                seguros.add(new Seguro(
                        rs.getLong("id"),
                        TipoDeSeguroEnum.valueOf(rs.getString("tipo")),
                        rs.getString("descricao"),
                        rs.getBigDecimal("cobertura"),
                        rs.getBigDecimal("valorBase")
                ));
            }
            if (seguros.isEmpty()) {
                throw new BancoVazioException();
            }
            return seguros.get(0);

        } catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao buscar seguro por ID no banco de dados");
        }
    }

    public void delete(Seguro seguro) {

        seguroService.validarDeleteSeguro(seguro);

        String sql = "DELETE FROM seguro WHERE id = ? AND tipo = ? AND valorBase = ?";

        try (Connection con = new ConnectionFactory().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, seguro.getId());
            stmt.setString(2, seguro.getTipo().name());
            stmt.setBigDecimal(3, seguro.getValorBase());

            int afetados = stmt.executeUpdate();

            if (afetados == 0) {
                throw new DadosInvalidosException("Seguro não encontrado para exclusão.");
            }

            System.out.println("Seguro removido com sucesso.");

        } catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao deletar seguro no banco de dados");
        }
    }


}