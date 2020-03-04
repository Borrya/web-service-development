package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import com.google.gson.Gson;
import dbService.DBException;
import dbService.DBService;
import dbService.dao.UsersDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;
    private final Connection connection = DBService.getPostgresConnection();

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException {
        try {
            String login = request.getParameter("login");
            String pass = request.getParameter("password");
            UsersDAO dbUsers = new UsersDAO(connection);

            UserProfile profile = new UserProfile(login, pass);
            dbUsers.insertUser(profile);

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getStatus();
        }
        catch (SQLException e){
            System.out.println("Something wrong in signUpServlet");
        }
    }
}
