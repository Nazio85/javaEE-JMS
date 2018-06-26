package pro.xway.jms.message;

import java.io.Serializable;

public class Message implements Serializable {
    private String description;

    public Message() {
    }

    public Message(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
