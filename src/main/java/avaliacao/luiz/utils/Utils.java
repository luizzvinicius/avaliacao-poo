package avaliacao.luiz.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utils implements AutoCloseable {
    private final Scanner scan;

    public Utils() {
        this.scan = new Scanner(System.in);
    }

    public String lerString(String msg, String errorMessage) {
        while (true) {
            System.out.print(msg);
            var palavra = this.scan.nextLine().strip().toLowerCase();
            if (!Pattern.matches("^[a-zÀ-ÿ\s]+$", palavra)) {
                System.out.println(errorMessage + "\n");
                continue;
            }
            return palavra;
        }
    }

    public String lerOnzeDigitos(String msg, String errorMessage) {
        while (true) {
            System.out.print(msg);
            var numero = this.scan.nextLine().strip();
            if (!Pattern.matches("^\\d{11}$", numero)) {
                System.out.println(errorMessage + "\n");
                continue;
            }
            return numero;
        }
    }

    public String lerCnpj(String msg, String errorMessage) {
        while (true) {
            System.out.print(msg);
            var numero = this.scan.nextLine().strip();
            if (!Pattern.matches("^\\d{14}$", numero)) {
                System.out.println(errorMessage + "\n");
                continue;
            }
            return numero;
        }
    }

    public int lerInt(String msg) {
        while (true) {
            System.out.print(msg);
            var num = this.scan.nextLine();
            try {
                return Integer.parseInt(num);
            } catch (NumberFormatException e) {
                System.out.printf("Número inteiro inválido. %s%n%n", e.getMessage());
            }
        }
    }

    public double lerDouble(String msg) {
        while (true) {
            System.out.print(msg);
            var num = this.scan.nextLine();
            try {
                return Double.parseDouble(num);
            } catch (NumberFormatException e) {
                System.out.printf("Número decimal inválido. %s%n%n", e.getMessage());
            }
        }
    }

    public int lerOption(String msg, int min, int max, String errorMessage) {
        while (true) {
            var num = this.lerInt(msg);
            try {
                if (num < min || num > max) {
                    throw new IllegalArgumentException(errorMessage + "\n");
                }
                return num - 1;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public LocalDate lerData(String msg) {
        while (true) {
            System.out.print(msg);
            String data = this.scan.nextLine().strip();
            if (!Pattern.matches("^\\d{2}/\\d{2}/\\d{4}$", data)) {
                System.out.println("Data no formato inválido\n");
                continue;
            }
            try {
                var arrayData = data.split("/");
                return LocalDate.parse(String.format("%s-%s-%s", arrayData[2], arrayData[1], arrayData[0]));
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida");
            }
        }
    }

    public <T> void mostraArrayFormatado(List<T> lista) {
        for (int i = 0; i < lista.size(); i++) {
            System.out.printf("%d- %s%n", i+1, lista.get(i).toString());
        }
        System.out.println("");
    }

    @Override
    public void close() {
        this.scan.close();
    }
}