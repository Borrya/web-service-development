package dbService.dao;

import accounts.UserProfile;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet get(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2),result.getString(3));
        });
    }

    public UsersDataSet getByLogin(String login)  throws SQLException{
        return executor.execQuery("select * from users where login= '" + login + "'", result -> {
                result.next();
                return new UsersDataSet(result.getLong(1), result.getString(2) , result.getString(3));
            });

    }

    public long getUserId(String name) throws SQLException {
        return executor.execQuery("select * from users where login='" + name + "'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    public void insertUser(UserProfile userProfile) throws SQLException {
        executor.execUpdate("insert into users (login, password) values ('" + userProfile.getLogin() + "' , '" + userProfile.getPass() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id serial not null primary key, login varchar(256), password varchar(256))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}
