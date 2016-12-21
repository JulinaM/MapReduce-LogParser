package utils;

/**
 * Created by jmaharjan on 12/21/16.
 */
public class Log {
    private String ipAdress;
    private String dateAndTime;
    private String request;
    private String response;
    private int bytes;
    private String browser;

    public Log() {
    }


    public  Log(String ipAdress, String dateAndTime, String request, String response, int bytes, String browser){
        this.ipAdress = ipAdress;
        this.dateAndTime = dateAndTime;
        this.request = request;
        this.response = response;
        this.bytes = bytes;
        this.browser = browser;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

}
