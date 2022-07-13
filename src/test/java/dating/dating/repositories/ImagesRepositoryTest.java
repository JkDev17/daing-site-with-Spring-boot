package dating.dating.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import dating.dating.entity.Images;

@DataJpaTest

public class ImagesRepositoryTest 
{
    @Autowired
    ImagesRepository imagesRepository;

    public static Images images;


    @BeforeAll
    public static void initializeImage()
    {
        byte[] imageData = "data/Jpeg/randomBytes".getBytes();
        images = new Images(imageData);
    }


    @Test
    @DisplayName("Testing method save image")
    public void canSaveImage()
    {
        imagesRepository.save(images);
        assertNotNull(images.getId());
    }

    @Test
    @DisplayName("Testing method findLastImage")
    public void canFindLastImage()
    {
        //given
        canSaveImage();

        //when
        byte [] actualResult = imagesRepository.findLastImage();

        //then
        assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotfindLastImage")
    public void cannotFindLastImage()
    {

        //when
        byte [] actualResult = imagesRepository.findLastImage();

        //then
        assertNull(actualResult);
    }

    @Test
    @DisplayName("Testing method findAllImages")
    public void canFindAllImages()
    {
        //given
        canSaveImage();

        //when
        List<byte []> actualResult = imagesRepository.findAllImages();

        //then
        assertNotNull(actualResult);
    }

    @Test
    @DisplayName("Testing method cannotFindAllImages")
    public void cannotFindAllImages()
    {
        List<byte []> expectedResult = new ArrayList<byte[]> ();
        //when
        List<byte []> actualResult = imagesRepository.findAllImages();

        //then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Testing method getLastId")
    public void canGetLastId()
    {
        //given
        canSaveImage();

        //when
        int id  = imagesRepository.getLastId();

        //then
        assertNotNull(id);
    }
    
    @Test
    @DisplayName("Testing method getDataById")
    public void canGetDataById()
    {
        //given
        canSaveImage();
        int id  = imagesRepository.getLastId();

        //when
        byte [] data = imagesRepository.getDataById(id);

        //then
        assertNotNull(data);
    }


       @Test
    @DisplayName("Testing method cannotGetDataById")
    public void cannotGetDataById()
    {
        //given
        int id  = 1;

        //when
        byte [] data = imagesRepository.getDataById(id);

        //then
        assertNull(data);
    }

    @Test
    @DisplayName("Testing method canGetDataByNeqId")
    public void canGetDataByNeqId()
    {
        //given
        canSaveImage();
        int id  = imagesRepository.getLastId();

        //when
        List<byte [] > imagesData = imagesRepository.getDataNeqId(id);

        //then
        assertNotNull(imagesData);
    }


       @Test
    @DisplayName("Testing method cannotGetDataNeqId")
    public void cannotGetDataByNeqId()
    {
        //given
        List<byte []> expectedResult = new ArrayList<byte[]> ();
        int id  = 1;

        //when
        List<byte [] > actualResult = imagesRepository.getDataNeqId(id);

        //then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("Testing method getDataEqImageIds")
    public void canGetDataEqImageIds()
    {
        //given
        canSaveImage();
        ArrayList<Integer> arrayOfImageIds = new ArrayList<>();
        arrayOfImageIds.add(1);
        arrayOfImageIds.add(2);
        arrayOfImageIds.add(3);

        //when
        List <byte []> imagesData = imagesRepository.getDataEqImageIds(arrayOfImageIds);
        
        //then
        assertNotNull(imagesData);
    }

    @Test
    @DisplayName("Testing method cannot getDataEqImageIds")
    public void cannotGetDataEqImageIds()
    {
        //given
        List<byte []> expectedResult = new ArrayList<byte[]> ();
        ArrayList<Integer> arrayOfImageIds = new ArrayList<>();
        arrayOfImageIds.add(1);
        arrayOfImageIds.add(2);
        arrayOfImageIds.add(3);

        //when
        List <byte []> actualResult = imagesRepository.getDataEqImageIds(arrayOfImageIds);
        
        //then
        assertEquals(expectedResult, actualResult);
    }
}