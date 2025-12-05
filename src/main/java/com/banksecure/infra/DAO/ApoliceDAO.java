package com.banksecure.infra.DAO;

import com.banksecure.domain.Apolice;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.exception.EstruturaBancoException;
import com.banksecure.infra.db.ConnectionFactory;
import com.banksecure.service.ApoliceService;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApoliceDAO {

    private ApoliceService apoliceService;
    private Connection con = new ConnectionFactory().getConnection();

    public ApoliceDAO() {
        this.apoliceService = new ApoliceService();
        this.createTable();
    }

    public void createTable() {
        try{
            String sqlTabela = """
                    CREATE TABLE IF NOT EXISTS apolice(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    cliente_id INT NOT NULL,
                    seguro_id INT NOT NULL,
                    funcionario_id INT NOT NULL,
                    valorFinal DECIMAL(10,2) NOT NULL,
                    dataInicio DATE NOT NULL,
                    dataFim DATE NOT NULL,
                    CONSTRAINT fk_idCliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
                    CONSTRAINT fk_idSeguro FOREIGN KEY (seguro_id)  REFERENCES seguro(id),
                    CONSTRAINT fk_idFuncionario FOREIGN KEY (funcionario_id)  REFERENCES funcionarios(id)
                    );
                    """;

            Statement smtm = con.createStatement();
            smtm.execute(sqlTabela);
        }catch (SQLException e){
            throw new EstruturaBancoException("Erro ao criar tabela apolice");
        }
    }

    public void popularRegistro() {
        try {

            Apolice apolice = new Apolice(1L,1L,1L, new BigDecimal("200000"), LocalDate.now(), LocalDate.now().plusYears(1));
            Apolice apolice2 = new Apolice(2L,2L,2L, new BigDecimal("15000"), LocalDate.now(), LocalDate.now().plusYears(1));

            this.save(apolice);
            this.save(apolice2);

        } catch (Exception e) {
            throw new EstruturaBancoException("Erro ao popular tabela apolice");
        }
    }

    public void save(Apolice apolice) {

        apoliceService.validarApoliceDAO(apolice);

        try{
            String sqlInsert = "INSERT INTO apolice (cliente_id, seguro_id, funcionario_id, valorFinal, dataInicio, dataFim) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement prepared = con.prepareStatement(sqlInsert);
            prepared.setLong(1, apolice.getCliente_id());
            prepared.setLong(2, apolice.getSeguro_id());
            prepared.setLong(3, apolice.getFuncionario_id());
            prepared.setBigDecimal(4, apolice.getValorFinal());
            prepared.setDate(5, Date.valueOf(apolice.getDataInicio()));
            prepared.setDate(6, Date.valueOf(apolice.getDataFim()));
            prepared.execute();

        }catch (SQLException e){
            throw new DadosInvalidosException("Erro ao salvar apolice no banco de dados");
        }
    }

    public List<Apolice> getAll(){
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM apolice");
            List<Apolice> apolices = new ArrayList<>();

            while (rs.next()) {
                apolices.add(
                        new Apolice(
                                rs.getLong("id"),
                                rs.getLong("cliente_id"),
                                rs.getLong("seguro_id"),
                                rs.getLong("funcionario_id"),
                                rs.getBigDecimal("valorFinal"),
                                rs.getDate("dataInicio").toLocalDate(),
                                rs.getDate("dataFim").toLocalDate()));
            }

            return apolices;

        }catch (SQLException e){
            throw new EstruturaBancoException("Erro ao buscar apólices no banco de dados");
        }
    }
}
