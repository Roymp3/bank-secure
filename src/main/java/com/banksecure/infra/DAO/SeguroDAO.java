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

    public void iniciaTabela(){
         this.createTable();
         this.popularRegistros();

    }

    public void popularRegistros(){
        try{
            Seguro seguro1 = new Seguro(null, "Seguro de vida", 
            "Cobertura completa de R$200.000,00 para segurança de vida", 
            new BigDecimal("200000"), 
            new BigDecimal("70")
        );
            Seguro seguro2 = new Seguro(null, "Seguro residencial", 
            "Cobertura de R$300.000,00 para danos a residencia", 
            new BigDecimal("300000"), 
            new BigDecimal("55")
        );
            Seguro seguro3 = new Seguro(null, "Seguro automotivo", 
            "Cobertura de R$20.000,00 para danos ao veiculo", 
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
                    valor_base DECIMAL(15,2) NOT NULL
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM seguro");

            List<Seguro> seguros = new ArrayList<>();
            while (rs.next()) {
                seguros.add(new Seguro(
                    rs.getLong("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getBigDecimal("cobertura"),
                    rs.getBigDecimal("valor_base")
                ));
            
            }

            if (seguros.isEmpty()) {
                throw new BancoVazioException();
            }
            return seguros;
        }catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao buscar registros no banco de dados");
     }
   }
   public void save(Seguro seguro) {
    try {
        if(seguro.getTitulo()==null || seguro.getDescricao()==null ||
        seguro.getCobertura()==null || seguro.getValorBase()==null){
            throw new EstruturaBancoException("Dados do seguro incompletos");
        }
        if(seguro.getTitulo().trim().isEmpty() || seguro.getDescricao().trim().isEmpty() ||
        seguro.getCobertura().compareTo(BigDecimal.ZERO)<=0 || seguro.getValorBase().compareTo(BigDecimal.ZERO)<=0){
            throw new EstruturaBancoException("Dados do seguro invalidos");
         }

         String sqlInsert = "INSERT INTO seguro (titulo, descricao, cobertura, valor_base) VALUES ( ?, ?, ?, ?)";
                var prepared = con.prepareStatement(sqlInsert);
                prepared.setString(1, seguro.getTitulo());
                prepared.setString(2, seguro.getDescricao());
                prepared.setBigDecimal(3, seguro.getCobertura());
                prepared.setBigDecimal(4, seguro.getValorBase());   
                prepared.executeUpdate();
                prepared.close();
    } catch (SQLException e) {
        throw new DadosInvalidosException("Erro ao salvar seguro no banco de dados");

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
                    rs.getBigDecimal("valor_base")
                ));
            }
            return seguros.get(0);
        }catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao buscar seguro por ID no banco de dados");
        }
    }
}