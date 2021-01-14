import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import utils.ParameterFilter;
import utils.Password;

/**
 * Application to handle API requests.
 */
public class App {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8590), 0);
        HttpContext login = server.createContext("/api/v1/user/login", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                int b;
                StringBuilder buf = new StringBuilder();
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }

                br.close();
                isr.close();

                Map requestParams = (Map)exchange.getAttribute("parameters");
                List<String> params = new ArrayList<>();
                params.add(requestParams.get("username").toString());

                String providedPassword = requestParams.get("password").toString();
                ResultSet rs = null;
                try {
                    rs = Database.query("SELECT * FROM user WHERE username = ?", params);
                    while (rs.next()) {
                        String securePassword = rs.getString("password");
                        String salt = rs.getString("salt");
                        boolean passwordMatch = Password.verifyUserPassword(providedPassword, securePassword, salt);
                        if (passwordMatch) {
                            System.out.println("Provided user password " + providedPassword + " is correct.");
                        } else {
                            System.out.println("Provided password is incorrect");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                exchange.sendResponseHeaders(200, buf.toString().getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(buf.toString().getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        HttpContext register = server.createContext("/api/v1/user/register", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                boolean result;
                String response;

                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                int b;
                StringBuilder buf = new StringBuilder();
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }

                br.close();
                isr.close();

                OutputStream output = exchange.getResponseBody();
                output.write(exchange.getAttribute("parameters").toString().getBytes());

                List<String> params = new ArrayList<>();
                params.add(requestParams.get("username").toString());
                String password = requestParams.get("password").toString();
                String salt = Password.getSalt(20);
                String passwordSecure = Password.generateSecurePassword(password, salt);
                params.add(passwordSecure);
                params.add(salt);

                try {
                    result = Database.insert("INSERT INTO user(username, password, salt) VALUES(?, ?, ?)", params);
                } catch (SQLException e) {
                    result = false;
                    output.write(e.getMessage().getBytes());
                }
                if (result) {
                    exchange.sendResponseHeaders(200, buf.toString().getBytes().length);
                    response = "Successfully registered";
                } else {
                    exchange.sendResponseHeaders(500, buf.toString().getBytes().length);
                    response = "Internal server error";
                }

                output.write(response.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        HttpContext save = server.createContext("/api/v1/app/save", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                int b;
                StringBuilder buf = new StringBuilder();
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }

                br.close();
                isr.close();

                exchange.sendResponseHeaders(200, buf.toString().getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(buf.toString().getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        server.createContext("/api/v1/app/load", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                int b;
                StringBuilder buf = new StringBuilder();
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }

                br.close();
                isr.close();

                exchange.sendResponseHeaders(200, buf.toString().getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(buf.toString().getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        HttpContext setting = server.createContext("/api/v1/setting/save", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                int b;
                StringBuilder buf = new StringBuilder();
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }

                br.close();
                isr.close();

                exchange.sendResponseHeaders(200, buf.toString().getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(buf.toString().getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        server.createContext("/api/v1/setting/load", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);

                int b;
                StringBuilder buf = new StringBuilder();
                while ((b = br.read()) != -1) {
                    buf.append((char) b);
                }

                br.close();
                isr.close();

                exchange.sendResponseHeaders(200, buf.toString().getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(buf.toString().getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        //login.getFilters().add(new ParameterFilter());
        //register.getFilters().add(new ParameterFilter());
        //save.getFilters().add(new ParameterFilter());
        //setting.getFilters().add(new ParameterFilter());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
