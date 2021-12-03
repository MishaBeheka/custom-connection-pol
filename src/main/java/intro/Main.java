package intro;

import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        PGSimpleDataSource dataSource = initDataSource();

        Long startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            try(Connection connection = dataSource.getConnection()) {

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Long endTime = System.nanoTime();
        System.out.println("Result time: " + ((endTime - startTime) / 1000000) + " ms");

    }

    private static PGSimpleDataSource initDataSource() throws SQLException {
       return new CustomDataSource(
               "jdbc:postgresql://localhost:5432/postgres",
               "postgres",
               "My postgres secret");
    }
}
