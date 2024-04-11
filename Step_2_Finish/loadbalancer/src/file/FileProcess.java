package file;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.yamlbeans.YamlException;
import net.sourceforge.yamlbeans.YamlReader;
import serverconnector.ServerCommunicator;

public class FileProcess {
    private static final List<ServerCommunicator> serverCommunicatorList = new ArrayList<>();
    public static void readFile(String path) throws IOException, YamlException {
        try {
            YamlReader reader = new YamlReader(new FileReader(path));
            Object object;
            while ((object = reader.read()) != null) {
                if (object instanceof Map) {
                    Map<String, List<Map<String, String>>> data = (Map<String, List<Map<String, String>>>) object;
                    List<Map<String, String>> servers = data.get("servers");
                    for (Map<String, String> server : servers) {
                        String host =  server.get("host");
                        int port = Integer.parseInt(server.get("port"));
                        ServerCommunicator serverCommunicator = new ServerCommunicator(host, port);
                        serverCommunicatorList.add(serverCommunicator);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<ServerCommunicator> getServerCommunicatorList() {
        return serverCommunicatorList;
    }
}
