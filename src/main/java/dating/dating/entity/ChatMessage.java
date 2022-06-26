package dating.dating.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage 
{
    private MessageType type;
    private String content;
    private String sender;
    private String recipientFullname;
    /*private LocalDateTime localDateTime;

    public  String getLocalDateTime()
    {
        return localDateTime.format((DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public  void setLocalDateTime()
    {
        localDateTime = LocalDateTime.now();
    }*/

    public String getRecipientFullname() 
    {
        return recipientFullname;
    }

    public void setRecipientFullname(String recipientFullname) 
    {
        this.recipientFullname = recipientFullname;
    }

    public enum MessageType
    {
        CHAT, JOIN, LEAVE
    }

    public MessageType getMessageType()
    {
        return type;
    }

    public void setType(MessageType type)
    {
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getSender()
    {   
        return sender;
    }

    public void setSender(String sender)
    {
        this.sender = sender;
    }
}