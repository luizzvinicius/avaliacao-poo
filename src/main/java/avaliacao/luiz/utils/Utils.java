package avaliacao.luiz.utils;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Utils implements AutoCloseable {
    private Scanner scan;

    public Utils() {
        this.scan = new Scanner(System.in);
    }

    public String lerString(String msg, String errorMessage) {
        var scan = System.console();
        while (true) {
            System.out.print(msg);
            var palavra = scan.readLine().strip().toLowerCase();
            if (!Pattern.matches("^[a-zÀ-ÿ\s]+$", palavra)) {
                System.out.println(errorMessage + "\n");
                continue;
            }
            return palavra;
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
                return num;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        this.scan.close();
    }
}