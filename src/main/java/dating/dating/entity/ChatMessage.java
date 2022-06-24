package dating.dating.entity;


public class ChatMessage 
{
    private MessageType type;
    private String content;
    private String sender;
    private String recipientFullname;

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