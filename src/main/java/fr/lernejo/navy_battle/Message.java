package fr.lernejo.navy_battle;

public class Message {
    private String id;
    private String url;
    private String message;

    public Message(String id, String url, String message){
        setId(id);
        setUrl(url);
        setMessage(message);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "{ 'id' : '" + id + "', 'url' : '" + url + "', 'message' : '" + message + "'}";
    }
}
