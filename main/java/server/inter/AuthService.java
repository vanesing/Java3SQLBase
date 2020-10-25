package server.inter;

import java.sql.SQLException;

public interface AuthService {
    void start() throws SQLException, ClassNotFoundException;
    String getNick(String login, String password) throws SQLException;
    void stop() throws SQLException;
}
