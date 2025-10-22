package org.example.yardflow;
import org.example.yardflow.model.Registro_check_in_out;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class testeCalculoPermanencia {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Registro_check_in_out registro = new Registro_check_in_out();

        try {
            // Entrada
            System.out.print("Digite a data de entrada (dd/MM/yyyy): ");
            String entradaStr = scanner.nextLine();
            LocalDate entrada = LocalDate.parse(entradaStr, formatter);
            registro.setEntradapatio(entrada);

            // Saída (opcional)
            System.out.print("Digite a data de saída (dd/MM/yyyy) ou deixe em branco: ");
            String saidaStr = scanner.nextLine();
            if (!saidaStr.isBlank()) {
                LocalDate saida = LocalDate.parse(saidaStr, formatter);
                registro.setSaidapatio(saida);
            }

            // Calcula período
            registro.calcularPeriodoPermanencia();

            System.out.println("Período de permanência: " + registro.getPeriodo() + " dia(s)");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
