package file;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerInfoWriter {
    private static final String CSV_FILE_PATH = "D:\\Admin\\IdeaProjects\\Step_2_Finish\\test\\ServerInfomation.csv";

    public static void writeServerInfo(String port, String path) {
        try {
            FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String currentDate = dateFormat.format(new Date());
            fileWriter.append(String.format("%s,%s,%s\n", port,path, currentDate));

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
