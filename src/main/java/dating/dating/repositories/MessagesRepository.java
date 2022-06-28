package dating.dating.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import dating.dating.entity.Messages;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> 
{
    @Query(value = "SELECT * FROM MESSAGES WHERE sender_fullname = ?1 AND recipient_fullname= ?2 OR sender_fullname = ?2 AND recipient_fullname = ?1 ORDER BY local_date_time", nativeQuery = true)
    public List<Messages> getMessagesOrderByLocalDateTime(String sender, String recipient);
}
