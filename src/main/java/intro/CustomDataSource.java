package intro;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CustomDataSource extends PGSimpleDataSource {

    private final Queue<Connection> CONNECTION_POOL;
    private final int POOL_SIZE = 10;


    public CustomDataSource(String url, String userName, String userPass) throws SQLException {
        super();
        setURL(url);
        setUser(userName);
        setPassword(userPass);
        CONNECTION_POOL = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection originConnection = super.getConnection();
            var connectionProxy = new ConnectionProxy(originConnection, CONNECTION_POOL);
            CONNECTION_POOL.add(connectionProxy);
        }
    }

    @Override
    public Connection getConnection() {
        System.out.println("Get connection!");
        return CONNECTION_POOL.poll();
    }

}
