package dating.dating.repositories;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import dating.dating.entity.UserHasImages;

@Repository
public interface UserHasImagesRepository extends  JpaRepository <UserHasImages, Integer>
{
    @Query(value = "SELECT image_id FROM user_has_images WHERE user_id=?1 AND  is_profile_pic = '1'", nativeQuery = true)
    public int getImageIdByUserId(int id);

    @Query(value = "SELECT image_id FROM user_has_images WHERE user_id = ?1", nativeQuery = true)
    public  int   userHasImages(int userId);

    
    @Query(value = "SELECT image_id FROM user_has_images WHERE user_id IN (?1)", nativeQuery = true)
    public  ArrayList<Integer>   getImageIdByUsersId(ArrayList<Integer> usersId);
}
