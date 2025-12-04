package com.banksecure.infra.DAO;

import com.banksecure.domain.Cliente;
import com.banksecure.domain.Seguro;
import com.banksecure.exception.DadosInvalidosException;
import com.banksecure.exception.EstruturaBancoException;
import com.banksecure.infra.db.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection con = new ConnectionFactory().getConnection();

    public void iniciaTabela(){
        this.createTable();
        this.popularRegistros();

    }

    public void popularRegistros(){
        try{
            Cliente cliente1 = new Cliente("Flavão", "80758585472", LocalDate.of(2004, 9, 15));

            Cliente cliente2 = new Cliente("Eduardo", "58142587954", LocalDate.of(2000, 1, 25));

            this.save(cliente1);
            this.save(cliente2);

        } catch (Exception e) {
            throw new EstruturaBancoException("Erro ao popular tabela de clientes");
        }
    }

    public void createTable(){
        try{
            String sql = """
                    CREATE TABLE IF NOT EXISTS clientes(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        cpf VARCHAR(11) NOT NULL,
                        data_nascimento DATE NOT NULL
                    );
                    """;
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        }catch(Exception e){
            throw new EstruturaBancoException("Erro ao criar tabela de clientes");
        }
    }

    public void save(Cliente cliente) {

        try {
            if(cliente.getNome() == null || cliente.getNome().trim().isEmpty()
                    || cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()
                    || cliente.getDataNascimento() == null) {
                throw new DadosInvalidosException("Dados invalidos: os campos do Cliente não podem ser vazios ou negativos.");
            }
            if(cliente.getDataNascimento().isAfter(LocalDate.now())) {
                throw new DadosInvalidosException("Data de nascimento inválida: não pode ser no futuro.");
            }
            if(cliente.getCpf().trim().isEmpty() || cliente.getCpf().length() != 11 || !cliente.getCpf().matches("\\d{11}")) {
                throw new DadosInvalidosException("CPF inválido: O cpf deve ter 11 digitos.");
            }

            String sql = "INSERT INTO clientes (nome, cpf, data_nascimento)" +
                    " VALUES ( ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, java.sql.Date.valueOf(cliente.getDataNascimento()));

            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            throw new DadosInvalidosException("Erro ao salvar Cliente no banco de dados");
        }
    }

    public List<Cliente> getAll() {
        try {
            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM clientes c order by c.id");

            List<Cliente> clientes = new ArrayList<>();
            while (result.next()) {
                clientes.add(new Cliente(
                        result.getLong("id"),
                        result.getString("nome"),
                        result.getString("cpf"),
                        result.getDate("data_nascimento").toLocalDate()));
            }
            if (clientes.isEmpty()) {
                throw new EstruturaBancoException("Nenhum cliente encontrado");
            }
            return clientes;
        } catch (Exception e) {
            throw new EstruturaBancoException("Erro ao listar clientes no banco de dados");
        }
    }

    public Cliente getById(int clienteId) {
            try {
                Statement stmt = con.createStatement();
                ResultSet result = stmt.executeQuery("SELECT * FROM clientes c where c.id = "+ clienteId);

                List<Cliente> clientes = new ArrayList<>();
                while (result.next()) {
                    clientes.add(new Cliente(
                            result.getLong("id"),
                            result.getString("nome"),
                            result.getString("cpf"),
                            result.getDate("data_nascimento").toLocalDate()));
                }
                if (clientes.isEmpty()) {
                    throw new EstruturaBancoException("Nenhum cliente encontrado para o id informado");
                }
                return clientes.get(0);
            } catch (Exception e) {
                throw new EstruturaBancoException("Erro ao listar clientes no banco de dados");
            }
    }

}
