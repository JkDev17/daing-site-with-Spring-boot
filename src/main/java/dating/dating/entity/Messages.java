package dating.dating.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Messages 
{
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String senderFullname;

    @Column(nullable = false)
    private String recipientFullname;
    
    @Column(nullable = false)
    private LocalDateTime localDateTime;

    public Messages() 
    {
                
    }

    public Messages(String content, String senderFullname, String recipientFullname, LocalDateTime localDateTime) 
    {
        this.content = content;
        this.senderFullname = senderFullname;
        this.recipientFullname = recipientFullname;
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() 
    {
        return this.localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) 
    {
        this.localDateTime = localDateTime;
    }

    public String getContent() 
    {
        return this.content;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getSender() 
    {
        return this.senderFullname;
    }

    public void setSender(String sender) 
    {
        this.senderFullname = sender;
    }

    public String getRecipientFullname() 
    {
        return this.recipientFullname;
    }

    public void setRecipientFullname(String recipientFullname) 
    {
        this.recipientFullname = recipientFullname;
    }
}