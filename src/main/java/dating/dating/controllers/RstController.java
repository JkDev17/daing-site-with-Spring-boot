package dating.dating.controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dating.dating.entity.Filters;
import dating.dating.entity.UserVisitedUsers;
import dating.dating.entity.Users;
import dating.dating.exceptions.ProfileNotFoundException;
import dating.dating.repositories.ImagesRepository;
import dating.dating.repositories.UserHasImagesRepository;
import dating.dating.repositories.UserVisitedUsersRepository;
import dating.dating.repositories.UsersRepository;
import dating.dating.services.UsersServices;

@RestController
public class RstController
{
    @Autowired
    UsersServices userService;

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserHasImagesRepository userHasImagesRepository;

    @Autowired
    UserVisitedUsersRepository userVisitedUsersRepository;
    
    @GetMapping(value="/checkDuplicateEmail")
    public String checkDuplicateEmail(@RequestParam("email") String email)
    {
        return userService.checkDuplicateEmail(email);
    }
    
    @PostMapping(value="/saveVarsToSession" , consumes = "application/json", produces="application/json")
    public Map<String, String> saveVarsToSession(@RequestBody Map<String, String> input , HttpServletRequest request) 
    {
        request.getSession().setAttribute("userEmailFromSignup", input.get("email"));
        request.getSession().setAttribute("userUnhashedPasswordFromSignup", input.get("userPassword"));
        request.getSession().setAttribute("userFullnameFromSignup", input.get("userFullname"));
        request.getSession().setAttribute("userAgeFromSignup", input.get("userAge"));
        return input;
    }

    @GetMapping(value="/getSessionData")
    public Map<String,String> getSessionData(HttpSession session)
    {
        HashMap<String, String> map = new HashMap<>();
        System.out.println(session.getId());
        String emailFromUser="";
        String fullnameFromUser="";
        String unhashedPasswordFromUser="";
        String ageFromUser="";

        if(session.getAttribute("userEmailFromSignup")==null)
        {
            emailFromUser="empty";
        }
        else
            emailFromUser=session.getAttribute("userEmailFromSignup").toString();


        if(session.getAttribute("userFullnameFromSignup")==null)
        {
            fullnameFromUser="empty";
        }
        else
            fullnameFromUser=session.getAttribute("userFullnameFromSignup").toString();
    

        if(session.getAttribute("userUnhashedPasswordFromSignup")==null)
        {
            unhashedPasswordFromUser="empty";
        }
        else
            unhashedPasswordFromUser=session.getAttribute("userUnhashedPasswordFromSignup").toString();


        if(session.getAttribute("userAgeFromSignup")==null  )
        {
            ageFromUser="empty";
        }    
        else
            ageFromUser=session.getAttribute("userAgeFromSignup").toString();


        map.put("email", emailFromUser);
        map.put("password", unhashedPasswordFromUser);   
        map.put("fullname", fullnameFromUser);
        map.put("age", ageFromUser);
        System.out.println("User :"+ fullnameFromUser+" has email: " + emailFromUser+ " and unhashedpassword: "+ unhashedPasswordFromUser+
                           " and user age is equal to:"+ ageFromUser);
        return map;
    }


    @PostMapping(value="/fetchUserPersonalDataIdEq2") //id equals to 2 => check js to remember
    public void fetchUserPersonalDataIdEq2(@RequestBody Map<String, String> input , HttpServletRequest request)
    {
        request.getSession().setAttribute("userGenderFromDatingUserPersonalData", input.get("userGender"));
        request.getSession().setAttribute("userBdayFromDatingUserPersonalData", input.get("userBday"));
        request.getSession().setAttribute("userEducationlevelFromDatingUserPersonalData", input.get("userEducationlevel"));
        request.getSession().setAttribute("userLocationFromDatingUserPersonalData", input.get("userLocation"));
        request.getSession().setAttribute("userJobFromDatingUserPersonalData", input.get("userJob"));
        request.getSession().setAttribute("userHobbiesFromDatingUserPersonalData", input.get("userHobbies"));
        System.out.println("User has gender :"+ input.get("userGender")+" and  has bday: " + input.get("userBday")
        + " and user has Educationlevel: "+ input.get("userEducationlevel")
        + " and user locates at:" +input.get("userLocation") +" and user works as:"+input.get("userJob") +" and user has hobbies such: "+ input.get("userHobbies"));
    } 

    @PostMapping(value="/fetchUserPersonalDataIdEq3") //id equals to 3 => check js to remember
    public void fetchUserPersonalDataIdEq3(@RequestBody Map<String, String> input , HttpServletRequest request)
    {
        request.getSession().setAttribute("userHeightFromDatingUserPersonalData", input.get("userHeight"));
        request.getSession().setAttribute("userWeightFromDatingUserPersonalData", input.get("userWeight"));
        request.getSession().setAttribute("userEyeFromDatingUserPersonalData", input.get("userEye"));
        request.getSession().setAttribute("userHairFromDatingUserPersonalData", input.get("userHair"));
        request.getSession().setAttribute("userSkinFromDatingUserPersonalData", input.get("userSkin"));
        System.out.println("User has height :"+ input.get("userHeight")+" and  has userWeight: " + input.get("userWeight")
        + " and user has eye color of: "+ input.get("userEye")
        + " and users hair is:" +input.get("userHair"));
    }

    @PostMapping(value="/fetchUserPersonalDataIdEq4") //id equals to 4 => check js to remember
    public void fetchUserPersonalDataIdEq4(@RequestBody Map<String, String> input , HttpServletRequest request)
    {
        request.getSession().setAttribute("mateGenderFromDatingUserPersonalData", input.get("mateGender"));
        request.getSession().setAttribute("rangeMinFromDatingUserPersonalData", input.get("rangeMin"));
        request.getSession().setAttribute("rangeMaxFromDatingUserPersonalData", input.get("rangeMax"));
        request.getSession().setAttribute("mateHeightFromDatingUserPersonalData", input.get("mateHeight"));
        request.getSession().setAttribute("mateWeightFromDatingUserPersonalData", input.get("mateWeight"));
        request.getSession().setAttribute("mateSkinFromDatingUserPersonalData", input.get("mateSkin"));
        System.out.println("User choose the gender of the mate to be :"+ input.get("mateGender")+" and  she/he has min age : " + input.get("rangeMin")
        + " and she/he  has max age: "+ input.get("rangeMax")
        + " and she/he has height of:" +input.get("mateHeight")
        + " and she/he has weight of:" + input.get("mateWeight")
        + " and she/he has skin color of:"+input.get("mateSkin"));
    }

    @PostMapping(value="/fetchUserPersonalDataProfilePic")
    public void fetchUserProfilePic(@RequestBody Map<String, String> input , HttpServletRequest request)
    {
        request.getSession().setAttribute("userProfilePic", input.get("dataImage"));
    }

    @GetMapping(value="/TestingImagesApi")
    public Map<String,String> TestingImagesApi(HttpSession session)
    {
        HashMap<String, String> map = new HashMap<>();
        String img="";
        if(session.getAttribute("userProfilePic")==null)
        {
            img="empty";
        }
        else
            img=session.getAttribute("userProfilePic").toString();
        map.put("userProfilePic", img);
        return map;
    }

    @PostMapping(value="/TestingImagesApi-v2")
    public Map<String,String> GetLastImage()
    {
        HashMap<String, String> map = new HashMap<>();
        byte [] image = imagesRepository.findLastImage();
        String bytesInStringFormat = new String(image);
        //String encodedString = Base64.getEncoder().encodeToString(image);
        //map.put("userProfilePic", Arrays.toString(image));
        map.put("userProfilePic", bytesInStringFormat);
        System.out.println(image.length+" Length size");
        return map;
    }

    @PostMapping(value="/TestingImagesApi-v3")
    public Map<String,List<String>> GetAllImages()
    {
        ArrayList <String> imagesList = new ArrayList <String>() ;
        HashMap<String, List<String>> map = new HashMap<>();
        List<byte[]> images = imagesRepository.findAllImages();
        for (int i =0; i < images.size(); i++)
        {
            imagesList.add(new String (images.get(i)));
        }
        map.put("userProfilePic", imagesList);
        return map;
    }

    @GetMapping(value="/saveDataToDatabase")
    public void saveDataToDatabase(HttpSession session) throws ParseException
    {
        userService.saveUserToDatabase(session);
    }

    @PostMapping(value="/sessionEmail",produces = {"application/json"},consumes = {"application/json"})
    public void fetchUserLogInEmail(@RequestBody Map<String, String> input , HttpServletRequest request)
    {
        for (Map.Entry<String, String> entry : input.entrySet()) 
        {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key: " +key+" value: "+value);
        }
        request.getSession().setAttribute("email", input.get("email"));
    }

    @GetMapping(value= "/fetchDataFromDatabase")
    public Map<String,String> fetchDataFromDatabase(HttpSession session)
    {
        HashMap<String,String> map = new HashMap<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String email = session.getAttribute("email").toString();
        String fullname = usersRepository.getFullNameByEmail(email);
        String bday = usersRepository.getBdayByEmail(email);
        int age = Integer.parseInt(bday.substring(0,4));
        age = year - age;
        String job = usersRepository.getJobByEmail(email);
        String location = usersRepository.getLocationByEmail(email);
        int idFromUsers = usersRepository.getIdByEmail(email); 
        int imageId = userHasImagesRepository.getImageIdByUserId(idFromUsers);
        byte [] img = imagesRepository.getDataById(imageId);
        String eyeColor = usersRepository.getEyeColorByEmail(email);
        String hairColor = usersRepository.getHairColorByEmail(email);
        String hobbies = usersRepository.getHobbiesByEmail(email);
        String educationlevel = usersRepository.getEducationByEmail(email);
        String image = new String (img);
        int id = usersRepository.getIdByEmail(email);
        int numOfUsersVisitedUser = userVisitedUsersRepository.getUsersVisitedUser(id);

        map.put("image",image);
        map.put("job",job);
        map.put("age",String.valueOf(age));
        map.put("fullname",fullname);
        map.put("location",location);
        map.put("hobbies",hobbies);
        map.put("hairColor",hairColor);
        map.put("eyeColor",eyeColor);
        map.put("educationlevel",educationlevel);
        map.put("numOfUsersVisitedUser",String.valueOf(numOfUsersVisitedUser));
        return map;
    }

    @GetMapping(value= "/fetchUserVisitedUsersList")
    public Map<String,List<String>> fetchUserVisitedUsersList(HttpSession session)
    {
        String name = "";
        HashMap<String,List<String>> map = new HashMap<>();
        List<Integer> arrayListOfUid1 = new ArrayList<Integer>();
        List<String> arrayListOfFullnames = new ArrayList<String>();
        String email = session.getAttribute("email").toString();
        int id = usersRepository.getIdByEmail(email);
        arrayListOfUid1 = userVisitedUsersRepository.getUid1_VisitedUser(id);
        for (int i =0; i< arrayListOfUid1.size(); i++)
        {
            name = usersRepository.getFullnameById(arrayListOfUid1.get(i));
            arrayListOfFullnames.add(name);
        }
        map.put("arrayListOfFullnames",arrayListOfFullnames);
        return map;
    }

    @GetMapping(value= "/fetchBasicDataFromDB")
    public Map<String,String> fetchBasicDataFromDB(HttpSession session)
    {
        HashMap<String,String> map = new HashMap<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String email = session.getAttribute("email").toString();
        String fullname = usersRepository.getFullNameByEmail(email);
        String bday = usersRepository.getBdayByEmail(email);
        int age = Integer.parseInt(bday.substring(0,4));
        age = year - age;
        String job = usersRepository.getJobByEmail(email);
        String location = usersRepository.getLocationByEmail(email);
        int idFromUsers = usersRepository.getIdByEmail(email); 
        int imageId = userHasImagesRepository.getImageIdByUserId(idFromUsers);
        byte [] img = imagesRepository.getDataById(imageId);
        String eyeColor = usersRepository.getEyeColorByEmail(email);
        String hairColor = usersRepository.getHairColorByEmail(email);
        String hobbies = usersRepository.getHobbiesByEmail(email);
        String educationlevel = usersRepository.getEducationByEmail(email);
        String image = new String (img);

        map.put("image",image);
        map.put("job",job);
        map.put("age",String.valueOf(age));
        map.put("fullname",fullname);
        map.put("location",location);
        map.put("hobbies",hobbies);
        map.put("hairColor",hairColor);
        map.put("eyeColor",eyeColor);
        map.put("educationlevel",educationlevel);
        return map;
    }

    @GetMapping(value= "/updateToPremium")
    public Map<String,Integer> updateToPremium(HttpSession session)
    {
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        String email = session.getAttribute("email").toString();
        int res = usersRepository.updateIsPrem(email);
        map.put("res",res);
        return map;
    }

    @GetMapping(value = "/fetchStarFromUsersNeqId")
    public HashMap<String, List<String>> fetchStarFromUsersNeqId(HttpSession session)
    {
        HashMap <String,List<String>> map = new HashMap <String,List<String>>();
        ArrayList<String> fullnames = new ArrayList<String>();
        ArrayList<String> jobs = new ArrayList<String>();
        ArrayList<String> ages = new ArrayList<String>();
        ArrayList<String> locations = new ArrayList<String>();
        ArrayList<String> hairColors  = new ArrayList<String>();
        ArrayList<String> eyeColors = new ArrayList<String>();
        ArrayList<String> hobbies = new ArrayList<String>();
        ArrayList<String> educationLevels = new ArrayList<String>();
        ArrayList<String> images = new ArrayList<String>();
        ArrayList<String> emails = new ArrayList<String>();
        String email = "";
        String bday = "";
        int age = 0;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        try
        {
            email = session.getAttribute("email").toString();
            int id = usersRepository.getIdByEmail(email);
            int imageIdToExclude = userHasImagesRepository.userHasImages(id);
            List <byte[]> byteImg = imagesRepository.getDataNeqId(imageIdToExclude);
            List<Users> listOfSelectStarUsers = usersRepository.getStarFromUsersNeqToId(id);
            for(Users list: listOfSelectStarUsers)
            {
                fullnames.add(list.getFullname().toString());
                jobs.add(list.getJobTitle().toString());
                bday = list.getBirthday().toString();
                age = Integer.parseInt(bday.substring(0, 4));
                age = year - age;
                ages.add(String.valueOf(age));
                locations.add(list.getLocation().toString());
                hairColors.add(list.getHairColor().toString());
                eyeColors.add(list.getEyeColor().toString());
                hobbies.add(list.getHobbies().toString());
                educationLevels.add(list.getEducationLevel().toString());
            }
            for (int i =0; i < byteImg.size(); i++)
            {
                images.add(new String (byteImg.get(i)));
            }
        }
        catch(NullPointerException exception)
        {
            throw new ProfileNotFoundException();
        }

        emails.add(email);
        map.put("email",emails);
        map.put("images", images);
        map.put("fullnames",fullnames);
        map.put("jobs",jobs);
        map.put("ages",ages);
        map.put("locations",locations);
        map.put("hairColors",hairColors);
        map.put("eyeColors",eyeColors);
        map.put("hobbies",hobbies);
        map.put("educationLevels",educationLevels);
        return map;
    }

    @GetMapping(value = "/updateUserVisitedUser")
    public void userVisitedUser(HttpSession session, HttpServletRequest request)   
    {
        int id1=0,id2=0;
        try
        {
            String email = session.getAttribute("email").toString();
            id1 = usersRepository.getIdByEmail(email);
            id2 = usersRepository.getIdByFullname(request.getParameter("fullname").toString());
            
            if(userVisitedUsersRepository.getUid1_VisitedUserUid2(id2, id1) == null)
            {
                Date date = new Date();
                Timestamp timestamp = new Timestamp(date.getTime());
                userVisitedUsersRepository.saveAndFlush(new UserVisitedUsers(id1, id2, timestamp));
            }
            else
            {
                Date date = new Date();
                Timestamp timestamp = new Timestamp(date.getTime());
                userVisitedUsersRepository.updateUid1_VisitedUserUid2(id2, id1, timestamp);
            }
        }
        catch(NullPointerException exception)
        {
            throw new ProfileNotFoundException();
        }
    } 

    @PostMapping(value="/FilterProfiles")
    public HashMap<String,List<String>> filterProfiles(@RequestBody Filters  filter,HttpSession session, HttpServletRequest request)
    {
        HashMap<String,List<String>> map = new HashMap<String,List<String>>();
        ArrayList<String> fullnames = new ArrayList<String>();
        ArrayList<String> jobs = new ArrayList<String>();
        ArrayList<String> ages = new ArrayList<String>();
        ArrayList<String> locations = new ArrayList<String>();
        ArrayList<String> hairColors  = new ArrayList<String>();
        ArrayList<String> eyeColors = new ArrayList<String>();
        ArrayList<String> hobbies = new ArrayList<String>();
        ArrayList<String> educationLevels = new ArrayList<String>();
        ArrayList<String> images = new ArrayList<String>();
        ArrayList<Integer> imagesId = new ArrayList<Integer>();
        ArrayList<String> emails = new ArrayList<String>();
        ArrayList<Integer> usersId = new ArrayList<Integer>();
        String bday = "";
        String email= "";
        String hairColor = "";
        String gender = "";
        int age = 0;
        int year = Calendar.getInstance().get(Calendar.YEAR);

        try
        {
            gender = filter.getgenderFilter();
            hairColor = filter.gethairFilter();
            email = session.getAttribute("email").toString();
        }
        catch(NullPointerException exception)
        {
            throw new ProfileNotFoundException("parameters not found to filter with");
        }

        int idToExclude = usersRepository.getIdByEmail(email);
        emails.add(email);
        if(!"empty".equals(hairColor) && !"empty".equals(gender))
        {
            List<Users> listOfSelectStarUsers = usersRepository.selecStartWithFilters1(gender, hairColor,idToExclude);

            for(Users list: listOfSelectStarUsers)
            {
                fullnames.add(list.getFullname().toString());
                jobs.add(list.getJobTitle().toString());
                bday = list.getBirthday().toString();
                age = Integer.parseInt(bday.substring(0, 4));
                age = year - age;
                ages.add(String.valueOf(age));
                locations.add(list.getLocation().toString());
                hairColors.add(list.getHairColor().toString());
                eyeColors.add(list.getEyeColor().toString());
                hobbies.add(list.getHobbies().toString());
                educationLevels.add(list.getEducationLevel().toString());
            }

            for(int i=0; i<fullnames.size(); i++)
            {
               usersId.add(usersRepository.getIdByFullname(fullnames.get(i)));
            }

            imagesId = userHasImagesRepository.getImageIdByUsersId(usersId);
            List <byte[]> byteImg = imagesRepository.getDataEqImageIds(imagesId);
            for (int i =0; i < byteImg.size(); i++)
            {
                images.add(new String (byteImg.get(i)));
            }

            map.put("email",emails);
            map.put("images", images);
            map.put("fullnames",fullnames);
            map.put("jobs",jobs);
            map.put("ages",ages);
            map.put("locations",locations);
            map.put("hairColors",hairColors);
            map.put("eyeColors",eyeColors);
            map.put("hobbies",hobbies);
            map.put("educationLevels",educationLevels);
        }

        else if (!"empty".equals(gender))
        {
            List<Users> listOfSelectStarUsers = usersRepository.selecStartWithFilters2(gender, idToExclude);
            for(Users list: listOfSelectStarUsers)
            {
                fullnames.add(list.getFullname().toString());
                jobs.add(list.getJobTitle().toString());
                bday = list.getBirthday().toString();
                age = Integer.parseInt(bday.substring(0, 4));
                age = year - age;
                ages.add(String.valueOf(age));
                locations.add(list.getLocation().toString());
                hairColors.add(list.getHairColor().toString());
                eyeColors.add(list.getEyeColor().toString());
                hobbies.add(list.getHobbies().toString());
                educationLevels.add(list.getEducationLevel().toString());
            }
            for(int i=0; i<fullnames.size(); i++)
            {
               usersId.add(usersRepository.getIdByFullname(fullnames.get(i)));
            }
            imagesId = userHasImagesRepository.getImageIdByUsersId(usersId);
            List <byte[]> byteImg = imagesRepository.getDataEqImageIds(imagesId);
            for (int i =0; i < byteImg.size(); i++)
            {
                images.add(new String (byteImg.get(i)));
            }

            map.put("email",emails);
            map.put("images", images);
            map.put("fullnames",fullnames);
            map.put("jobs",jobs);
            map.put("ages",ages);
            map.put("locations",locations);
            map.put("hairColors",hairColors);
            map.put("eyeColors",eyeColors);
            map.put("hobbies",hobbies);
            map.put("educationLevels",educationLevels);
        }

        else if (!"empty".equals(hairColor))
        {
            List<Users> listOfSelectStarUsers = usersRepository.selecStartWithFilters3(hairColor, idToExclude);
            for(Users list: listOfSelectStarUsers)
            {
                fullnames.add(list.getFullname().toString());
                jobs.add(list.getJobTitle().toString());
                bday = list.getBirthday().toString();
                age = Integer.parseInt(bday.substring(0, 4));
                age = year - age;
                ages.add(String.valueOf(age));
                locations.add(list.getLocation().toString());
                hairColors.add(list.getHairColor().toString());
                eyeColors.add(list.getEyeColor().toString());
                hobbies.add(list.getHobbies().toString());
                educationLevels.add(list.getEducationLevel().toString());
            }
            for(int i=0; i<fullnames.size(); i++)
            {
               usersId.add(usersRepository.getIdByFullname(fullnames.get(i)));
            }
            imagesId = userHasImagesRepository.getImageIdByUsersId(usersId);
            List <byte[]> byteImg = imagesRepository.getDataEqImageIds(imagesId);
            for (int i =0; i < byteImg.size(); i++)
            {
                images.add(new String (byteImg.get(i)));
            }

            map.put("email",emails);
            map.put("images", images);
            map.put("fullnames",fullnames);
            map.put("jobs",jobs);
            map.put("ages",ages);
            map.put("locations",locations);
            map.put("hairColors",hairColors);
            map.put("eyeColors",eyeColors);
            map.put("hobbies",hobbies);
            map.put("educationLevels",educationLevels);
        }

        else
        {
            ArrayList<String> errArr = new ArrayList<String>();
            errArr.add("Could not find any filtering arguments");
            map.put("error",errArr);
        }
        
        return map;
    }   

    @GetMapping(value = "/fetchOnePersonVisited")
    public Map<String,String> fetchOnePersonVisited(HttpSession session)
    {
        HashMap<String,String> map = new HashMap<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);

        try
        {
            String fullname = session.getAttribute("fullnamePerson").toString();
            String userLoggedInFullname = usersRepository.getFullNameByEmail(session.getAttribute("email").toString());
            String email = usersRepository.getEmailByFullname(fullname);
            String bday = usersRepository.getBdayByEmail(email);
            int age = Integer.parseInt(bday.substring(0,4));
            age = year - age;
            String job = usersRepository.getJobByEmail(email);
            String location = usersRepository.getLocationByEmail(email);
            int idFromUsers = usersRepository.getIdByEmail(email); 
            int imageId = userHasImagesRepository.getImageIdByUserId(idFromUsers);
            byte [] img = imagesRepository.getDataById(imageId);
            String eyeColor = usersRepository.getEyeColorByEmail(email);
            String hairColor = usersRepository.getHairColorByEmail(email);
            String hobbies = usersRepository.getHobbiesByEmail(email);
            String educationlevel = usersRepository.getEducationByEmail(email);
            String image = new String (img);

            map.put("image",image);
            map.put("job",job);
            map.put("age",String.valueOf(age));
            map.put("userLoggedInFullname",userLoggedInFullname);
            map.put("fullname",fullname);
            map.put("location",location);
            map.put("hobbies",hobbies);
            map.put("hairColor",hairColor);
            map.put("eyeColor",eyeColor);
            map.put("educationlevel",educationlevel);
        }
        catch(NullPointerException exception)
        {
            throw new ProfileNotFoundException("Profile not found");
        }
        return map;
    }
}