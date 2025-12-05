package com.banksecure.app;

import com.banksecure.domain.Seguro;
import com.banksecure.infra.DAO.SeguroDAO;
import java.math.BigDecimal;
import java.util.List;

public class Dashboard {
    public void exibirDashboard() {
        SeguroDAO seguroDAO = new SeguroDAO();
        seguroDAO.iniciaTabelas();
        List<Seguro> segurosVendidos = seguroDAO.getAll();

        int qtdSeguroVida = 0;
        BigDecimal totalVida = BigDecimal.ZERO;

        int qtdSeguroResidencial = 0;
        BigDecimal totalResidencial = BigDecimal.ZERO;

        int qtdSeguroAuto = 0;
        BigDecimal totalAuto = BigDecimal.ZERO;

        for (Seguro seguro : segurosVendidos) {
            String titulo = seguro.getTitulo().toLowerCase();
            BigDecimal valorBase = seguro.getValorBase();

            if (titulo.contains("vida")) {
                qtdSeguroVida++;
                totalVida = totalVida.add(valorBase);
            } else if (titulo.contains("residencial")) {
                qtdSeguroResidencial++;
                totalResidencial = totalResidencial.add(valorBase);
            } else if (titulo.contains("autom")) {
                qtdSeguroAuto++;
                totalAuto = totalAuto.add(valorBase);
            }
        }
        System.out.println("================== Dashboard de Seguros Vendidos ==================");
        System.out.printf("Tipo: Vida        | Apolices: %d | Total Arrecadado: R$ %.2f%n", qtdSeguroVida, totalVida);
        System.out.printf("Tipo: Residencial | Apolices: %d | Total Arrecadado: R$ %.2f%n", qtdSeguroResidencial, totalResidencial);
        System.out.printf("Tipo: Automóvel   | Apolices: %d | Total Arrecadado: R$ %.2f%n", qtdSeguroAuto, totalAuto);
    }
}


