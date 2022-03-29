package dating.dating;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import dating.dating.repositories.ImagesRepository;
import dating.dating.repositories.UserHasImagesRepository;
import dating.dating.repositories.UserVisitedUsersRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import dating.dating.repositories.UsersRepository;
import dating.dating.services.UsersServices;

@SpringBootTest
class DatingApplicationTests implements Serializable
{

	@Autowired
	UsersRepository userRepository;

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    UsersServices userService;

    @Autowired
    UserVisitedUsersRepository userVisitedUsersRepository;

    @Autowired
    UserHasImagesRepository userHasImagesRepository;
	/*@Test
	void testingfindPasswordByEmail()
	{
        char notPrem='0';
		char res=userRepository.userIsPrem("Danny@gmail.com");
		if(res==notPrem)
		{
			System.out.println("No password found based on this email.");
		}
		else
		System.out.println("Password is equal to "+ res);	
	}	


	
	@Test
	public void getAuthoriti() 
    {    
        String email="Danny@gmail.com";
        char res= userRepository.userIsPrem(email);
        char notPrem='0';
        GrantedAuthority authority;
        if(res==notPrem)
        {
            authority= new SimpleGrantedAuthority("ROLE_SIMPLE_USER"); 
            System.out.println("No prem");   
        }
        else
            {   
                authority = new SimpleGrantedAuthority("PREMIUM_USER");
                System.out.println("YES prem"); 
            }
        ArrayList <GrantedAuthority> authorities= new ArrayList <GrantedAuthority>();
        authorities.add(authority);
        System.out.println(authorities.toString());
        //return email;        
    }*/	

   /* @Test
    void testGetLastImage()
    {
        byte [] image = imagesRepository.findLastImage();
        System.out.println(Arrays.toString(image));
    }*/

   /* @Test
    public void testUpdateToPremium()
    {
        String email = "GretaMuller@gmail.com";
        int res = userRepository.updateIsPrem(email);
        System.out.println(res);
    }*/
    /*
    @Test
    public void testGetUsersVisitedUser()
    {
        List<Integer>  arrayListOfUids = new ArrayList<Integer> ();
        String email = "GretaMuller@gmail.com";
        int id = userRepository.getIdByEmail(email);
        arrayListOfUids = userVisitedUsersRepository.getUid1_VisitedUser(id);
        System.out.println(arrayListOfUids);
    }*/

    /*
    @Test
    public void testGetStarNeqToId()
    {
        HashMap <String,List<String>> map = new HashMap<String,List<String>>();
        ArrayList <String> arr = new ArrayList<String>();
        String email = "GretaMuller@gmail.com";
        int id = userRepository.getIdByEmail(email);
        List<Users> listOfSelectStarUsers = userRepository.getStarFromUsersNeqToId(id);
        for( Users list: listOfSelectStarUsers)
        {
           arr.add(list.getFullname());
        }
        map.put("fullname",arr);
        System.out.println(map);*/
        

        /*@Test
        public void checkUserVisitedUserFunctionality()
        {
            int id1 = 10000;
            int id2 = 10001;

            if(userVisitedUsersRepository.getUid1_VisitedUserUid2(id2, id1) == null)
            {
                System.out.println(userVisitedUsersRepository.getUid1_VisitedUserUid2(id2, id1));
            }
        }*/

        @Test
        public void checkIfNotInQueryWorks()
        {
            ArrayList<Integer> arr = new ArrayList<Integer>();
            ArrayList<Integer> arr1 = new ArrayList<Integer>();
            ArrayList<String> images = new ArrayList<String>();
            arr.add(7);
            arr.add(37);
            arr.add(9);
            arr1= userHasImagesRepository.getImageIdByUsersId(arr);
            System.out.println(arr1);
            List <byte[]> byteImg = imagesRepository.getDataEqImageIds(arr1);
            for (int i =0; i < byteImg.size(); i++)
            {
                images.add(new String (byteImg.get(i)));
            }
            System.out.println(images);
        } 
}