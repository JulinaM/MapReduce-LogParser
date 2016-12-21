package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jmaharjan on 12/21/16.
 */
public class LogParser {
    private String logEntryPattern = "^([\\d\\w.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+)";
    private static final int NUM_FIELDS = 7;
    private Log log = new Log();

    public void parse(String logEntryLine){
        Pattern p = Pattern.compile(logEntryPattern);
        Matcher matcher = p.matcher(logEntryLine);
        if (!matcher.matches() || NUM_FIELDS != matcher.groupCount()) {
            System.err.println("Bad log entry (or problem with RE?):");
            System.err.println(logEntryLine);
            throw new RuntimeException();
        }
        this.log.setIpAdress(matcher.group(1));
        this.log.setDateAndTime(matcher.group(4));
        this.log.setRequest(matcher.group(5));
        this.log.setResponse(matcher.group(6));
        this.log.setBytes(Integer.parseInt(matcher.group(7)));
    }

    public Log getLog(){
        return this.log;
    }

}
