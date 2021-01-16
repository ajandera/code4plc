package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import model.Database;
import utils.Password;
import utils.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Base Rest api class
 */
public class User extends Base{

    /**
     * @param exchange
     */
    public static void getUser(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            Map<String, String> parameters = getParamMap(exchange.getRequestURI().getQuery());
            List<String> params = new ArrayList<>();
            params.add(parameters.get("id"));

            try {
                ResultSet rs = Database.query("SELECT * FROM user WHERE id = ?", params);
                while (true) {
                    assert rs != null;
                    if (!rs.next()) break;
                    model.User user = new model.User();
                    user.setId(rs.getString("id"));
                    user.setUsername(rs.getString("username"));
                    user.setLast_login(rs.getTimestamp("last_login"));
                    Gson gson = new Gson();
                    String jsonResponse = gson.toJson(user);
                    exchange.sendResponseHeaders(200, jsonResponse.length());
                    OutputStream output = exchange.getResponseBody();
                    output.write(jsonResponse.getBytes());
                    output.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    /**
     * @param exchange
     */
    public static void authorize(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
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
                String jsonString = buf.toString();
                Gson gson = new Gson();
                JsonObject object = gson.fromJson(jsonString, JsonObject.class);

                List<String> params = new ArrayList<>();
                params.add(object.get("username").getAsString());
                String providedPassword = object.get("password").getAsString();

                try {
                    ResultSet rs = Database.query("SELECT * FROM user WHERE username = ?", params);
                    while (true) {
                        assert rs != null;
                        if (!rs.next()) break;
                        String securePassword = rs.getString("password");
                        String salt = rs.getString("salt");
                        boolean passwordMatch = Password.verifyUserPassword(providedPassword, securePassword, salt);
                        Response response = new Response();

                        if (passwordMatch) {
                            response.setSuccess(true);
                            response.setId(rs.getString("id"));
                            response.setMessage("Successfully authorized");
                            String jsonResponse = gson.toJson(response);
                            exchange.sendResponseHeaders(200, jsonResponse.length());
                            OutputStream output = exchange.getResponseBody();
                            output.write(jsonResponse.getBytes());
                            output.flush();
                        } else {
                            response.setSuccess(false);
                            response.setMessage("Provided password is incorrect");
                            String jsonResponse = gson.toJson(response);
                            exchange.sendResponseHeaders(200, jsonResponse.length());
                            OutputStream output = exchange.getResponseBody();
                            output.write(jsonResponse.getBytes());
                            output.flush();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    /**
     * @param exchange
     */
    public static void registration(HttpExchange exchange) throws IOException {
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
            String jsonString = buf.toString();
            Gson gson = new Gson();
            JsonObject object = gson.fromJson(jsonString, JsonObject.class);

            List<String> params = new ArrayList<>();
            params.add(object.get("username").getAsString());
            String password = object.get("password").getAsString();
            String salt = Password.getSalt(20);
            String passwordSecure = Password.generateSecurePassword(password, salt);
            params.add(passwordSecure);
            params.add(salt);
            Response response = new Response();

            try {
                Database.insert("INSERT INTO user(username, password, salt) VALUES(?, ?, ?)", params);
                response.setSuccess(true);
                response.setMessage("Successfully registered");
                String jsonResponse = gson.toJson(response);
                exchange.sendResponseHeaders(200, jsonResponse.length());
                OutputStream output = exchange.getResponseBody();
                output.write(jsonResponse.getBytes());
                output.flush();
            } catch (SQLException e) {
                response.setSuccess(false);
                response.setMessage("Registration failed");
                String jsonResponse = gson.toJson(response);
                exchange.sendResponseHeaders(200, jsonResponse.length());
                OutputStream output = exchange.getResponseBody();
                output.write(jsonResponse.getBytes());
                output.flush();
            }
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }
}
