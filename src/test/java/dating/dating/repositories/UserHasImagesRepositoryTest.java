package dating.dating.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import dating.dating.entity.UserHasImages;

@DataJpaTest
public class UserHasImagesRepositoryTest 
{
    @Autowired
    UserHasImagesRepository userHasImagesRepository;    

    public static UserHasImages userHasImages;


    @BeforeAll
    public static void initializeUserhasImages()
    {   
        int userId = 1;
        int imageId = 1;
        char isProfilePic = '1';
        userHasImages = new UserHasImages(userId, imageId, isProfilePic);
    }


    @Test
    @DisplayName("Testing method save UserHasImages")
    public void canSaveUserHasImages()
    {
        userHasImagesRepository.save(userHasImages);
    }

    @Test
    @DisplayName("Testing method  getImageIdByUserId")
    public void getImageIdByUserId()
    {
        //given
        canSaveUserHasImages();
        int id =1;

        //when
        int res = userHasImagesRepository.getImageIdByUserId(id);

        //then
        assertNotNull(res);
    }
    
    @Test
    @DisplayName("Testing method  userHasImages")
    public void userHasImages()
    {
        //given
        canSaveUserHasImages();
        int id =1;

        //when
        int res = userHasImagesRepository.userHasImages(id);

        //then
        assertNotNull(res);
    }

    @Test
    @DisplayName("Testing method  getImageIdByUsersId")
    public void getImageIdByUsersId()
    {
        //given
        canSaveUserHasImages();
        ArrayList<Integer> listOfUserIds = new ArrayList<>();
        ArrayList<Integer> actualResults  = new ArrayList<>();


        //when
        actualResults = userHasImagesRepository.getImageIdByUsersId(listOfUserIds);

        //then
        assertNotNull(actualResults);
    }

}