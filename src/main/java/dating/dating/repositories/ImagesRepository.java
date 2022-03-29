package dating.dating.repositories;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import dating.dating.entity.Images;

@Repository
public interface ImagesRepository extends JpaRepository <Images, Integer>
{
    @Query(value = "SELECT data FROM images ORDER BY id DESC LIMIT 0,1", nativeQuery = true)
    public  byte [] findLastImage();

    @Query(value = "SELECT data FROM images ORDER BY id DESC LIMIT 0,1", nativeQuery = true)
    public  List<byte[]>  findAllImages();

    @Query(value = "SELECT id FROM images ORDER BY id DESC LIMIT 0,1", nativeQuery = true)
    public  int  getLastId();

    @Query(value = "SELECT data FROM images WHERE id = ?1", nativeQuery = true)
    public  byte []  getDataById(int imageId);

    @Query(value = "SELECT data FROM images WHERE id != ?1", nativeQuery = true)
    public  List<byte[]>   getDataNeqId(int imageId);

    @Query(value = "SELECT data FROM images WHERE id IN (?1)", nativeQuery = true)
    public  List<byte[]>   getDataEqImageIds(ArrayList<Integer> imageId);
} 