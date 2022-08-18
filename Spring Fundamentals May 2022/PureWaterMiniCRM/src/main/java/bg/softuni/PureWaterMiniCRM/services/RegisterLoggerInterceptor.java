package bg.softuni.PureWaterMiniCRM.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

@Configuration
public class RegisterLoggerInterceptor implements HandlerInterceptor {

    private Logger logger =  java.util.logging.Logger.getLogger(this.getClass().getName());

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);

        //Unsuccessful attempt
        if ( request.getMethod().equals("POST") && request.getRequestURI().equals("/users/register")
                && response.getStatus() == 302 &&  response.getHeader("Location").equals("http://localhost:8080/users/register")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String username = request.getParameter("username");

            logger.info("//// A new register attempt has occurred.");
            logger.info(String.format("// A user attempted to register unsuccessfully with first name: %s, last name: %s and username: %s.", firstName, lastName, username));
        }

        //Successful attempt
        if ( request.getMethod().equals("POST") && request.getRequestURI().equals("/users/register")
                && response.getStatus() == 302 &&  response.getHeader("Location").equals("http://localhost:8080/users/login")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String username = request.getParameter("username");

            logger.info("//// A new register attempt has occurred.");
            logger.info(String.format("// A user registered successfully with first name: %s, last name: %s and username: %s.", firstName, lastName, username));
        }
    }
}
