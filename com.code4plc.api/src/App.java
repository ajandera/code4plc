import com.sun.net.httpserver.HttpServer;
import rest.Application;
import rest.Setting;
import rest.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import rest.Base;


/**
 * Application to handle API requests.
 */
public class App {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", Base::doGet);
        server.createContext("/api/v1/user", User::getUser);
        server.createContext("/api/v1/user/login", User::authorize);
        server.createContext("/api/v1/user/register", User::registration);
        server.createContext("/api/v1/app", Application::getApplication);
        server.createContext("/api/v1/app/save", Application::save);
        server.createContext("/api/v1/setting", Setting::getSetting);
        server.createContext("/api/v1/setting/save", Setting::save);
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
