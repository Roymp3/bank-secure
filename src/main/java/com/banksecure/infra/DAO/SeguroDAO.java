package com.banksecure.infra.DAO;

import com.banksecure.domain.Seguro;
import com.banksecure.exception.BancoVazioException;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.exception.EstruturaBancoException;
import com.banksecure.infra.db.ConnectionFactory;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeguroDAO {

    private Connection con = new ConnectionFactory().getConnection();

    public void iniciaTabelas(){
         this.createTable();
         this.popularRegistros();

    }

    public void popularRegistros(){
        try{
            Seguro seguro1 = new Seguro(1L,
              "Seguro de vida",
              "Cobertura completa de R$200.000,00 para segurança de vida",
              new BigDecimal("200000"),
              new BigDecimal("70")
        );
            Seguro seguro2 = new Seguro(2L,
              "Seguro residencial",
              "Cobertura de R$300.000,00 para danos à residência: casa, Av dos Estados, 678 (cep: 09092-300)",
              new BigDecimal("300000"),
              new BigDecimal("55")
        );
            Seguro seguro3 = new Seguro(3L,
              "Seguro de automovél",
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
     public void createTable(){
        try {
            String sqlTabelaSeguro = """
                    CREATE TABLE IF NOT EXISTS seguro (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    titulo VARCHAR(100) NOT NULL,
                    descricao VARCHAR(300) NOT NULL,
                    cobertura DECIMAL(15,2) NOT NULL,
                    valorBase DECIMAL(10,2) NOT NULL
                    );
                    """;

            Statement stmt = con.createStatement();
            stmt.execute(sqlTabelaSeguro);
        } catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao criar tabela de seguros");
        }
     }

     public List<Seguro> getAll(){
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM seguro");

            List<Seguro> seguros = new ArrayList<>();
            while (result.next()) {
                seguros.add(new Seguro(
                    result.getLong("id"),
                    result.getString("titulo"),
                    result.getString("descricao"),
                    result.getBigDecimal("cobertura"),
                    result.getBigDecimal("valorBase")
                ));
            
            }

            if (seguros.isEmpty()) {
                throw new BancoVazioException();
            }
            return seguros;
        }catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao buscar registros na tabela de seguros");
     }
   }

   public void save(Seguro seguro) {
    try {
        if(seguro.getTitulo()==null || seguro.getDescricao()==null ||
        seguro.getCobertura()==null || seguro.getValorBase()==null){
            throw new EstruturaBancoException("Registros fornecidos não coincidem com a tabela Seguro");
        }
        if(seguro.getTitulo().trim().isEmpty() || seguro.getDescricao().trim().isEmpty() ||
        seguro.getCobertura().compareTo(BigDecimal.ZERO)<=0 || seguro.getValorBase().compareTo(BigDecimal.ZERO)<=0){
            throw new DadosInvalidosException("Dados invalidos: campos não podem ser vazios ou negativos.");
         }

         String sqlInsert = "INSERT INTO seguro (titulo, descricao, cobertura, valorBase) VALUES ( ?, ?, ?, ?)";
                var prepared = con.prepareStatement(sqlInsert);
                prepared.setString(1, seguro.getTitulo());
                prepared.setString(2, seguro.getDescricao());
                prepared.setBigDecimal(3, seguro.getCobertura());
                prepared.setBigDecimal(4, seguro.getValorBase());   
                prepared.executeUpdate();
                prepared.close();
    } catch (SQLException e) {
        throw new DadosInvalidosException("Dados invalidos: campos não podem ser vazios ou negativos.");
       }
    }

    public Seguro getById(Long id){
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM seguro WHERE id = " + id);

            List<Seguro> seguros = new ArrayList<>();
            while (rs.next()) {
                seguros.add(new Seguro(
                    rs.getLong("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getBigDecimal("cobertura"),
                    rs.getBigDecimal("valorBase")
                ));
            }
            if (seguros.isEmpty()) {
                throw new BancoVazioException();
            }
            return seguros.get(0);
        }catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao buscar seguro por ID no banco de dados");
        }
    }
}