package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.util.UserUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");

        String user_id = request.getParameter("user_id");
        if (user_id != null) {
            int id = Integer.parseInt(user_id);
            final int users_size = UserUtils.USERS.size();
            if (id >= users_size) {
                id = 0;
            }
            AuthorizedUser.setId(id);
            request.setAttribute("userId", id);
        }

        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
