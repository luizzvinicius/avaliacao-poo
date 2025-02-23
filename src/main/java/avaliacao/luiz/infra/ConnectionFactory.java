package avaliacao.luiz.infra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory implements AutoCloseable {
    private static Connection conn = null;

    public static Connection getConn() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/av-poo", "postgres", "vinicius");
                criarBanco(conn);
            } catch (SQLException e) {
                System.out.println("Erro ao se conectar ao banco: " + e.getMessage());
            }
        }
        return conn;
    }

    private static void criarBanco(Connection conn) {
        var path = Paths.get("src/tables.sql");
        try (var stmt = conn.createStatement()) {
            var file = Files.readString(path).replace("\r\n", "").split(";");
            for (String command : file) {
                stmt.addBatch(command + ";");
            }
            stmt.executeBatch();
        } catch (IOException e) {
            System.out.println("Erro ao ler tables.sql: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao criar preparedStatement: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void close() throws Exception {
        conn.close();
    }
}