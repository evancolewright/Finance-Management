package connection;

import java.sql.*;

public class DatabaseConnection {
    private final String CONNECTION_STRING = "jdbc:mysql://localhost/finance_management";
    private Connection connection;
    private PreparedStatement sql_statement;

    public DatabaseConnection(String username, String password) throws SQLException{
        this.connection = DriverManager.getConnection(CONNECTION_STRING, username, password);
    }

    public DatabaseConnection(String username, String password, String query) throws SQLException {
        this.connection = DriverManager.getConnection(CONNECTION_STRING, username, password);
        this.sql_statement = this.connection.prepareStatement(query);
    }

    public void setQuery(String query) throws SQLException {
        this.sql_statement = this.connection.prepareStatement(query);
    }

    public void setStatementString(int parameterIndex, String statement_string) throws SQLException {
        this.sql_statement.setString(parameterIndex, statement_string);
    }

    public void setStatementInt(int parameterIndex, int statement_int) throws SQLException {
        this.sql_statement.setInt(parameterIndex, statement_int);
    }

    public void setStatementDate(int parameterIndex, Date statement_date) throws SQLException {
        this.sql_statement.setDate(parameterIndex, statement_date);
    }

    public void setStatementDouble(int parameterIndex, double statement_double) throws SQLException {
        this.sql_statement.setDouble(parameterIndex, statement_double);
    }

    public PreparedStatement getSql_statement(){
        return this.sql_statement;
    }

    public Connection getConnection(){
        return this.connection;
    }

    public void closePreparedStatement() throws SQLException {
        this.sql_statement.close();
    }

    public void closeConnection() throws SQLException{
        this.connection.close();
    }
}
