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

    public ApoliceDAO() {
        this.apoliceService = new ApoliceService();
        this.createTable();
    }

    public void popularRegistro() {
        try {

            Apolice apolice = new Apolice(1L,1L,1L, new BigDecimal("200000"), LocalDate.now(), LocalDate.now().plusYears(1),false);
            Apolice apolice2 = new Apolice(2L,2L,2L, new BigDecimal("15000"), LocalDate.now(), LocalDate.now().plusYears(1),false);
            Apolice apolice3 = new Apolice(2L,3L,2L, new BigDecimal("17000"), LocalDate.now(), LocalDate.now().plusYears(1),false);
            Apolice apolice4 = new Apolice(1L,1L,2L, new BigDecimal("17000"), LocalDate.now(), LocalDate.now().plusYears(1),false);
            Apolice apolice5 = new Apolice(1L,1L,2L, new BigDecimal("17000"), LocalDate.now(), LocalDate.now().plusYears(1),false);
            Apolice apolice6 = new Apolice(2L,3L,1L, new BigDecimal("17000"), LocalDate.now(), LocalDate.now().plusYears(1),false);
            Apolice apolice7 = new Apolice(1L,1L,1L,new BigDecimal("17000"), LocalDate.now(), LocalDate.now().plusDays(30),false);

            this.save(apolice);
            this.save(apolice2);
            this.save(apolice3);
            this.save(apolice4);
            this.save(apolice5);
            this.save(apolice6);
            this.save(apolice7);

        } catch (Exception e) {
            throw new EstruturaBancoException("Erro ao popular tabela apolice");
        }
    }

    public void createTable() {
            String sqlTabela = """
                    CREATE TABLE IF NOT EXISTS apolice(
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    cliente_id INT NOT NULL,
                    seguro_id INT NOT NULL,
                    funcionario_id INT NOT NULL,
                    valorFinal DECIMAL(10,2) NOT NULL,
                    dataInicio DATE NOT NULL,
                    dataFim DATE NOT NULL,
                    renovada BOOLEAN NOT NULL,
                    CONSTRAINT fk_idCliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
                    CONSTRAINT fk_idSeguro FOREIGN KEY (seguro_id)  REFERENCES seguro(id),
                    CONSTRAINT fk_idFuncionario FOREIGN KEY (funcionario_id)  REFERENCES funcionarios(id)
                    );
                    """;

        try(Connection con = new ConnectionFactory().getConnection();
            Statement smtm = con.createStatement()){
            smtm.execute(sqlTabela);
        }catch (SQLException e){
            throw new EstruturaBancoException("Erro ao criar tabela apolice");
        }
    }

    public void save(Apolice apolice) {

        apoliceService.validarApoliceDAO(apolice);

        String sqlInsert = "INSERT INTO apolice (cliente_id, seguro_id, funcionario_id, valorFinal, dataInicio, dataFim, renovada) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(Connection con = new ConnectionFactory().getConnection();
            PreparedStatement prepared = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)){

            prepared.setLong(1, apolice.getCliente_id());
            prepared.setLong(2, apolice.getSeguro_id());
            prepared.setLong(3, apolice.getFuncionario_id());
            prepared.setBigDecimal(4, apolice.getValorFinal());
            prepared.setDate(5, Date.valueOf(apolice.getDataInicio()));
            prepared.setDate(6, Date.valueOf(apolice.getDataFim()));
            prepared.setBoolean(7,apolice.isRenovada());
            prepared.execute();
            try (ResultSet rs = prepared.getGeneratedKeys()) {
                if (rs.next()) {
                    apolice.setId(rs.getLong(1));
                }
            }

        }catch (SQLException e){
            throw new DadosInvalidosException("Erro ao salvar apolice no banco de dados");
        }
    }

    public List<Apolice> getAll(){

        String sqlSelect = "SELECT * FROM apolice";

        try (Connection con = new ConnectionFactory().getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sqlSelect)){
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
                                rs.getDate("dataFim").toLocalDate(),
                                rs.getBoolean("renovada")));
            }

            return apolices;

        }catch (SQLException e){
            throw new EstruturaBancoException("Erro ao buscar apólices no banco de dados");
        }
    }

    public void renovarApolice(Apolice apoliceExistente, BigDecimal novoValorFinal, LocalDate novaDataFim){
        Apolice novaApolice = new Apolice(
                apoliceExistente.getCliente_id(),
                apoliceExistente.getSeguro_id(),
                apoliceExistente.getFuncionario_id(),
                novoValorFinal,
                LocalDate.now(),
                novaDataFim,
                false
        );

        this.save(novaApolice);
        update(apoliceExistente.getId());
    }

    public List<Apolice> getByDueDate(){
        String sql = "SELECT * FROM apolice WHERE dataFim = ? AND renovada = FALSE";
        LocalDate dataVencimento = LocalDate.now().plusDays(30);

        try(
            Connection con = new ConnectionFactory().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setDate(1,Date.valueOf(dataVencimento));

            try(ResultSet rs = stmt.executeQuery()) {
                List<Apolice> apolices = new ArrayList<>();
                while (rs.next()) {
                    apolices.add(new Apolice(
                            rs.getLong("id"),
                            rs.getLong("cliente_id"),
                            rs.getLong("seguro_id"),
                            rs.getLong("funcionario_id"),
                            rs.getBigDecimal("valorFinal"),
                            rs.getDate("dataInicio").toLocalDate(),
                            rs.getDate("dataFim").toLocalDate(),
                            rs.getBoolean("renovada")
                    ));
                }
                return apolices;
            }

        } catch (SQLException e) {
            throw new EstruturaBancoException("Erro ao buscar apolices com vencimento em 30 dias no banco de dados");
        }
    }

    public Apolice getById(Long id){
        String sqlSelect = "SELECT * FROM apolice WHERE id = ?";

        try (Connection con = new ConnectionFactory().getConnection();
        PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Apolice(
                            rs.getLong("id"),
                            rs.getLong("cliente_id"),
                            rs.getLong("seguro_id"),
                            rs.getLong("funcionario_id"),
                            rs.getBigDecimal("valorFinal"),
                            rs.getDate("dataInicio").toLocalDate(),
                            rs.getDate("dataFim").toLocalDate(),
                            rs.getBoolean("renovada")
                    );
                }else {
                    throw new EstruturaBancoException("Apolice não encontrada: id=" + id);
                }
            }

        }catch (SQLException e){
            throw new EstruturaBancoException("Erro ao buscar apolices no banco de dados");
        }
    }

    private void update(Long id){
        String sqlUpdate = "UPDATE apolice SET renovada = TRUE WHERE id = ?";
        try (Connection con = new ConnectionFactory().getConnection();
        PreparedStatement stmt = con.prepareStatement(sqlUpdate)){

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            throw new DadosInvalidosException("Erro ao atualizar apolices no banco de dados");
        }
    }
}
