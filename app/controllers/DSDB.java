package controllers;

import play.Logger;
import play.api.PlayException;
import play.api.UsefulException;
import play.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DSDB {

    @FunctionalInterface
    public interface StatementSetup {
        public void run(Connection connection) throws Exception;
    }

    @FunctionalInterface
    public interface Parser {
        public void parse(ResultSet rs) throws SQLException;
    }

    public static void withConnection(StatementSetup function) throws UsefulException {
        Connection connection = DB.getConnection();

        try {
            function.run(connection);
        } catch (Exception e) {
            Logger.error("[MZDB] An exception occurred!");
            throw new PlayException(e.getLocalizedMessage(), e.getMessage(), e);
        } finally {
            close(connection);
        }
    }

    public static void withTransaction(StatementSetup function) throws UsefulException {
        Connection connection = DB.getConnection();

        try {
            connection.setAutoCommit(false);
            function.run(connection);
            connection.commit();
        } catch (Exception e) {
            Logger.error("[MZDB] An exception within a transaction occurred!");

            try {
                Logger.debug("[MZDB] The transaction was rolled back.");
                connection.rollback();
            } catch (Exception e2) {
                Logger.error("[MZDB] An exception occurred while rolling back the transaction!");
                throw new PlayException(e.getLocalizedMessage(), e.getMessage(), e);
            }

            throw new PlayException(e.getLocalizedMessage(), e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                Logger.error("[MZDB] An exception occurred while setting connection AutoCommit = true!");

            } finally {
                close(connection);
            }
        }
    }

    private static void close(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            Logger.error("[MZDB] An exception occurred while closing the connection!");
            e.printStackTrace();
        }
    }

    public static void executeAndParse(PreparedStatement statement, Parser parser) throws SQLException {
        ResultSet resultSet = statement.executeQuery();

        while(resultSet != null && resultSet.next()) {
            parser.parse(resultSet);
        }

        statement.close();
    }
}
