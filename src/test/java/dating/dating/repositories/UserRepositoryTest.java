package dating.dating.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import dating.dating.entity.Users;

@DataJpaTest
public class UserRepositoryTest
{
    @Autowired
    UsersRepository usersRepository;

    public static Users user;

    @BeforeAll()
    public static void InitializeUser()
    {
        Date sqlDate = Date.valueOf("1980-04-09");
        user = new Users("JohnWilliams@gmail.com","John Williams", "1234567890A", "Male", sqlDate, "Master",
        "Programmer", "London", "Traveling Music Cooking Sports", 
        175.5, 80.0, "black", "white", "blue", '1', true, "user");
    }
    

    @Test
    @DisplayName("Testing if we can save user")
    public void canSaveUser()
    {
        //when
        usersRepository.save(user);
        
        //then
        assertNotNull(user.getId());
    }

    @Test
    @DisplayName("Testning if we can get email based on id")
    public void canfindByEmail()
    {
        //given
        canSaveUser();
        String email = "JohnWilliams@gmail.com";

        //when
        int actualId = usersRepository.getIdByEmail(email);

        //then
        assertNotNull(actualId);
    }

    @Test
    @DisplayName("Testing method findByEmail when res is present")
    public void findByEmailTaken()
    {
        //given
        String  email = "JohnWilliams@gmail.com";

        //when
        Optional<String> result = usersRepository.findByEmail(email);

        //then
        if (result.isPresent())
        {
            assertNotNull(result);
        }
    }

    @Test
    @DisplayName("Testing method findByEmail when res is Optional empty")
    public void findByEmailNew()
    {
        //given
        String  email = "John Williamson@gmail.com";

        //when
        Optional<String> result = usersRepository.findByEmail(email);

        //then
        assertEquals(Optional.empty(), result);
    }

    @Test
    @DisplayName("Testing method getEmailByFullname")
    public void canGetEmailByFullname()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliams@gmail.com";
        String expectedResult = "John Williams";


        //when
        String actualResult = usersRepository.getFullNameByEmail(email);

        //then
        assertEquals(expectedResult, actualResult);
    }


    @Test
    @DisplayName("Testing method cannotGetEmailByFullname")
    public void cannotGetEmailByFullname()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliamson@gmail.com";
        String expectedResult = null;


        //when
        String actualResult = usersRepository.getFullNameByEmail(email);

        //then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Testing method GetHeightByEmail")
    public void canGetHeightByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliams@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotGetHeightByEmail")
    public void cannotGetHeightByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliamson@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNull(actualResult);
    }

    @Test
    @DisplayName("Testing method GetWeightByEmail")
    public void canGetWeightByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliams@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotGetWeightByEmail")
    public void cannotGetWeightByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliamson@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNull(actualResult);
    }

    @Test
    @DisplayName("Testing method GetJobByEmail")
    public void canGetJobByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliams@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotGetJobByEmail")
    public void cannotGetJobByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliamson@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNull(actualResult);
    }


    @Test
    @DisplayName("Testing method canGetBdayByEmail")
    public void canGetBdayByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliams@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotBdayByEmail")
    public void cannotBdayByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliamson@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNull(actualResult);
    }

    @Test
    @DisplayName("Testing method canGetLocationByEmail")
    public void canGetLocationByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliams@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotGetLocationByEmail")
    public void cannotGetLocationByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliamson@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNull(actualResult);
    }

    @Test
    @DisplayName("Testing method getEducationByEmail")
    public void canGetEducationByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliams@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotGetEducationByEmail")
    public void cannotGetEducationByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliamson@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNull(actualResult);
    }

    @Test
    @DisplayName("Testing method canGetHairColorByEmail")
    public void canGetHairColorByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliams@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotGetHairColorByEmail")
    public void cannotGetHairColorByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliamson@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNull(actualResult);
    }

    @Test
    @DisplayName("Testing method canGetEyeColorByEmail")
    public void canGetEyeColorByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliams@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotGetEyeColorByEmail")
    public void cannotGetEyeColorByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliamson@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNull(actualResult);
    }

    @Test
    @DisplayName("Testing method canGetHobbiesByEmail")
    public void canGetHobbiesByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliams@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotGetEyeColorByEmail")
    public void cannotGetHobbiesByEmail()
    {
        //given
        canSaveUser();
        String  email = "JohnWilliamson@gmail.com";

        //when
        String actualResult = usersRepository.getHeightByEmail(email);

        //then
       assertNull(actualResult);
    }

    @Test
    @DisplayName("Testing method getFullnameById")
    public void canGetFullnameById()
    {
        //given
        canSaveUser();
        String expectedFullname = "John Williams";
        int id = usersRepository.getIdByEmail("JohnWilliams@gmail.com");


        //when
        String actualFullname = usersRepository.getFullnameById(id);

        //then
        assertEquals(expectedFullname, actualFullname);
    }

    @Test
    @DisplayName("Testing method getIdByFullname")
    public void canGetIdByFullname()
    {
        //given
        canSaveUser();
        String fullname = "John Williams";

        //when
        int id = usersRepository.getIdByFullname(fullname);

        //then
        assertNotNull(id);
    }

    @Test
    @DisplayName("Testing method getStarFromUsersNeqToId")
    public void canGetStarFromUsersNeqToId()
    {
        //given
        canSaveUser();
        String email = "JohnWilliams@gmail.com";
        int id = usersRepository.getIdByEmail(email);

        //when
        List <Users> listOfUsers = usersRepository.getStarFromUsersNeqToId(id);

        //then
        assertNotNull(listOfUsers);
    }

    @Test
    @DisplayName("Testing method updateIsPrem")
    public void updateIsPrem()
    {
        //given
        canSaveUser();
        String email = "JohnWilliams@gmail.com";

        //when
        int result = usersRepository.updateIsPrem(email);

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Testing method cannotUpdateIsPrem")
    public void cannotUpdateIsPrem()
    {
        //given
        canSaveUser();
        String email = "";
        int expectedResult = 0;

        //when
        int actualResult = usersRepository.updateIsPrem(email);

        //then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Testing method updateRole")
    public void updateRole()
    {
        //given
        canSaveUser();
        String email = "JohnWilliams@gmail.com";

        //when
        int result = usersRepository.updateRole(email);

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Testing method cannotUpdateRole")
    public void cannotUpdateRole()
    {
        //given
        canSaveUser();
        String email = "";
        int expectedResult = 0;

        //when
        int actualResult = usersRepository.updateRole(email);

        //then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Testing method canFindPasswordByEmail")
    public void canFindPasswordByEmail()
    {
        //given
        canSaveUser();
        String email = "JohnWilliams@gmail.com";

        //when
        String passwordResult = usersRepository.findPasswordByEmail(email);

        //then
        assertNotNull(passwordResult);
    }



    @Test
    @DisplayName("Testing method cannotFindPasswordByEmail")
    public void cannotFindPasswordByEmail()
    {
        //given
        canSaveUser();
        String email = "";

        //when
        String passwordResult = usersRepository.findPasswordByEmail(email);

        //then
        assertNull(passwordResult);
    }

    @Test
    @DisplayName("Testing method canGetUserIsPrem")
    public void canGetUserIsPrem()
    {
        //given
        canSaveUser();
        String email = "JohnWilliams@gmail.com";

        //when
        char actualResult = usersRepository.userIsPrem(email);

        //then
        assertNotNull(actualResult);
    }



    @Test
    @DisplayName("Testing method getLastId")
    public void canGetLastId()
    {
        //given
        canSaveUser();

        //when
        int result = usersRepository.getLastId();

        //then
        assertNotNull(result);
    }


    @Test
    @DisplayName("Testing method selecStartWithFilters1")
    public void canSelecStartWithFilters1()
    {
        //given
        canSaveUser();
        String gender = "males";
        String hairColor = "black";
        int idToExclude = 0;

        //when
        List<Users> listOfUsers = usersRepository.selecStartWithFilters1(gender, hairColor, idToExclude);


        //then
        assertNotNull(listOfUsers);
    }


        @Test
    @DisplayName("Testing method selecStartWithFilters1")
    public void canSelecStartWithFilters2()
    {
        //given
        canSaveUser();
        String gender = "males";
        int idToExclude = 0;

        //when
        List<Users> listOfUsers = usersRepository.selecStartWithFilters2(gender,  idToExclude);


        //then
        assertNotNull(listOfUsers);
    }




    @Test
    @DisplayName("Testing method selecStartWithFilters1")
    public void canSelecStartWithFilters3()
    {
        //given
        canSaveUser();
        String hairColor = "black";
        int idToExclude = 0;

        //when
        List<Users> listOfUsers = usersRepository.selecStartWithFilters3( hairColor, idToExclude);


        //then
        assertNotNull(listOfUsers);
    }
    
}