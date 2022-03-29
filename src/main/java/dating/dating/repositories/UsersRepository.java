package dating.dating.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import dating.dating.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository <Users, Integer>
{
    public Optional<String> findByEmail(String email);

    @Query(value = "SELECT id FROM users WHERE email=?1", nativeQuery = true)
    public int getIdByEmail(String email);

    @Query(value = "SELECT fullname FROM users WHERE email=?1", nativeQuery = true)
    public String getFullNameByEmail(String email);

    @Query(value = "SELECT height FROM users WHERE email=?1", nativeQuery = true)
    public String getHeightByEmail(String email);

    @Query(value = "SELECT weight FROM users WHERE email=?1", nativeQuery = true)
    public String getWeightByEmail(String email);

    @Query(value = "SELECT job_title FROM users WHERE email=?1", nativeQuery = true)
    public String getJobByEmail(String email);

    @Query(value = "SELECT birthday FROM users WHERE email=?1", nativeQuery = true)
    public String getBdayByEmail(String email);

    @Query(value = "SELECT location FROM users WHERE email=?1", nativeQuery = true)
    public String getLocationByEmail(String email);

    @Query(value = "SELECT education_level FROM users WHERE email=?1", nativeQuery = true)
    public String getEducationByEmail(String email);

    @Query(value = "SELECT hair_color FROM users WHERE email=?1", nativeQuery = true)
    public String getHairColorByEmail(String email);

    @Query(value = "SELECT eye_color FROM users WHERE email=?1", nativeQuery = true)
    public String getEyeColorByEmail(String email);

    @Query(value = "SELECT hobbies FROM users WHERE email=?1", nativeQuery = true)
    public String getHobbiesByEmail(String email);

    @Query(value = "SELECT fullname FROM users WHERE id=?1", nativeQuery = true)
    public String getFullnameById(int id);

    @Query(value = "SELECT id FROM users WHERE fullname =?1", nativeQuery = true)
    public int getIdByFullname(String fullname);
    
    @Query(value = "SELECT * FROM users WHERE id != ?1", nativeQuery = true)
    public List<Users> getStarFromUsersNeqToId(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET is_prem = '1' WHERE email=?1", nativeQuery = true)
    public int updateIsPrem(String email);

    @Modifying
    @Query(value = "UPDATE users SET role = 'PREMIUM_USER' WHERE email=?1", nativeQuery = true)
    public int updateRole(String email);

    @Query("SELECT u.password FROM Users u WHERE email = ?1")
    public String findPasswordByEmail(String email);

    @Query(value = "SELECT is_prem FROM users WHERE email=?1", nativeQuery = true)
    public char userIsPrem(String email); 

    @Query(value = "SELECT id FROM users ORDER BY id DESC LIMIT 0,1", nativeQuery = true)
    public  int  getLastId();

    @Query(value = "SELECT * FROM users WHERE gender =?1 AND hair_color = ?2", nativeQuery = true)
    public  List<Users>  selecStartWithFilters1(String gender, String hairColor);

    @Query(value = "SELECT * FROM users WHERE gender =?1 ", nativeQuery = true)
    public  List<Users>  selecStartWithFilters2(String gender);

    @Query(value = "SELECT * FROM users WHERE hair_color =?1 ", nativeQuery = true)
    public  List<Users>  selecStartWithFilters3(String hair_color);
}