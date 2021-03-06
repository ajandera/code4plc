package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import model.Database;
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
public class Setting extends Base {

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
            params.add(object.get("application").getAsString());
            params.add(object.get("keyIndex").getAsString());
            params.add(object.get("value").getAsString());
            Response response = new Response();

            try {
                Database.insert("INSERT INTO setting(application_id, keyIndex, value) VALUES(?, ?, ?)", params);
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
        } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
            System.out.println(exchange.getRequestMethod());
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
            exchange.sendResponseHeaders(204, -1);
            return;
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }

    /**
     * @param exchange
     */
    public static void getSetting(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            Map<String, String> parameters = getParamMap(exchange.getRequestURI().getQuery());
            List<String> params = new ArrayList<>();
            params.add(parameters.get("id"));
            List<model.Setting> toJson = new ArrayList<>();
            try {
                ResultSet rs = Database.query("SELECT * FROM setting WHERE application_id = ?", params);
                while (true) {
                    assert rs != null;
                    if (!rs.next()) break;
                    model.Setting setting = new model.Setting();
                    setting.setId(rs.getString("id"));
                    setting.setKeyIndex(rs.getString("keyIndex"));
                    setting.setValue(rs.getString("value"));
                    toJson.add(setting);
                }

                Gson gson = new Gson();
                String jsonResponse = gson.toJson(toJson);
                exchange.sendResponseHeaders(200, jsonResponse.length());
                OutputStream output = exchange.getResponseBody();
                output.write(jsonResponse.getBytes());
                output.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("OPTIONS".equals(exchange.getRequestMethod())) {
            System.out.println(exchange.getRequestMethod());
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");
            exchange.sendResponseHeaders(204, -1);
            return;
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }
}
