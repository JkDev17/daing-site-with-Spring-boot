package dating.dating.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import dating.dating.entity.Messages;

@DataJpaTest
public class MessagesRepositoryTest 
{   
    @Autowired
    MessagesRepository messagesRepository;

    public static Messages messages;

    @BeforeAll
    public static void initializeMessages()
    {
        String content = "Life can be extremely rough sometimes";
        String senderFullname = "Jasmine White";
        String recipientFullname = "GeorgeWilliams";
        LocalDateTime localDateTime = LocalDateTime.now();
        messages = new Messages(content, senderFullname, recipientFullname, localDateTime);
    }
    
    @Test
    @DisplayName("Testing method save Messages")
    public void canSaveMessages()
    {
        messagesRepository.save(messages);
    }
        
    @Test
    @DisplayName("Testing method  getMessagesOrderByLocalDateTime")
    public void canGetMessagesOrderByLocalDateTime()
    {
        //given
        canSaveMessages();
        String recipient = "Jasmine White"; 
        String sender = "George Williams";

        //when  
        List<Messages> listOfMessages = messagesRepository.getMessagesOrderByLocalDateTime(sender, recipient);
        
        //then
        assertNotNull(listOfMessages);
    }
}