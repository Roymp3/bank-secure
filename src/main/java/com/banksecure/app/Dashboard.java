package com.banksecure.app;

import com.banksecure.domain.Apolice;
import com.banksecure.infra.DAO.ApoliceDAO;
import com.banksecure.infra.DAO.ClienteDAO;
import com.banksecure.infra.DAO.FuncionarioDAO;
import com.banksecure.infra.DAO.SeguroDAO;

import java.math.BigDecimal;
import java.util.List;

public class Dashboard {

    private int qtdApolicesVida;
    private int qtdApolicesAuto;
    private int qtdApolicesResid;
    private int qtdFuncionario1;
    private int qtdFuncionario2;
    private BigDecimal valorTotalVida;
    private BigDecimal valorTotalAuto;
    private BigDecimal valorTotalResid;

    public int getQtdApolicesVida() {return qtdApolicesVida;}
    public int getQtdApolicesAuto() {return qtdApolicesAuto;}
    public int getQtdApolicesResid() {return qtdApolicesResid;}
    public BigDecimal getValorTotalVida() {return valorTotalVida;}
    public BigDecimal getValorTotalAuto() {return valorTotalAuto;}
    public BigDecimal getValorTotalResid() {return valorTotalResid;}
    public int getQtdFuncionario1() {return qtdFuncionario1;}
    public int getQtdFuncionario2() {return qtdFuncionario2;}

    public void inicializarBanco() {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.iniciaTabela();

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionarioDAO.inicializaTabelas();

        SeguroDAO seguroDAO = new SeguroDAO();
        seguroDAO.iniciaTabelas();

        ApoliceDAO apoliceDAO = new ApoliceDAO();
        if (apoliceDAO.getAll().isEmpty()) {
            apoliceDAO.popularRegistro();
        }
    }

    public void exibirDashSeguros() {
        qtdApolicesVida = 0;
        qtdApolicesAuto = 0;
        qtdApolicesResid = 0;
        valorTotalVida = BigDecimal.ZERO;
        valorTotalResid = BigDecimal.ZERO;
        valorTotalAuto = BigDecimal.ZERO;

        inicializarBanco();

        ApoliceDAO apoliceDAO = new ApoliceDAO();
        List<Apolice> apolicesVendidas = apoliceDAO.getAll();


        for (Apolice apolice : apolicesVendidas) {
            switch (apolice.getSeguro_id().intValue()) {
                case 1 -> { qtdApolicesVida++; valorTotalVida = valorTotalVida.add(apolice.getValorFinal()); }
                case 2 -> { qtdApolicesResid++; valorTotalResid = valorTotalResid.add(apolice.getValorFinal()); }
                case 3 -> { qtdApolicesAuto++; valorTotalAuto = valorTotalAuto.add(apolice.getValorFinal()); }
            }
        }

        System.out.println("\n======================== Vendas por Seguro =========================");
        System.out.printf("Tipo: Vida        | Apolices: %d | Total Arrecadado: R$ %.2f%n", qtdApolicesVida, valorTotalVida);
        System.out.printf("Tipo: Residencial | Apolices: %d | Total Arrecadado: R$ %.2f%n", qtdApolicesResid, valorTotalResid);
        System.out.printf("Tipo: Automovel   | Apolices: %d | Total Arrecadado: R$ %.2f%n", qtdApolicesAuto, valorTotalAuto);
    }

    public void exibirDashFuncionarios(){
        qtdFuncionario1 = 0;
        qtdFuncionario2 = 0;
        BigDecimal totalFuncionario1 = BigDecimal.ZERO;
        BigDecimal totalFuncionario2 = BigDecimal.ZERO;

        inicializarBanco();

        ApoliceDAO apoliceDAO = new ApoliceDAO();
        List<Apolice> apolicesVendidas = apoliceDAO.getAll();

        for (Apolice apolice : apolicesVendidas) {
            switch (apolice.getFuncionario_id().intValue()) {
                case 1 -> {qtdFuncionario1++;totalFuncionario1 = totalFuncionario1.add(apolice.getValorFinal());}
                case 2 -> {qtdFuncionario2++;totalFuncionario2 = totalFuncionario2.add(apolice.getValorFinal());}
            }
        }

        System.out.println("\n====================== Vendas por Funcionario ======================");
        System.out.printf("Func(diflale)  | Apolices: %d | Total Arrecadado: R$ %.2f%n", qtdFuncionario1, totalFuncionario1);
        System.out.printf("Func(jejairoy) | Apolices: %d | Total Arrecadado: R$ %.2f%n", qtdFuncionario2, totalFuncionario2);
    }

    public void exibeGraficoDeBarrasSeguros() {
        int[] valores = {qtdApolicesVida, qtdApolicesResid, qtdApolicesAuto};
        String[] textos = {"Vid", "Res",  "Aut"};

        System.out.println("\n=================== Grafico de Barras - Seguros ====================");
        for (int i = 0; i < valores.length; i++) {
            System.out.print(textos[i] + " | ");
            for (int j = 0; j < valores[i]; j++) {
                System.out.print("**");
            }
            System.out.println(" (" + valores[i] + ")");
        }
    }

    public void exibeGraficoDeBarrasFuncionario() {
        int[] valores = {qtdFuncionario1, qtdFuncionario2};
        String[] nomes = {"Func(diflale)", "Func(jejairoy)"};

        System.out.println("\n================ Grafico de Barras - Funcionarios ==================");
        for (int i = 0; i < valores.length; i++) {
            System.out.print(nomes[i] + " | ");
            for (int j = 0; j < valores[i]; j++) {
                System.out.print("**");
            }
            System.out.println(" (" + valores[i] + ")");
        }
    }

    public void exibirDashboard() {
        exibirDashSeguros();
        exibirDashFuncionarios();
        exibeGraficoDeBarrasSeguros();
        exibeGraficoDeBarrasFuncionario();
    }
}