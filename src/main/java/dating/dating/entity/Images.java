package dating.dating.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Images implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    byte[] data;

    public int getId() 
    {
        return this.id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public byte[] getData() 
    {
        return this.data;
    }

    public void setData(byte[] data) 
    {
        this.data = data;
    }
    
    @Override
    public String toString() 
    {
        return "{" +
                " id='" + getId() + "'" +
                ", data='" + getData() + "'" +
                "}"; 
    }

    public Images(byte [] data)
    {
        this.data = data;
    }

    public Images() {}
}