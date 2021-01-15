import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import utils.Password;

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


/**
 * Application to handle API requests.
 */
public class App {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = "Api server is running!";
                exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        server.createContext("/api/v1/user/login", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
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
                        if (passwordMatch) {
                            response = "{\"success\": \"true\", \"user\": \""+rs.getString("id")+"\", \"username\": \""+rs.getString("username")+"\"}";
                        } else {
                            response = "{\"success\": \"true\", \"message\": \"Provided password is incorrect\"}";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        server.createContext("/api/v1/user/register", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
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

                try {
                    Database.insert("INSERT INTO user(username, password, salt) VALUES(?, ?, ?)", params);
                    response = "{\"success\": \"true\", \"message\": \"Successfully registered\"}";
                } catch (SQLException e) {
                    response = "{\"success\": \"false\", \"message\": \"Registration failed\"}";
                }

                exchange.sendResponseHeaders(200, response.length());
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        server.createContext("/api/v1/app/save", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
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
                String jsonString = buf.toString();
                Gson gson = new Gson();
                JsonObject object = gson.fromJson(jsonString, JsonObject.class);

                List<String> params = new ArrayList<>();
                params.add(object.get("user").getAsString());
                params.add(object.get("name").getAsString());
                params.add(object.get("content").getAsString());
                params.add(salt);

                try {
                    Database.insert("INSERT INTO aplication(user_id, name, content) VALUES(?, ?, ?)", params);
                    response = "{\"success\": \"true\", \"message\": \"Successfully saved\"}";
                } catch (SQLException e) {
                    response = "{\"success\": \"false\", \"message\": \"Save failed\"}";
                }

                exchange.sendResponseHeaders(200, response.length());
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        server.createContext("/api/v1/app/load", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
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
        server.createContext("/api/v1/setting/save", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
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
                String jsonString = buf.toString();
                Gson gson = new Gson();
                JsonObject object = gson.fromJson(jsonString, JsonObject.class);

                List<String> params = new ArrayList<>();
                params.add(object.get("application").getAsString());
                params.add(object.get("keyIndex").getAsString());
                params.add(object.get("value").getAsString());

                try {
                    Database.insert("INSERT INTO setting(application_id, keyIndex, value) VALUES(?, ?, ?)", params);
                    response = "{\"success\": \"true\", \"message\": \"Successfully saved\"}";
                } catch (SQLException e) {
                    response = "{\"success\": \"false\", \"message\": \"Save failed\"}";
                }

                exchange.sendResponseHeaders(200, response.length());
                OutputStream output = exchange.getResponseBody();
                output.write(response.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        server.createContext("/api/v1/setting/load", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
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
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
