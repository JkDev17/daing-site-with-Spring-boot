package dating.dating.repositories;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import dating.dating.entity.UserVisitedUsers;

@Repository
public interface UserVisitedUsersRepository extends JpaRepository <UserVisitedUsers,Integer>
{
    @Query(value = "SELECT count(*)  FROM user_visited_users WHERE u_id2 = ?1", nativeQuery = true)
    public int getUsersVisitedUser(int uId2);

    @Query(value = "SELECT u_id1  FROM user_visited_users WHERE u_id2 = ?1", nativeQuery = true)
    public List<Integer> getUid1_VisitedUser(int uId2);

    @Query(value = "SELECT u_id1  FROM user_visited_users WHERE u_id2 = ?1 AND u_id1= ?2", nativeQuery = true)
    public Integer getUid1_VisitedUserUid2(int uId2, int uId1);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_visited_users  SET last_visited = ?3 WHERE u_id2 = ?1 AND u_id1 = ?2 ", nativeQuery = true)
    public Integer updateUid1_VisitedUserUid2(int uId2, int uId1, Timestamp timestamp);

    @Modifying
    @Transactional
    @Query(value = "DELETE * FROM user_visited_users", nativeQuery = true)
    void deleteStarUserVisitedUsers();
}