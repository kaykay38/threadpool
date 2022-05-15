import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** 
 * @author Tianyang Liao, Mia Hunt, Samuel Urcino-Martinez
 * @course CSCD 467
 * @date 2022/5/14
 */
public class Utils {

    public static String getTimeStamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSS yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

}