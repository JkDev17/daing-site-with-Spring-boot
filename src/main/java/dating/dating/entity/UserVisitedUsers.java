package dating.dating.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserVisitedUsers 
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    int id;  
    int uId1;
    int uId2;
    Timestamp lastVisited;   

    public int getId() 
    {
        return this.id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public int getUId1() 
    {
        return this.uId1;
    }

    public void setUId1(int uId1) 
    {
        this.uId1 = uId1;
    }

    public int getUId2() 
    {
        return this.uId2;
    }

    public void setUId2(int uId2) 
    {
        this.uId2 = uId2;
    }

    public Timestamp getLastVisited() 
    {
        return this.lastVisited;
    }

    public void setLastVisited(Timestamp lastVisited) 
    {
        this.lastVisited = lastVisited;
    }

    public UserVisitedUsers(int uId1, int uId2, Timestamp lastVisited)
    {
        this.uId1 = uId1;
        this.uId2 = uId2;
        this.lastVisited = lastVisited;
    }

    @Override
    public String toString() 
    {
        return 
        "{" +
            " id='" + getId() + "'" +
            ", uId1='" + getUId1() + "'" +
            ", uId2='" + getUId2() + "'" +
            ", lastVisited='" + getLastVisited() + "'" +
        "}";
    }
}