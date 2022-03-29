package dating.dating.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


@Entity
@Component
@Table
(   name="users",
    uniqueConstraints=@UniqueConstraint
    (
        name="email", columnNames = "email"
    )
)

public class Users implements Serializable 
{
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false)
    private String educationLevel;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String hobbies;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String hairColor;

    @Column(nullable = false)
    private String skinColor;

    @Column(nullable = false)
    private String eyeColor;

    @Column(nullable = false)
    private char isPrem;

    @Column(nullable = false)
    private boolean enabled;


    @Column(nullable = false)
    private String role;


    @javax.persistence.Transient
    private Collection<? extends GrantedAuthority> authorities;

    public Collection<?extends GrantedAuthority> getAuthorities() 
    {
        return this.authorities;
    }


    public void setAuthorities(Collection<?extends GrantedAuthority> authorities) 
    {
        this.authorities = authorities;
    }

   public Users( String email, String fullname, String password, String gender, Date birthday, String educationLevel, String jobTitle,
                                                                 String location, String hobbies, Double height, Double weight,
                                                                 String hairColor, String skinColor, String eyeColor,
                                                                 char isPrem,boolean enabled,String role) 
    {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.educationLevel = educationLevel;
        this.jobTitle = jobTitle;
        this.location = location;
        this.hobbies = hobbies;
        this.height = height;
        this.weight = weight;
        this.hairColor = hairColor;
        this.skinColor = skinColor;
        this.eyeColor = eyeColor;
        this.isPrem = isPrem;
        this.enabled=enabled;
        this.role=role;
    }
    
    public Users() {}
    
    public int getId() 
    {
        return this.id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getEmail() 
    {
        return this.email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getFullname() 
    {
        return this.fullname;
    }

    public void setFullname(String fullname) 
    {
        this.fullname = fullname;
    }

    public String getPassword() 
    {
        return this.password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getGender() 
    {
        return this.gender;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public Date getBirthday() 
    {
        return this.birthday;
    }

    public void setBirthday(Date birthday) 
    {
        this.birthday = birthday;
    }

    public String getEducationLevel() 
    {
        return this.educationLevel;
    }

    public void setEducationLevel(String educationLevel) 
    {
        this.educationLevel = educationLevel;
    }

    public String getJobTitle() 
    {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) 
    {
        this.jobTitle = jobTitle;
    }

    public String getLocaltion() 
    {
        return this.location;
    }

    public String getHobbies() 
    {
        return this.hobbies;
    }

    public void setHobbies(String hobbies) 
    {
        this.hobbies = hobbies;
    }

    public Double getHeight() 
    {
        return this.height;
    }

    public void setHeight(Double height) 
    {
        this.height = height;
    }

    public Double getWeight() 
    {
        return this.weight;
    }

    public void setWeight(Double weight) 
    {
        this.weight = weight;
    }

    public String getHairColor() 
    {
        return this.hairColor;
    }

    public void setHairColor(String hairColor) 
    {
        this.hairColor = hairColor;
    }

    public String getSkinColor() 
    {
        return this.skinColor;
    }

    public void setSkinColor(String skinColor) 
    {
        this.skinColor = skinColor;
    }

    public String getEyeColor() 
    {
        return this.eyeColor;
    }

    public void setEyeColor(String eyeColor) 
    {
        this.eyeColor = eyeColor;
    }

    public char isIsPrem() 
    {
        return this.isPrem;
    }

    public char getIsPrem() 
    {
        return this.isPrem;
    }

    public void setIsPrem(char isPrem) 
    {
        this.isPrem = isPrem;
    }   
    
    public boolean getEnabled() 
    {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) 
    {
        this.enabled = enabled;
    }


    public String getRole() 
    {
        return this.role;
    }

    public void setRole(String role) 
    {
        this.role = role;
    }

    public String getLocation() 
    {
        return this.location;
    }

    public void setLocation(String location) 
    {
        this.location = location;
    }

    @Override
    public String toString() 
    {
        return "{" +
            " id='" + getId() + "'" +
            ", email='" + getEmail() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", password='" + getPassword() + "'" +
            ", gender='" + getGender() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", educationLevel='" + getEducationLevel() + "'" +
            ", jobTitle='" + getJobTitle() + "'" +
            ", localtion='" + getLocaltion() + "'" +
            ", hobbies='" + getHobbies() + "'" +
            ", height='" + getHeight() + "'" +
            ", weight='" + getWeight() + "'" +
            ", hairColor='" + getHairColor() + "'" +
            ", skinColor='" + getSkinColor() + "'" +
            ", eyeColor='" + getEyeColor() + "'" +
            ", isPrem='" + isIsPrem() + "'" +
            "}";
    }
} 