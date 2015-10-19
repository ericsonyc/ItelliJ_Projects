package leetcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by ericson on 2015/8/14 0014.
 */
public class WebServer {
    public static void main(String[] args) {
        try {
//            WebServer web = new WebServer();
//            ServerSocket server = new ServerSocket(80);
//            Socket s = null;
//            while ((s = server.accept()) != null) {
//                web.new HttpThread(s).start();
//            }

            URL url = new URL("http://www.baidu.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            Map<String, List<String>> header = conn.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : header.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class HttpThread extends Thread {
        Socket socket = null;

        public HttpThread(Socket s) {
            this.socket = s;
        }

        @Override
        public void run() {
            try {
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                pw.println("<html>");
                pw.println("<body>");
                pw.println("hello, this is my web page");
                pw.println("</body>");
                pw.println("</html>");
                pw.flush();
                pw.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
