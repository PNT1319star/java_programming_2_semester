package file;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientInfoWriter {
    private static final String CSV_FILE_PATH = "D:\\Admin\\IdeaProjects\\Step_2_Finish\\test\\ClientInfomation.csv";

    public static void writeClientInfo(String host, String port) {
        try {
            FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String currentDate = dateFormat.format(new Date());
            fileWriter.append(String.format("%s,%s,%s\n", host, port, currentDate));

            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
