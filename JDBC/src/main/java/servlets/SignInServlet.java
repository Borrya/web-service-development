package servlets;

import accounts.AccountService;
import accounts.UserProfile;
import dbService.DBService;
import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class SignInServlet extends HttpServlet {
    private final AccountService accountService;
    private final Connection connection = DBService.getPostgresConnection();

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException{
        try {
            String login = request.getParameter("login");
            String pass = request.getParameter("password");
            UsersDAO dbUsers = new UsersDAO(connection);

            if (login == null || pass == null) {
                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            UsersDataSet profile = dbUsers.getByLogin(login);

            if (profile == null || !profile.getPassword().equals(pass)) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println("Unauthorized");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Authorized: " + profile.getLogin());
            response.setStatus(HttpServletResponse.SC_OK);
        }
        catch (SQLException e){
            System.out.println("Something wrong in signInServlet");
        }
    }
}
