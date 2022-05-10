package dating.dating.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import dating.dating.entity.Users;
import dating.dating.exceptions.ProfileNotFoundException;
import dating.dating.entity.Filters;
import dating.dating.entity.Images;
import dating.dating.entity.UserHasImages;
import dating.dating.entity.UserVisitedUsers;
import dating.dating.repositories.ImagesRepository;
import dating.dating.repositories.UserHasImagesRepository;
import dating.dating.repositories.UserVisitedUsersRepository;
import dating.dating.repositories.UsersRepository;

@Service
public class UsersServices
{
    private final UsersRepository usersRepository;

    private final UserHasImagesRepository userHasImagesRepository;

    private final ImagesRepository imagesRepository;

    private final UserVisitedUsersRepository userVisitedUsersRepository;

    UsersServices(UsersRepository usersRepository,UserHasImagesRepository userHasImagesRepository, ImagesRepository imagesRepository,
                  UserVisitedUsersRepository userVisitedUsersRepository)
    {
        this.usersRepository = usersRepository;
        this.userHasImagesRepository = userHasImagesRepository;
        this.imagesRepository = imagesRepository;
        this.userVisitedUsersRepository = userVisitedUsersRepository;
    }

    Logger LOGGER = LoggerFactory.getLogger(UsersServices.class);

    public void saveVarsToSession(Map<String, String> input , HttpServletRequest request)
    {
        request.getSession().setAttribute("userEmailFromSignup", input.get("email"));
        request.getSession().setAttribute("userUnhashedPasswordFromSignup", input.get("userPassword"));
        request.getSession().setAttribute("userFullnameFromSignup", input.get("userFullname"));
        request.getSession().setAttribute("userAgeFromSignup", input.get("userAge"));
    }

    public HashMap<String,String> serveDatatoGetSession(HttpSession session)
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
    
    public void fetchUserPersonalDataPage2(Map<String, String> input , HttpServletRequest request)
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

    public void fetchUserPersonalDataPage3(Map<String, String> input , HttpServletRequest request)
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

    public void fetchUserPersonalDataPage4(Map<String, String> input , HttpServletRequest request)
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

    public void saveUsersProfilePicToSessionData(Map<String, String> input , HttpServletRequest request)
    {
        request.getSession().setAttribute("userProfilePic", input.get("dataImage"));
        System.out.println("SAVING USERS PROFILE PIC");
    }


    public void fetchUserEmail(Map<String, String> input , HttpServletRequest request)
    {
        System.out.println("ENTERING fetchUserEmail!!!!!!!");
        for (Map.Entry<String, String> entry : input.entrySet()) 
        {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key: " +key+" value: "+value);
        }
        request.getSession().setAttribute("email", input.get("email"));
        System.out.println("EXITING fetchUserEmail!!!!!!!!");
    }

    public HashMap<String,String> fetchDataForProfileWhoLoggedIn(HttpSession session)
    {
        System.out.println("ENTERING fetchDataForProfileWhoLoggedIn!!!!!!!!!");
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
        System.out.println("EXITING fetchDataForProfileWhoLoggedIn!!!!!!!!!");
        return map;
    }

    public HashMap<String,List<String>> getUserVisitedUsersList(HttpSession session)
    {
        System.out.println("ENTERING getUserVisitedUsersList!!!!!!!!!");
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
        System.out.println("EXITING getUserVisitedUsersList!!!!!!!!!");
        return map;
    }

    public HashMap<String,String> getBasicDataFromUsers(HttpSession session)
    {
        System.out.println("ENTERING getBasicDataFromUsers!!!!!!!!!");
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
        System.out.println("EXITING getBasicDataFromUsers!!!!!!!!!");
        return map;
    }

    public HashMap<String,Integer> userUpdatesToPremium(HttpSession session)
    {
        System.out.println("ENTERING userUpdatesToPremium!!!!!!!!!");
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        String email = session.getAttribute("email").toString();
        int res = usersRepository.updateIsPrem(email);
        map.put("res",res);
        return map;
    }

    public HashMap<String, List<String>> getStarFromUsersNeqId(HttpSession session)
    {
        System.out.println("ENTERING getStarFromUsersNeqId!!!!!!!!!");
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
            LOGGER.error("Session variables returning null at: "+ exception.getStackTrace()[0].getLineNumber() + " at file RstController.java");
            throw new ProfileNotFoundException("Null values found at method getStarFromUsersNeqId");
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
        System.out.println("EXITING getStarFromUsersNeqId!!!!!!!!!");
        return map;
    }

    public void updateUserVisitedUser(HttpSession session, HttpServletRequest request)
    {
        System.out.println("ENTERING updateUserVisitedUser!!!!!!!!!");
        String email = "";
        int id1=0,id2=0;
         if( session.getAttribute("email") == null && session.getAttribute("userEmailFromSignup") == null)
         {
            LOGGER.error("Session variables returning null at file RstController.java");
            throw new ProfileNotFoundException();
         }

        else if( session.getAttribute("email") == null)
            email = session.getAttribute("userEmailFromSignup").toString();
            
        else
            email = session.getAttribute("email").toString();
        
        try
        {
            id1 = usersRepository.getIdByEmail(email);
            id2 = usersRepository.getIdByFullname(request.getParameter("fullname").toString());
        }
        catch(NullPointerException exception)
        {
            LOGGER.error("Session variables returning null at file RstController.java");
            throw new ProfileNotFoundException("Null at session | request variables");
        }
        if(userVisitedUsersRepository.getUid1_VisitedUserUid2(id2, id1) == null)
        {
            java.util.Date date = new java.util.Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            userVisitedUsersRepository.saveAndFlush(new UserVisitedUsers(id1, id2, timestamp));
        }
        else
        {
            java.util.Date date = new java.util.Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            userVisitedUsersRepository.updateUid1_VisitedUserUid2(id2, id1, timestamp);
        }
        System.out.println("EXITING updateUserVisitedUser!!!!!!!!!");
    }

    public HashMap<String,List<String>> filterProfiles( Filters filters,HttpSession session, HttpServletRequest request)
    {
        System.out.println("ENTERING filterProfiles!!!!!!!!!");
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
            gender = filters.getgenderFilter();
            hairColor = filters.gethairFilter();
            email = session.getAttribute("email").toString();
        }
        catch(NullPointerException exception)
        {
            LOGGER.error("Session variables returning null at: "+ exception.getStackTrace()[0].getLineNumber() + " at file RstController.java");
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

        System.out.println("EXITING filterProfiles!!!!!!!!!");
        return map;
    }

    public HashMap<String,String> fetchOnePersonVisited(HttpSession session)
    {
        System.out.println("ENTERING fetchOnePersonVisited!!!!!!!!!!");
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
            LOGGER.error("Session variables returning null at: "+ exception.getStackTrace()[0].getLineNumber() + " at file RstController.java");
            throw new ProfileNotFoundException("Profile not found");
        }
        System.out.println("EXITING fetchOnePersonVisited!!!!!!!!!!");
        return map;
    }

    public HashMap<String,List<String>> matchingProfiles (HttpSession session)
    {
        System.out.println("ENTERING matchingProfiles!!!!!!!!!!");
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
        ArrayList<Integer> imagesId = new ArrayList<Integer>();
        ArrayList<Integer> usersId = new ArrayList<Integer>();
        List<Users> users = new ArrayList<Users>();
        String email = "";
        String usersLocation= "";
        
        try
        {
            email = session.getAttribute("userEmailFromSignup").toString();
            session.setAttribute("email", email);
            int idToExclude = usersRepository.getIdByEmail(email);
            usersLocation = usersRepository.getLocationByEmail(email);
            users = usersRepository.getStarFromUsersNeqToId(idToExclude);
        }
        catch(NullPointerException exception)
        {
            LOGGER.error("Session variables returning null at: "+ exception.getStackTrace()[0].getLineNumber() + " at file RstController.java");
            throw new ProfileNotFoundException("Session variables returning null");
        }
        String bday = "";
        int age= 0;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String mateSkinColor = "";
        String mateGender = "";
        int mateAgeUpperLimit = 0;
        int mateLowerAgeLowerLimit = 0;
        double mateHeight =0.0;
        double mateWeight = 0.0;
        try
        {
            mateSkinColor = session.getAttribute("mateSkinFromDatingUserPersonalData").toString();
            mateGender = session.getAttribute("mateGenderFromDatingUserPersonalData").toString();
            mateAgeUpperLimit = Integer.parseInt(session.getAttribute("rangeMaxFromDatingUserPersonalData").toString());
            mateLowerAgeLowerLimit  = Integer.parseInt(session.getAttribute("rangeMinFromDatingUserPersonalData").toString());
            mateHeight = Double.parseDouble(session.getAttribute("mateHeightFromDatingUserPersonalData").toString());
            mateWeight = Double.parseDouble(session.getAttribute("mateWeightFromDatingUserPersonalData").toString());
        }
        catch(NullPointerException exception)
        {
            LOGGER.error("Session variables returning null at: "+ exception.getStackTrace()[0].getLineNumber() + " at file RstController.java");
            throw new ProfileNotFoundException("Session variables returning null");
        }
        System.out.println("User is:"+ email);
        System.out.println("User wanted his mate to be the gender of:"+ mateGender);
        System.out.println("User wanted his mate to be of color:" + mateSkinColor);
        System.out.println("User wanted his mate to be of age between:"+ mateLowerAgeLowerLimit + " and "+ mateAgeUpperLimit);
        System.out.println("User wanted his mate to be in kg:"+ mateWeight);
        System.out.println("User wanted his mate to be in cm"+ mateHeight);
        System.out.println(users.size()+ " is the size of our list of users");
        System.out.println(usersLocation);
        for( int i =0; i<users.size(); i++)
        {
            if(!users.get(i).getGender().equals(mateGender))
            {
                System.out.println(i);
                System.out.println(users.get(i).getFullname() + " is about to be removed");
                users.remove(i);
                i--;
            }
            else
            {
                System.out.println(i);
                System.out.println("User: " + users.get(i).getFullname() +" is not getting removed with gender:"+users.get(i).getGender());
            }
        }
        
        for(int i =0; i<users.size();i++)
        {
            if(users.get(i).getLocation().equals(usersLocation))
            {
                System.out.println("I am "+ users.get(i).getFullname() + " and I am in location "+ users.get(i).getLocation());
                users.get(i).updateMatchingScore(10);
            }

            if(users.get(i).getSkinColor().equals(mateSkinColor))
            {
                System.out.println("I am "+ users.get(i).getFullname() + " and I have "+ users.get(i).getSkinColor() + " skin color");
                users.get(i).updateMatchingScore(5);
            }

            bday = usersRepository.getBdayByEmail(users.get(i).getEmail().toString());
            age = Integer.parseInt(bday.substring(0,4));
            age = year - age;
            if(age <= mateAgeUpperLimit && age >= mateLowerAgeLowerLimit )
            {
                System.out.println("I am "+ users.get(i).getFullname() + " and my age is:"+ age+ " with mateAgeUpperLimit:"+ mateAgeUpperLimit + " and mateLowerAgeLowerLimit:"+ mateLowerAgeLowerLimit);
                users.get(i).updateMatchingScore(4);
            }

            double matesWeight = Double.parseDouble(users.get(i).getWeight().toString());
            double matesHeight = Double.parseDouble(users.get(i).getHeight().toString());
            if( (matesWeight <=mateWeight+5 && matesWeight>= mateWeight-5 ) && (matesHeight<= mateHeight+5 && matesHeight>= mateHeight-5) )
            {
                System.out.println("I am "+ users.get(i).getFullname() + " and my weight is:"+ users.get(i).getWeight()+ " and my height is:"+users.get(i).getHeight());
                users.get(i).updateMatchingScore(3);
            } 
        }

        Collections.sort(users, Comparator.comparingInt(Users ::getMatchingScore).reversed());
        for(Users user: users)
        {
            System.out.println("The user: "+user.getFullname()+" has matching score of:" + user.getMatchingScore());
        }

        for(Users user: users)
        {
            if(user.getMatchingScore() ==0)
            {
                continue;
            }
            fullnames.add(user.getFullname());
            jobs.add(user.getJobTitle().toString());
            bday = user.getBirthday().toString();
            age = Integer.parseInt(bday.substring(0, 4));
            age = year - age;
            ages.add(String.valueOf(age));
            locations.add(user.getLocation().toString());
            hairColors.add(user.getHairColor().toString());
            eyeColors.add(user.getEyeColor().toString());
            hobbies.add(user.getHobbies().toString());
            educationLevels.add(user.getEducationLevel().toString());
        }

        System.out.println("Number of fullnames are:" +fullnames.size());
        for(int i=0; i<fullnames.size(); i++)
        {
           usersId.add(usersRepository.getIdByFullname(fullnames.get(i)));
           System.out.println(""+ fullnames.get(i) + " has user id:" + usersRepository.getIdByFullname(fullnames.get(i)));
        }

        for(int i=0; i<usersId.size(); i++ )
        {
            int userId = userHasImagesRepository.getImageIdByUserId(usersId.get(i));
            imagesId.add(userId);
        }
        System.out.println("UsersId are:" + usersId);
        System.out.println("ImagesId are:" + imagesId);
        System.out.println("ImagesId are:" + imagesId.size());
        List <byte[]> byteImg = new ArrayList<byte[]> ();
        for(int i=0; i<imagesId.size();i++)
        {
            byteImg.add(imagesRepository.getDataById(imagesId.get(i)));
        }
        
        System.out.println("I have "+ byteImg.size()+ " IMAGES");
        for (int i =0; i < byteImg.size(); i++)
        {
            images.add(new String (byteImg.get(i)));
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
        System.out.println("EXITING matchingProfiles!!!!!!!!!!");
        return map; 
    }

    public String checkDuplicateEmail(String email)
    {
        Optional <String> u=usersRepository.findByEmail(email);
        if(u.isPresent())
        {
            String res="taken";
            return res;
        }
        return "new";
    }

    public char getUserRole(String email)
    {
        return usersRepository.userIsPrem(email);
    }

    public void saveUserToDatabase( HttpSession session) throws ParseException
    {
        int userId;
        int imageId;
        String role="user";
        boolean enabled=true;
        Date userBday=Date.valueOf(session.getAttribute("userBdayFromDatingUserPersonalData").toString());
        double userWeight=Double.parseDouble(session.getAttribute("userWeightFromDatingUserPersonalData").toString());
        double userHeight=Double.parseDouble(session.getAttribute("userHeightFromDatingUserPersonalData").toString());
        byte [] userProfilePic = session.getAttribute("userProfilePic").toString().getBytes();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(session.getAttribute("userUnhashedPasswordFromSignup").toString());

        usersRepository.saveAndFlush(new Users(session.getAttribute("userEmailFromSignup").toString(),  
                                        session.getAttribute("userFullnameFromSignup").toString(),
                                        hashedPassword,
                                        session.getAttribute("userGenderFromDatingUserPersonalData").toString(),
                                        userBday,
                                        session.getAttribute("userEducationlevelFromDatingUserPersonalData").toString(),
                                        session.getAttribute("userJobFromDatingUserPersonalData").toString(),
                                        session.getAttribute("userLocationFromDatingUserPersonalData").toString(),
                                        session.getAttribute("userHobbiesFromDatingUserPersonalData").toString(),
                                        userHeight,
                                        userWeight,
                                        session.getAttribute("userHairFromDatingUserPersonalData").toString(),
                                        session.getAttribute("userSkinFromDatingUserPersonalData").toString(),
                                        session.getAttribute("userEyeFromDatingUserPersonalData").toString(),
                                        '0',
                                        enabled,
                                        role));

        imagesRepository.saveAndFlush(new Images(userProfilePic));

        userId = usersRepository.getLastId();
        imageId = imagesRepository.getLastId();
        userHasImagesRepository.saveAndFlush(new UserHasImages(userId, imageId, '1'));
        LOGGER.info("User created with id:"+ userId+" and email:"+ session.getAttribute("userEmailFromSignup").toString());
    }
}