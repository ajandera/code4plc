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
public class Application extends Base {

    /**
     * @param exchange
     */
    public static void getApplication(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            Map<String, String> parameters = getParamMap(exchange.getRequestURI().getQuery());
            List<String> params = new ArrayList<>();
            params.add(parameters.get("id"));

            try {
                ResultSet rs = Database.query("SELECT * FROM application WHERE id = ?", params);
                while (true) {
                    assert rs != null;
                    if (!rs.next()) break;
                    model.Application app = new model.Application();
                    app.setId(rs.getString("id"));
                    app.setName(rs.getString("name"));
                    app.setContent(rs.getString("content"));
                    Gson gson = new Gson();
                    String jsonResponse = gson.toJson(app);
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
    public static void save(HttpExchange exchange) throws IOException {
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
            params.add(object.get("user").getAsString());
            params.add(object.get("name").getAsString());
            params.add(object.get("content").getAsString());
            Response response = new Response();

            try {
                Database.insert("INSERT INTO application(user_id, name, content) VALUES(?, ?, ?)", params);
                response.setSuccess(true);
                response.setMessage("Successfully saved");
                String jsonResponse = gson.toJson(response);
                exchange.sendResponseHeaders(200, jsonResponse.length());
                OutputStream output = exchange.getResponseBody();
                output.write(jsonResponse.getBytes());
                output.flush();
            } catch (SQLException e) {
                response.setSuccess(false);
                response.setMessage("Save failed");
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
