package dating.dating.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Timestamp;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import dating.dating.entity.UserVisitedUsers;

@DataJpaTest
public class UserVisitedUsersRepositoryTest 
{
    
    @Autowired
    UserVisitedUsersRepository userVisitedUsersRepository;

    public static  UserVisitedUsers userVisitedUsers;

    @BeforeAll
    public static void initializeUserVisitedUsers()
    {
        int id1 = 0;
        int id2 = 1;
        Timestamp timestamp =  new Timestamp(System.currentTimeMillis());

        userVisitedUsers = new UserVisitedUsers(id1,id2,timestamp);
    }


    @Test
    @DisplayName("Testing method save for UserVisitedUsers")
    public void canSaveUserVisitedUsers()
    {   
        userVisitedUsersRepository.save(userVisitedUsers);
    }

    @Test
    @DisplayName("Testing method getUsersVisitedUser")
    public void canGetUsersVisitedUser()
    {
        //given
        int id = 1;

        //when
        int idRes = userVisitedUsersRepository.getUsersVisitedUser(id);

        //then
        assertNotNull(idRes);
    }

    @Test
    @DisplayName("Testing method getUid1_VisitedUser")
    public void getUid1_VisitedUser()
    {
        //given
        int id1 = 1;

        //when
        List<Integer> idRes = userVisitedUsersRepository.getUid1_VisitedUser(id1);

        //then
        assertNotNull(idRes);
    }

    
    @Test
    @DisplayName("Testing method getUid1_VisitedUserUid2")
    public void getUid1_VisitedUserUid2()
    {
        //given
        canSaveUserVisitedUsers();
        int id1 = 0;
        int id2 = 1;

        //when
       Integer idRes = userVisitedUsersRepository.getUid1_VisitedUserUid2(id2, id1);

        //then
        assertNotNull(idRes);
    }
    
    @Test
    @DisplayName("Testing method updateUid1_VisitedUserUid2")
    public void updateUid1_VisitedUserUid2()
    {
        //given
        canSaveUserVisitedUsers();
        int id1 = 0;
        int id2 = 1;
        Timestamp timestamp =  new Timestamp(System.currentTimeMillis());

        //when
       Integer idRes = userVisitedUsersRepository.updateUid1_VisitedUserUid2(id1, id2,timestamp);

        //then
        assertNotNull(idRes);
    }

    @Test
    @DisplayName("Testing method deleteStarUserVisitedUsers")
    public void canDeleteStarUserVisitedUsers()
    {
        userVisitedUsersRepository.deleteStarUserVisitedUsers();
    }

    
}