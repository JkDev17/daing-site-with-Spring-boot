package dating.dating.entity;

import org.springframework.stereotype.Component;

@Component
public class Filters 
{
    String  genderFilter;
    String hairFilter;
    String eyeColor;
    double height;
    double weight;
    int age;    

    public Filters(String genderFilter, String hairFilter, String eyeColor, double height, double weight, int age) 
    {
        this.genderFilter = genderFilter;
        this.hairFilter = hairFilter;
        this.eyeColor = eyeColor;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public Filters()
    {
        ;
    }
    
    public String getgenderFilter() 
    {
        return this.genderFilter;
    }

    public void setgenderFilter(String genderFilter) 
    {
        this.genderFilter = genderFilter;
    }

    public String gethairFilter() 
    {
        return this.hairFilter;
    }

    public void sethairFilter(String hairFilter) 
    {
        this.hairFilter = hairFilter;
    }

    public String getEyeColor() 
    {
        return this.eyeColor;
    }

    public void setEyeColor(String eyeColor) 
    {
        this.eyeColor = eyeColor;
    }

    public double getHeight() 
    {
        return this.height;
    }

    public void setHeight(double height) 
    {
        this.height = height;
    }

    public double getWeight() 
    {
        return this.weight;
    }

    public void setWeight(double weight) 
    {
        this.weight = weight;
    }

    public int getAge() 
    {
        return this.age;
    }

    public void setAge(int age) 
    {
        this.age = age;
    }

    @Override
    public String toString() 
    {
        return "{" +
            " genderFilter='" + getgenderFilter() + "'" +
            ", hairFilter='" + gethairFilter() + "'" +
            ", eyeColor='" + getEyeColor() + "'" +
            ", height='" + getHeight() + "'" +
            ", weight='" + getWeight() + "'" +
            ", age='" + getAge() + "'" +
            "}";
    }
}
