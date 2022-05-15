package main.server.util;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** 
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @date 2022/5/14
 */
public class LogUtil {

    /**
     * Get the current time in the format of yyyy-MM-dd HH:mm:ss.SSSS
     * @return the current timestamp as a String
     */
    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Logs a simple message with a timestamp. In this case, we just write the
     * message to the server applications standard output.
     * @param message
     */
    public static void log(String message) {
        System.out.println(getCurrentTime() + " " + message);
    }

}