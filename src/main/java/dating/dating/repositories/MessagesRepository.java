package dating.dating.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import dating.dating.entity.Messages;

public interface MessagesRepository extends JpaRepository<Messages,Long> 
{
            
}
