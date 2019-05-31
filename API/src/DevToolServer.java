import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import Handlers.AuthenticateUserHandler;
import Handlers.GetProjectHandler;
import Handlers.GetUserProjects;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

import javax.net.ssl.*;

public class DevToolServer {
    public static void main(String args[]){
        try{
            HttpsServer server = HttpsServer.create(new InetSocketAddress(16802), 0);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            char[] password = "password".toCharArray();
            KeyStore keyStore = KeyStore.getInstance("JKS");
            InputStream inputStream = DevToolServer.class.getClassLoader().getResourceAsStream("key.jks");
            keyStore.load(inputStream, password);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, password);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            server.setHttpsConfigurator(new HttpsConfigurator(sslContext){
                public void configure(HttpsParameters params){
                    try{
                        SSLContext context = SSLContext.getDefault();
                        SSLEngine engine = context.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        //Fetch default params
                        SSLParameters defaultSSLParams = context.getDefaultSSLParameters();
                        params.setSSLParameters(defaultSSLParams);
                    } catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
            server.createContext("/authenticate_user", new AuthenticateUserHandler());
            server.createContext("/get_user_projects", new GetUserProjects());
            server.createContext("/get_project", new GetProjectHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Server running and listening on 16802");
        } catch (Exception ex){
            //In my years of working with Java HTTP/s, never once have I encountered this
            //But it's here to shut IntelliJ up
            ex.printStackTrace();
        }
    }
}
