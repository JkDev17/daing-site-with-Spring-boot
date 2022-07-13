package dating.dating.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserHasImages 
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int imageId;
    private char isProfilePic;

    public int getId() 
    {
        return this.id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public int getUserId() 
    {
        return this.userId;
    }

    public void setUserId(int userId) 
    {
        this.userId = userId;
    }

    public int getImageId() 
    {
        return this.imageId;
    }

    public void setImageId(int imageId) 
    {
        this.imageId = imageId;
    }

    public char getIsProfilePic() 
    {
        return this.isProfilePic;
    }

    public void setIsProfilePic(char isProfilePic) 
    {
        this.isProfilePic = isProfilePic;
    }

    public UserHasImages(int userId, int imageId, char isProfilePic)
    {
        this.userId = userId;
        this.imageId = imageId;
        this.isProfilePic = isProfilePic;
    }
    
    public UserHasImages() {}

    @Override
    public String toString() 
    {
        return "{" +
            " id='" + getId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", imageId='" + getImageId() + "'" +
            ", isProfilePic='" + getIsProfilePic() + "'" +
            "}";
    }
}