package com.banksecure.app;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opc;

        do{

            System.out.println("---------bank secure---------");
            System.out.println("1 - login");
            System.out.println("2 - cotacao");
            System.out.println("3 - clientes");
            System.out.println("4 - seguros");
            System.out.println("5 - dashboard");
            System.out.println("6 - sair");
            System.out.println("\nselecione a opção desejada");
            opc = sc.nextInt();

            switch (opc){
                case 1:
                    int opc2;
                    do {
                        System.out.println("---------bank secure---------");
                        System.out.println("1 - login");
                        System.out.println("2 - voltar");
                        System.out.println("\nselecione a opção desejada");
                        opc2 = sc.nextInt();
                    }while (opc2 != 2);
                case 5:
                    int opc5;
                    do {
                        Dashboard dash = new Dashboard();
                        System.out.println("" +
                                "********************************************************************" +
                                "\n**************************** Dashboard *****************************" +
                                "\n********************************************************************");
                        dash.exibirDashboard();
                        System.out.println("\n2 - voltar");
                        opc5 = sc.nextInt();

                    }while(opc5 != 2);
            }

        } while (opc !=6);

    }
}