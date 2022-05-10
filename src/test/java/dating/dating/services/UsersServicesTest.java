package dating.dating.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import dating.dating.entity.Filters;
import dating.dating.entity.Users;
import dating.dating.exceptions.ProfileNotFoundException;
import dating.dating.repositories.ImagesRepository;
import dating.dating.repositories.UserHasImagesRepository;
import dating.dating.repositories.UserVisitedUsersRepository;
import dating.dating.repositories.UsersRepository;

@ExtendWith(MockitoExtension.class)
public class UsersServicesTest 
{
    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UserVisitedUsersRepository userVisitedUsersRepository;

    @Mock
    private UserHasImagesRepository userHasImagesRepository;

    @Mock
    private ImagesRepository imagesRepository;
    
    @Mock
    private HttpSession httpSession;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UsersServices usersServices;

    HashMap<String,String> map = new HashMap<>();


    @Test
    @DisplayName("Testing the method serveDatatoGetSession")
    public void canServeDatatoGetSession()
    {
        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn("JasmineWhite@gmail.com");
        when(request.getSession().getAttribute("userUnhashedPasswordFromSignup")).thenReturn("abcdefg1234");
        when(request.getSession().getAttribute("userFullnameFromSignup")).thenReturn("Jasmine White");
        when(request.getSession().getAttribute("userAgeFromSignup")).thenReturn("23");
        map = usersServices.serveDatatoGetSession(httpSession);

        //then
        assertNotNull(map);
    }

    @Test
    @DisplayName("Testing the method serveDatatoGetSession for empty as result")
    public void canNotServeDatatoGetSession()
    {
        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn(null);
        when(request.getSession().getAttribute("userUnhashedPasswordFromSignup")).thenReturn(null);
        when(request.getSession().getAttribute("userFullnameFromSignup")).thenReturn(null);
        when(request.getSession().getAttribute("userAgeFromSignup")).thenReturn(null);
        map = usersServices.serveDatatoGetSession(httpSession);

        //then
        assertAll("Email,pass,fullname,age ->empty",
        ()-> assertEquals("empty", map.get("email")),
        ()-> assertEquals("empty", map.get("password")),
        ()-> assertEquals("empty", map.get("fullname")),
        ()-> assertEquals("empty", map.get("age"))
        );
    }

    @Test
    @DisplayName("Testing method saveVarsToSession")
    public void canSaveVarsToSessionTest()
    {
        //given
        HashMap <String,String> inputMap = new HashMap<>();
        map.put("email","JohnMartins@gmail.com");
        map.put("password","abcdefg1234");
        map.put("fullname","John Martins");
        map.put("age","19");
  

        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn("JohnMartins@gmail.com");
        when(request.getSession().getAttribute("userUnhashedPasswordFromSignup")).thenReturn("abcdefg1234");
        when(request.getSession().getAttribute("userFullnameFromSignup")).thenReturn("John Martins");
        when(request.getSession().getAttribute("userAgeFromSignup")).thenReturn("19");
        usersServices.saveVarsToSession(inputMap, request);

        //then
        assertAll("Email,pass,fullname,age ->[JohnMartins@gmail.com," +
        "abcdefg1234,John Martins, 19]" ,
        ()-> assertEquals("JohnMartins@gmail.com", request.getSession().getAttribute("userEmailFromSignup").toString()),
        ()-> assertEquals("John Martins", request.getSession().getAttribute("userFullnameFromSignup").toString()),
        ()-> assertEquals("abcdefg1234", request.getSession().getAttribute("userUnhashedPasswordFromSignup").toString()),
        ()-> assertEquals("19", request.getSession().getAttribute("userAgeFromSignup").toString())
        ); 
    }

    @Test
    @DisplayName("Testing method saveVarsToSession")
    public void cannotSaveVarsToSessionTest()
    {
        //given
        HashMap <String,String> inputMap = new HashMap<>();
        map.put("email","JohnMartins@gmail.com");
        map.put("password","abcdefg1234");
        map.put("fullname","John Martins");
        map.put("age","19");

        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn("empty");
        when(request.getSession().getAttribute("userUnhashedPasswordFromSignup")).thenReturn("empty");
        when(request.getSession().getAttribute("userFullnameFromSignup")).thenReturn("empty");
        when(request.getSession().getAttribute("userAgeFromSignup")).thenReturn("empty");
        usersServices.saveVarsToSession(inputMap, request);

        //then
        assertAll("Email,pass,fullname,age -> empty" ,
        ()-> assertEquals("empty", request.getSession().getAttribute("userEmailFromSignup").toString()),
        ()-> assertEquals("empty", request.getSession().getAttribute("userFullnameFromSignup").toString()),
        ()-> assertEquals("empty", request.getSession().getAttribute("userUnhashedPasswordFromSignup").toString()),
        ()-> assertEquals("empty", request.getSession().getAttribute("userAgeFromSignup").toString())
        );
    }

    @Test
    @DisplayName("Testing method fetchUserPersonalDataPage")
    public void canFetchUserPersonalDataPage()
    {
        //given
        HashMap <String,String> map = new HashMap<>();
        map.put("userGender","Male");
        map.put("userBday","16-04-1996");
        map.put("userEducationlevel","Bachelor");
        map.put("userJob","Programmer");
        map.put("userLocation","Athens");
        map.put("userHobbies","MMA MUSIC TRAVELING");
        map.put("userHeight","185");
        map.put("userWeight","85");
        map.put("userEye","blue");
        map.put("userHair","blonde");
        map.put("userSkin","white");
        map.put("mateGender","Female");
        map.put("rangeMin","18");
        map.put("rangeMax","35");
        map.put("mateHeight","165");
        map.put("mateWeight","60");
        map.put("mateSkin","white");
        
        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("userGenderFromDatingUserPersonalData")).thenReturn("Male");
        when(request.getSession().getAttribute("userBdayFromDatingUserPersonalData")).thenReturn("16-04-1996");
        when(request.getSession().getAttribute("userEducationlevelFromDatingUserPersonalData")).thenReturn("Bachelor");
        when(request.getSession().getAttribute("userLocationFromDatingUserPersonalData")).thenReturn("Athens");
        when(request.getSession().getAttribute("userJobFromDatingUserPersonalData")).thenReturn("Programmer");
        when(request.getSession().getAttribute("userHobbiesFromDatingUserPersonalData")).thenReturn("MMA MUSIC TRAVELING");
        when(request.getSession().getAttribute("userHeightFromDatingUserPersonalData")).thenReturn("185");
        when(request.getSession().getAttribute("userWeightFromDatingUserPersonalData")).thenReturn("85");
        when(request.getSession().getAttribute("userEyeFromDatingUserPersonalData")).thenReturn("blue");
        when(request.getSession().getAttribute("userHairFromDatingUserPersonalData")).thenReturn("blonde");
        when(request.getSession().getAttribute("userSkinFromDatingUserPersonalData")).thenReturn("white");
        when(request.getSession().getAttribute("mateGenderFromDatingUserPersonalData")).thenReturn("Female");
        when(request.getSession().getAttribute("rangeMinFromDatingUserPersonalData")).thenReturn("18");
        when(request.getSession().getAttribute("rangeMaxFromDatingUserPersonalData")).thenReturn("35");
        when(request.getSession().getAttribute("mateHeightFromDatingUserPersonalData")).thenReturn("165");
        when(request.getSession().getAttribute("mateWeightFromDatingUserPersonalData")).thenReturn("60");
        when(request.getSession().getAttribute("mateSkinFromDatingUserPersonalData")).thenReturn("white");
        usersServices.fetchUserPersonalDataPage2(map, request);
        usersServices.fetchUserPersonalDataPage3(map, request);
        usersServices.fetchUserPersonalDataPage4(map, request);

        //then
        assertAll(
        ()-> assertEquals("Male", request.getSession().getAttribute("userGenderFromDatingUserPersonalData")),
        ()-> assertEquals("16-04-1996", request.getSession().getAttribute("userBdayFromDatingUserPersonalData")),
        ()-> assertEquals("Bachelor", request.getSession().getAttribute("userEducationlevelFromDatingUserPersonalData")),
        ()-> assertEquals("Athens", request.getSession().getAttribute("userLocationFromDatingUserPersonalData")),
        ()-> assertEquals("Programmer", request.getSession().getAttribute("userJobFromDatingUserPersonalData")),
        ()-> assertEquals("MMA MUSIC TRAVELING", request.getSession().getAttribute("userHobbiesFromDatingUserPersonalData")),
        ()-> assertEquals("185", request.getSession().getAttribute("userHeightFromDatingUserPersonalData")),
        ()-> assertEquals("85", request.getSession().getAttribute("userWeightFromDatingUserPersonalData")),
        ()-> assertEquals("blue", request.getSession().getAttribute("userEyeFromDatingUserPersonalData")),
        ()-> assertEquals("blonde", request.getSession().getAttribute("userHairFromDatingUserPersonalData")),
        ()-> assertEquals("white", request.getSession().getAttribute("userSkinFromDatingUserPersonalData")),
        ()-> assertEquals("Female", request.getSession().getAttribute("mateGenderFromDatingUserPersonalData")),
        ()-> assertEquals("18", request.getSession().getAttribute("rangeMinFromDatingUserPersonalData")),
        ()-> assertEquals("35", request.getSession().getAttribute("rangeMaxFromDatingUserPersonalData")),
        ()-> assertEquals("165", request.getSession().getAttribute("mateHeightFromDatingUserPersonalData")),
        ()-> assertEquals("60", request.getSession().getAttribute("mateWeightFromDatingUserPersonalData")),
        ()-> assertEquals("white", request.getSession().getAttribute("mateSkinFromDatingUserPersonalData"))
        );
    }



    @Test
    @DisplayName("Testing method fetchUserPersonalDataPage")
    public void cannotFetchUserPersonalDataPage()
    {
        //given
        HashMap <String,String> map = new HashMap<>();
        map.put("userGender",null);
        map.put("userBday",null);
        map.put("userEducationlevel",null);
        map.put("userJob",null);
        map.put("userLocation",null);
        map.put("userHobbies",null);
        map.put("userHeight",null);
        map.put("userWeight",null);
        map.put("userEye",null);
        map.put("userHair",null);
        map.put("userSkin",null);
        map.put("mateGender",null);
        map.put("rangeMin",null);
        map.put("rangeMax",null);
        map.put("mateHeight",null);
        map.put("mateWeight",null);
        map.put("mateSkin",null);
        
        //when
        when(request.getSession()).thenReturn(httpSession);
        lenient().when(request.getSession().getAttribute("userGenderFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("userBdayFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("userEducationlevelFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("userLocationFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("userJobFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("userHobbiesFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("userHeightFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("userWeightFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("userEyeFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("userHairFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("userSkinFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("mateGenderFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("rangeMinFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("rangeMaxFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("mateHeightFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("mateWeightFromDatingUserPersonalData")).thenReturn(null);
        lenient().when(request.getSession().getAttribute("mateSkinFromDatingUserPersonalData")).thenReturn(null);
        usersServices.fetchUserPersonalDataPage2(map, request);
        usersServices.fetchUserPersonalDataPage3(map, request);
        usersServices.fetchUserPersonalDataPage4(map, request);

        //then
        assertNull(map.get("userGender"));
    }

    @Test
    @DisplayName("Testing method saveUsersProfilePicToSessionData when input is not null")
    public void canSaveUsersProfilePicToSessionData()
    {
        //given
        HashMap <String,String> map = new HashMap<>();
        map.put("dataImage","ZBNHXDIGHDSGEYHGHWGHJORHPORHJORHF");
        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("userProfilePic")).thenReturn("ZBNHXDIGHDSGEYHGHWGHJORHPORHJORHF");
        usersServices.saveUsersProfilePicToSessionData(map, request);

        //then
        assertEquals("ZBNHXDIGHDSGEYHGHWGHJORHPORHJORHF", request.getSession().getAttribute("userProfilePic"));
    }

    @Test
    @DisplayName("Testing method saveUsersProfilePicToSessionData for null")
    public void cannotSaveUsersProfilePicToSessionData()
    {
        //given
        HashMap <String,String> map = new HashMap<>();
        map.put("dataImage",null);
        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("userProfilePic")).thenReturn(null);
        usersServices.saveUsersProfilePicToSessionData(map, request);

        //then
        assertNull(request.getSession().getAttribute("userProfilePic"));
    }

    @Test
    @DisplayName("Testing fetchUserEmail when input is not null")
    public void canFetchUserEmail()
    {
        //given
        map.put("email", "jimkapadoukas@gmail.com");

        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn("jimkapadoukas@gmail.com");
        usersServices.fetchUserEmail(map, request);

        //then
        assertEquals("jimkapadoukas@gmail.com", request.getSession().getAttribute("email"));
    }

    
    @Test
    @DisplayName("Testing fetchUserEmail when input is null")
    public void cannotFetchUserEmail()
    {
        //given
        map.put("email", null);

        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(null);
        usersServices.fetchUserEmail(map, request);

        //then
        assertEquals(null, request.getSession().getAttribute("email"));
    }

    @Test
    @DisplayName("Testing fetchDataForProfileWhoLoggedIn when not null")
    public void canFetchDataForProfileWhoLoggedIn() throws UnsupportedEncodingException
    {
        //given
        String email = "JasmineWhite";
        String image = "Jasmine White image";
        byte [] img = image.getBytes();

        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(email);
        when(usersRepository.getFullNameByEmail(email)).thenReturn("Jasmine White");
        when(usersRepository.getBdayByEmail(email)).thenReturn("1996-04-16");
        when(usersRepository.getJobByEmail(email)).thenReturn("Programmer");
        when(usersRepository.getLocationByEmail(email)).thenReturn("Athens");
        when(usersRepository.getIdByEmail(email)).thenReturn(1);
        when(usersRepository.getJobByEmail(email)).thenReturn("Student");
        when(userHasImagesRepository.getImageIdByUserId(1)).thenReturn(2);
        when(imagesRepository.getDataById(2)).thenReturn(img);
        when(usersRepository.getEyeColorByEmail(email)).thenReturn("Blue");
        when(usersRepository.getHairColorByEmail(email)).thenReturn("Blonde");
        when(usersRepository.getHobbiesByEmail(email)).thenReturn("MUSIC TRAVELING MMA GYM");
        when(usersRepository.getEducationByEmail(email)).thenReturn("Bachelor");
        when(userVisitedUsersRepository.getUsersVisitedUser(1)).thenReturn(2);
        map = usersServices.fetchDataForProfileWhoLoggedIn(httpSession);

        //then
        assertAll(
        ()-> assertEquals(map.get("image"),image),
        ()-> assertEquals(map.get("job"),"Student"),
        ()-> assertEquals(map.get("age"), "26"),
        ()-> assertEquals(map.get("location"), "Athens"),
        ()-> assertEquals(map.get("fullname"), "Jasmine White"),
        ()-> assertEquals(map.get("hobbies"), "MUSIC TRAVELING MMA GYM"),
        ()-> assertEquals(map.get("hairColor"), "Blonde"),
        ()-> assertEquals(map.get("eyeColor"), "Blue"),
        ()-> assertEquals(map.get("educationlevel"), "Bachelor"),
        ()-> assertEquals(map.get("numOfUsersVisitedUser"), "2")
        );
    }

    @Test
    @DisplayName("Testing method getUserVisitedUsersList no null values")
    public void canGetUserVisitedUsersList()
    {
        //given
        String email = "JasmineWhite@gmail.com";
        ArrayList<Integer> listOfUsersWhoVisitedUser = new ArrayList<Integer>();
        ArrayList<String> listOfUsersFullnamesWhoVisitedUser = new ArrayList<String>();
        listOfUsersWhoVisitedUser.add(2);
        listOfUsersWhoVisitedUser.add(3);
        listOfUsersWhoVisitedUser.add(4);
        listOfUsersFullnamesWhoVisitedUser.add("Jessica");
        listOfUsersFullnamesWhoVisitedUser.add("Lena");
        listOfUsersFullnamesWhoVisitedUser.add("John");
        HashMap <String,List<String>> map = new HashMap<>();

        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(email);
        when(usersRepository.getIdByEmail(email)).thenReturn(1);
        when(userVisitedUsersRepository.getUid1_VisitedUser(1)).thenReturn(listOfUsersWhoVisitedUser);
        for(int i=0; i<listOfUsersWhoVisitedUser.size(); i++)
        {
            lenient().when(usersRepository.getFullnameById(listOfUsersWhoVisitedUser.get(i))).thenReturn(listOfUsersFullnamesWhoVisitedUser.get(i));
        }
        map = usersServices.getUserVisitedUsersList(httpSession);

        //then
        assertEquals(listOfUsersFullnamesWhoVisitedUser, map.get("arrayListOfFullnames"));
        for(int i=0; i<listOfUsersWhoVisitedUser.size(); i++)
        {
            verify(usersRepository, times(1)).getFullnameById(listOfUsersWhoVisitedUser.get(i));
        }
    }

    @Test
    @DisplayName("Testing getBasicDataFromUsers when no null values")
    public void canGetBasicDataFromUsers()
    {
        //given

        String email = "JasmineWhite@gmail.com";
        String fullName = "JasmineWhite";
        String bday = "1998-04-16";
        String age = "24";
        String job = "Student";
        String location = "Athens";
        int userId = 1;
        int imageId = 2;
        String imgData = "Jasmine's White image";
        byte [] img = imgData.getBytes();
        String eyeColor = "blue";
        String hairColor = "Blonde";
        String hobbies = "Cooking, Traveling, Music, Dancing";
        String education = "Bachelor";

        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(email);
        when(usersRepository.getFullNameByEmail(email)).thenReturn(fullName);
        when(usersRepository.getBdayByEmail(email)).thenReturn(bday);
        when(usersRepository.getJobByEmail(email)).thenReturn(job);
        when(usersRepository.getLocationByEmail(email)).thenReturn(location);
        when(usersRepository.getIdByEmail(email)).thenReturn(userId);
        when(userHasImagesRepository.getImageIdByUserId(userId)).thenReturn(imageId);
        when(imagesRepository.getDataById(imageId)).thenReturn(img);
        when(usersRepository.getEyeColorByEmail(email)).thenReturn(eyeColor);
        when(usersRepository.getHairColorByEmail(email)).thenReturn(hairColor);
        when(usersRepository.getHobbiesByEmail(email)).thenReturn(hobbies);
        when(usersRepository.getEducationByEmail(email)).thenReturn(education);
        map = usersServices.getBasicDataFromUsers(httpSession);

        //then
        verify(usersRepository,times(1)).getBdayByEmail(email);
        verify(usersRepository,times(1)).getEducationByEmail(email);
        verify(usersRepository,times(1)).getHairColorByEmail(email);
        verify(usersRepository,times(1)).getEyeColorByEmail(email);
        verify(usersRepository,times(1)).getEducationByEmail(email);
        verify(usersRepository,times(1)).getJobByEmail(email);
        verify(usersRepository,times(1)).getFullNameByEmail(email);
        verify(usersRepository,times(1)).getIdByEmail(email);
        verify(imagesRepository,times(1)).getDataById(2);
        verify(userHasImagesRepository,times(1)).getImageIdByUserId(1);

        assertAll(
            ()-> assertEquals(map.get("image"),imgData),
            ()-> assertEquals(map.get("job"),job),
            ()-> assertEquals(map.get("age"), age),
            ()-> assertEquals(map.get("location"), location),
            ()-> assertEquals(map.get("fullname"), fullName),
            ()-> assertEquals(map.get("hobbies"), hobbies),
            ()-> assertEquals(map.get("hairColor"), hairColor),
            ()-> assertEquals(map.get("eyeColor"), eyeColor),
            ()-> assertEquals(map.get("educationlevel"), education)
            );
    }

    @Test
    @DisplayName("Testing method userUpdatesToPremium no null values")
    public void canUserUpdateToPremium()
    {
        //given
        String email = "JasmineWhite@gmail.com";
        HashMap<String,Integer> map = new HashMap<>();
        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(email);
        when(usersRepository.updateIsPrem(email)).thenReturn(1);
        map = usersServices.userUpdatesToPremium(httpSession);

        //then
        assertEquals(1, map.get("res"));
    }

    @Test
    @DisplayName("Testing method getStarFromUsersNeqId when no null values")
    public void canGetStarFromUsersNeqId() throws ParseException
    {
        //given
        String email = "JasmineWhite@gmail.com";
        ArrayList <byte[]> images = new ArrayList<byte[]>();
        HashMap<String,List<String>> map = new HashMap<>();
        String image1 = "John Martins image data";
        String image2 = "George Black image data";
        String image3 = "Daniel Leblanc image data";
        byte []  imageData1 = image1.getBytes();
        byte []  imageData2 = image2.getBytes();
        byte []  imageData3 = image3.getBytes();
        images.add(imageData1);
        images.add(imageData2);
        images.add(imageData3);
        int id = 1;
        int imageId = 2;
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        Date sqlDate1 = new java.sql.Date(df.parse("02-04-2000").getTime());
        Date sqlDate2 = new java.sql.Date(df.parse("16-04-1996").getTime());
        Date sqlDate3 = new java.sql.Date(df.parse("12-07-1992").getTime());
        ArrayList<Users> arrayListOfUsers = new ArrayList<>();
        Users user1 = new Users("MikeJohnSon@gmail.com","Mike Johnson", "abcdefg1234", "male",
                                (Date) sqlDate1, "Bachelor", "Student", "New York", "READING TRAVELING MUSIC MOVIES",
                                190.0,85.5, "brown", "white", "blue", '0', true, "user");

        Users user2 = new Users("AlexPerez@gmail.com","Alex Perez", "AlexPerezIsABeAST1!", "male",
                                (Date) sqlDate2, "Bachelor", "UFC FIGHTER", "Houston", "MMA MUSIC GYM GAMING",
                                195.0,95.5, "black", "white", "brown", '0', true, "user");

        Users user3 = new Users("DanielLeblanc@gmail.com","Daniel Leblanc", "ParisEloiseMaria17", "male",
                                (Date) sqlDate3, "Master", "UFC FIGHTER", "Houston", "ACTING TRAVELING FOOD SPORTS",
                                187.0,77.0, "brown", "white", "brown", '0', true, "user");
        arrayListOfUsers.add(user1);                     
        arrayListOfUsers.add(user2);
        arrayListOfUsers.add(user3);

        //when      
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(email);
        when(usersRepository.getIdByEmail(email)).thenReturn(id);
        when(userHasImagesRepository.userHasImages(id)).thenReturn(imageId);
        when(imagesRepository.getDataNeqId(imageId)).thenReturn(images);
        when(usersRepository.getStarFromUsersNeqToId(id)).thenReturn(arrayListOfUsers);
        map = usersServices.getStarFromUsersNeqId(httpSession);

        //then
        assertNotNull(map);
    }

    @Test
    @DisplayName("Testing method getStarFromUsersNeqId when null")
    public void cannotGetStarFromUsersNeqId() throws ParseException
    {
        //given
        String email = null;
        ArrayList <byte[]> images = new ArrayList<byte[]>();
        String image1 = "Mike Johnson image data";
        String image2 = "Alex Perez Black image data";
        String image3 = "Daniel Leblanc image data";
        byte []  imageData1 = image1.getBytes();
        byte []  imageData2 = image2.getBytes();
        byte []  imageData3 = image3.getBytes();
        images.add(imageData1);
        images.add(imageData2);
        images.add(imageData3);
        int id = 1;
        int imageId = 2;
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        Date sqlDate1 = new java.sql.Date(df.parse("02-04-2000").getTime());
        Date sqlDate2 = new java.sql.Date(df.parse("16-04-1996").getTime());
        Date sqlDate3 = new java.sql.Date(df.parse("12-07-1992").getTime());
        ArrayList<Users> arrayListOfUsers = new ArrayList<>();
        Users user1 = new Users("MikeJohnSon@gmail.com","Mike Johnson", "abcdefg1234", "male",
                                (Date) sqlDate1, "Bachelor", "Student", "New York", "READING TRAVELING MUSIC MOVIES",
                                190.0,85.5, "brown", "white", "blue", '0', true, "user");

        Users user2 = new Users("AlexPerez@gmail.com","Alex Perez", "AlexPerezIsABeAST1!", "male",
                                (Date) sqlDate2, "Bachelor", "UFC FIGHTER", "Houston", "MMA MUSIC GYM GAMING",
                                195.0,95.5, "black", "white", "brown", '0', true, "user");

        Users user3 = new Users("DanielLeblanc@gmail.com","Daniel Leblanc", "ParisEloiseMaria17", "male",
                                (Date) sqlDate3, "Master", "UFC FIGHTER", "Houston", "ACTING TRAVELING FOOD SPORTS",
                                187.0,77.0, "brown", "white", "brown", '0', true, "user");
        arrayListOfUsers.add(user1);                     
        arrayListOfUsers.add(user2);
        arrayListOfUsers.add(user3);

        //when      
        lenient().when(request.getSession()).thenReturn(httpSession);
        lenient().when(request.getSession().getAttribute("email")).thenReturn(email);
        lenient().when(usersRepository.getIdByEmail(email)).thenReturn(id);
        lenient().when(userHasImagesRepository.userHasImages(id)).thenReturn(imageId);
        lenient().when(imagesRepository.getDataNeqId(imageId)).thenReturn(images);
        lenient().when(usersRepository.getStarFromUsersNeqToId(id)).thenReturn(arrayListOfUsers);
        ProfileNotFoundException exception = assertThrows(ProfileNotFoundException.class,
        ()-> usersServices.getStarFromUsersNeqId(httpSession), "Null value found");

        //then
        assertTrue(exception.getMyMessage().contains("Null"));
    } 
    
    @Test
    @DisplayName("Testing method updateUserVisitedUser when no null")
    public void canUpdateUserVisitedUser()
    {   
        //given
        String email = "JasmineWhite@gmail.com";
        String fullname = "John Smith";
        int id1 = 1, id2 =2;
        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(email);
        when(request.getParameter("fullname")).thenReturn(fullname);
        when(usersRepository.getIdByEmail(email)).thenReturn(id1);
        when(usersRepository.getIdByFullname(fullname)).thenReturn(id2);
        when(userVisitedUsersRepository.getUid1_VisitedUserUid2(id2,id1)).thenReturn(null);
        usersServices.updateUserVisitedUser(httpSession, request);
        //then
        verify(usersRepository, times(1)).getIdByEmail(email);
        verify(usersRepository, times(1)).getIdByFullname(fullname);
        verify(userVisitedUsersRepository, times(1)).saveAndFlush(any());
    }

    @Test
    @DisplayName("Testing method updateUserVisitedUser when null")
    public void cannnotUpdateUserVisitedUser()
    {   
        //given
        String email = "JasmineWhite@gmail.com";
        String fullname = null;
        int id1 = 1, id2 =2;
        //when
        when(request.getSession()).thenReturn(httpSession);
        lenient().when(request.getSession().getAttribute("email")).thenReturn(email);
        lenient().when(request.getParameter("fullname")).thenReturn(fullname);
        lenient().when(usersRepository.getIdByEmail(email)).thenReturn(id1);
        lenient().when(usersRepository.getIdByFullname(fullname)).thenReturn(id2);
        lenient().when(userVisitedUsersRepository.getUid1_VisitedUserUid2(id2,id1)).thenReturn(null);
        ProfileNotFoundException profileNotFoundException = assertThrows(ProfileNotFoundException.class,
            ()-> usersServices.updateUserVisitedUser(httpSession,request));
        //then
        assertTrue(profileNotFoundException.getMyMessage().contains("Null"));
    }

    static public Stream<Arguments> getFiltersArgs()
    {
        return Stream.of(Arguments.of(new Filters("female",
         "blonde", "blue", 65, 165.0, 25)));
    }
    
    @ParameterizedTest
    @MethodSource("getFiltersArgs")
    @DisplayName("Testing method filterProfiles when no null and with both filters(gender,hair)")
    public void canFilterProfilesWithBothFilters(Filters filters) throws ParseException
    {
        //given
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Integer> imageIds = new ArrayList<>();
        ArrayList <byte[]> images = new ArrayList<byte[]>();
        HashMap<String,List<String>> map = new HashMap<>();
        ids.add(4);
        ids.add(3);
        ids.add(7);
        imageIds.add(5);
        imageIds.add(8);
        imageIds.add(11);
        String email = "JasmineWhite@gmail.com";
        String image1 = "Lena Johnson image data";
        String image2 = "Jessica Muller Black image data";
        String image3 = "Anna White image data";
        byte []  imageData1 = image1.getBytes();
        byte []  imageData2 = image2.getBytes();
        byte []  imageData3 = image3.getBytes();
        images.add(imageData1);
        images.add(imageData2);
        images.add(imageData3);
        int id = 1;

        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        Date sqlDate1 = new java.sql.Date(df.parse("02-04-2000").getTime());
        Date sqlDate2 = new java.sql.Date(df.parse("16-04-1996").getTime());
        Date sqlDate3 = new java.sql.Date(df.parse("12-07-1992").getTime());
        ArrayList<Users> arrayListOfUsers = new ArrayList<>();
        Users user1 = new Users("LenaJohnson@gmail.com","Lena Johnson", "abcdefg!@", "female",
                                (Date) sqlDate1, "Master", "microbiologist", "New York", "READING COOKING TRAVELING MOVIES",
                                160.0,55.5, "blonde", "white", "blue", '0', true, "user");

        Users user2 = new Users("JessicaMuller@gmail.com","Jessica Muller", "Jessieee754!", "female",
                                (Date) sqlDate2, "Bachelor", "Actor", "Phoenix Arizona", "Traveling Partying drinking dancing",
                                175.0,65.5, "blonde", "white", "blue", '0', true, "user");

        Users user3 = new Users("AnnaWhite@gmail.com","Anna White", "AnnaAndMikeForEver", "female",
                                (Date) sqlDate3, "Master", "Sales accountant", "London", "ACTING TRAVELING FOOD MOVIES",
                                172.0,57.0, "blonde", "white", "blue", '0', true, "user");
        arrayListOfUsers.add(user1);                     
        arrayListOfUsers.add(user2);
        arrayListOfUsers.add(user3);

        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(email);
        when(usersRepository.getIdByEmail(email)).thenReturn(id);
        when(usersRepository.selecStartWithFilters1(filters.getgenderFilter(), filters.gethairFilter(), id)).thenReturn(arrayListOfUsers);
        for(int i=0; i<arrayListOfUsers.size(); i++)
        {
            when(usersRepository.getIdByFullname(arrayListOfUsers.get(i).getFullname())).thenReturn(ids.get(i));
        }
        when(userHasImagesRepository.getImageIdByUsersId(ids)).thenReturn(imageIds);
        when(imagesRepository.getDataEqImageIds(imageIds)).thenReturn(images);
        map = usersServices.filterProfiles(filters, httpSession, request);

        //then
        assertEquals(arrayListOfUsers.get(0).getFullname(), map.get("fullnames").get(0));
        verify(usersRepository,times(1)).getIdByEmail(email);
        verify(usersRepository,times(1)).selecStartWithFilters1(filters.getgenderFilter(), filters.gethairFilter(),id);
        for(int i=0; i<arrayListOfUsers.size(); i++)
            verify(usersRepository,times(1)).getIdByFullname(arrayListOfUsers.get(i).getFullname());
        verify(userHasImagesRepository,times(1)).getImageIdByUsersId(ids);
        verify(imagesRepository,times(1)).getDataEqImageIds(imageIds);
    }

    static public Stream<Arguments> getFiltersArgs2()
    {
        return Stream.of(Arguments.of(new Filters("empty",
         "empty", "blue", 65, 165.0, 25)));
    }
    
    @ParameterizedTest
    @MethodSource("getFiltersArgs2")
    @DisplayName("Testing method filterProfiles when filters are empty")
    public void cannotFilterProfiles(Filters filters) throws ParseException
    {
        //given
        HashMap<String,List<String>> map = new HashMap<>();
        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("Could not find any filtering arguments");
        String email = "JasmineWhite@gmail.com";

        //when  
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(email);
        map = usersServices.filterProfiles(filters, httpSession, request);

        //then
        assertEquals(expectedResult, map.get("error"));
    }

    static public Stream<Arguments> getFiltersArgs3()
    {
        return Stream.of(Arguments.of(new Filters(null,
        null, "blue", 65, 165.0, 25)));
    }

    @ParameterizedTest
    @MethodSource("getFiltersArgs3")
    @DisplayName("Testing method filterProfiles when filters,session objs are null")
    public void cannotFilterProfilesNull(Filters filters) throws ParseException
    {
        //given
        String email = null;

        //when  
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(email);
        ProfileNotFoundException profileNotFoundException = assertThrows(ProfileNotFoundException.class,
        ()-> usersServices.filterProfiles(filters, httpSession, request));

        //then
        System.out.println(profileNotFoundException.getMessage());
        assertTrue(profileNotFoundException.getMyMessage().contains("parameters not found"));
    }
       
    @Test
    @DisplayName("Testing method fetchOnePersonVisited when no null values")
    public void canFetchOnePersonVisited() throws ParseException
    {

        //given
        String email = "JasmineWhite@gmail.com";
        String emailPerson ="Jasmine White";     
        String fullName = "John Tompson";
        String bday = "1996-04-02";
        String job = "Software Developer";
        String location = "New York";
        String eyeColor = "black";
        String hairColor= "brown";
        String educationalLevel = "Masters";
        String hobbies = "TRAVELING COOKING READING SPORTS";
        String imgData = "This is the picture of JohnTompson";
        byte [] imgDataToBytes = imgData.getBytes();
        int id =7;
        int imageId= 9;

        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("email")).thenReturn(email);
        when(request.getSession().getAttribute("fullnamePerson")).thenReturn(fullName);
        when(usersRepository.getFullNameByEmail(email)).thenReturn(fullName);
        when(usersRepository.getEmailByFullname(fullName)).thenReturn(emailPerson);
        when(usersRepository.getBdayByEmail(emailPerson)).thenReturn(bday);
        when(usersRepository.getJobByEmail(emailPerson)).thenReturn(job);
        when(usersRepository.getLocationByEmail(emailPerson)).thenReturn(location);
        when(usersRepository.getHairColorByEmail(emailPerson)).thenReturn(hairColor);
        when(usersRepository.getEyeColorByEmail(emailPerson)).thenReturn(eyeColor);
        when(usersRepository.getEducationByEmail(emailPerson)).thenReturn(educationalLevel);
        when(usersRepository.getHobbiesByEmail(emailPerson)).thenReturn(hobbies);
        when(usersRepository.getIdByEmail(emailPerson)).thenReturn(id);
        when(userHasImagesRepository.getImageIdByUserId(id)).thenReturn(imageId);
        when(imagesRepository.getDataById(imageId)).thenReturn(imgDataToBytes);
        map = usersServices.fetchOnePersonVisited(httpSession);

        //then
        assertEquals(educationalLevel, map.get("educationlevel"));
        verify(usersRepository, times(1)).getFullNameByEmail(email);
        verify(usersRepository, times(1)).getEmailByFullname(fullName);
        verify(usersRepository, times(1)).getEducationByEmail(emailPerson);
        verify(usersRepository, times(1)).getHobbiesByEmail(emailPerson);
        verify(usersRepository, times(1)).getHairColorByEmail(emailPerson);
        verify(usersRepository, times(1)).getEyeColorByEmail(emailPerson);
        verify(usersRepository, times(1)).getIdByEmail(emailPerson);
        verify(userHasImagesRepository, times(1)).getImageIdByUserId(id);
        verify(imagesRepository, times(1)).getDataById(imageId);
    }

    @Test
    @DisplayName("Testing method fetchOnePersonVisited when null")
    public void cannotFetchOnePersonVisited()
    {
        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("fullnamePerson")).thenReturn(null);
        ProfileNotFoundException profileNotFoundException = assertThrows(ProfileNotFoundException.class, 
        ()-> usersServices.fetchOnePersonVisited(httpSession));

        //then
        assertTrue(profileNotFoundException.getMyMessage().contains("Profile not found"));
    }

    @Test
    public void canGetUserRole()
    {
        //given
        String emailAsParam = "JasmineWhite@gmail.com";
        char result;
        char expectedResult = '1';
        //when
        when(usersRepository.userIsPrem(emailAsParam)).thenReturn('1');
        result = usersServices.getUserRole(emailAsParam);

        //then
        assertEquals(expectedResult, result);
    }   

    @Test
    public void cannotGetUserRole()
    {
        //given
        String emailAsParam = "JasmineWhite@gmail.com";
        char result;
        char expectedResult = '0';
        //when
        when(usersRepository.userIsPrem(emailAsParam)).thenReturn('0');
        result = usersServices.getUserRole(emailAsParam);

        //then
        assertEquals(expectedResult, result);
    }   


    @Test
    @DisplayName("Testing the method checkDuplicateEmail when email is present")
    public void checkDuplicateEmailUnique()
    {
        //given
        String email = "JasmineWhite@gmail.com";
        String emailValue = null;
        Optional <String> userRepOptionalResponse = Optional.ofNullable(emailValue);
        String result;
        String expectedResult = "new";
        //when
        when(request.getSession()).thenReturn(httpSession);
        lenient().when(request.getSession().getAttribute("email")).thenReturn(email);
        when(usersRepository.findByEmail(email)).thenReturn(userRepOptionalResponse);
        result = usersServices.checkDuplicateEmail(email);

        //then
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Testing the method checkDuplicateEmail when email is present")
    public void checkDuplicateEmailNotUnique()
    {
        //given
        String email = "JasmineWhite@gmail.com";
        Optional <String> userRepOptionalResponse = Optional.of(email);
        String result;
        String expectedResult = "taken";
        //when
        when(request.getSession()).thenReturn(httpSession);
        lenient().when(request.getSession().getAttribute("email")).thenReturn(email);
        when(usersRepository.findByEmail(email)).thenReturn(userRepOptionalResponse);
        result = usersServices.checkDuplicateEmail(email);

        //then
        assertEquals(expectedResult, result);
    }

    /*@Test
    @DisplayName("Testing method saveUserToDatabase when no null values are passed")
    public void canSaveUserToDatabase() throws ParseException
    {
        //given
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        Date bday = new java.sql.Date(df.parse("1996-04-02").getTime());
        String email = "JohnPhilips@gmail.com";
        String fullName = "John Philips";
        String userEducationalLevel = "Masters";
        String job = "Software Developer";
        String userLocation = "London";
        String hobbies = "KICK BOXING TRAVELING MUSIC PARTYING";
        String hairColor = "brown";
        String userGender = "male";
        String skinColor = "white";
        String eyeColor = "blue";
        String img = "User image";
        double userWeight = 80.5;
        double userHeight = 185.0;
        byte[] imgData = img.getBytes();
        int userId = 17;
        int userImageId = 21;
        char isProfilePic = '1';
        String role = "user";
        char isPrem = '0';
        boolean enabled = true;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Users user = new Users(email, fullName, passwordEncoder.encode("abcdefg1234"),userGender, bday, userEducationalLevel, job ,
                                                    userLocation, hobbies, userHeight, userWeight, hairColor, skinColor,
                                                    eyeColor, isPrem, enabled, role);
        UsersServices testUsersServices = spy(new UsersServices(usersRepository,
                                                                userHasImagesRepository,
                                                                imagesRepository,
                                                                userVisitedUsersRepository));
        Users userMock = mock(Users.class);
    
        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("userBdayFromDatingUserPersonalData")).thenReturn(bday);
        when(request.getSession().getAttribute("userWeightFromDatingUserPersonalData")).thenReturn(userWeight);
        when(request.getSession().getAttribute("userHeightFromDatingUserPersonalData")).thenReturn(userHeight);
        when(request.getSession().getAttribute("userUnhashedPasswordFromSignup")).thenReturn("abcdefg1234");
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn(email);
        when(request.getSession().getAttribute("userFullnameFromSignup")).thenReturn(fullName);
        when(request.getSession().getAttribute("userGenderFromDatingUserPersonalData")).thenReturn(userGender);
        when(request.getSession().getAttribute("userEducationlevelFromDatingUserPersonalData")).thenReturn(userEducationalLevel);
        when(request.getSession().getAttribute("userProfilePic")).thenReturn(imgData);
        when(request.getSession().getAttribute("userJobFromDatingUserPersonalData")).thenReturn(job);
        when(request.getSession().getAttribute("userLocationFromDatingUserPersonalData")).thenReturn(userLocation);
        when(request.getSession().getAttribute("userHobbiesFromDatingUserPersonalData")).thenReturn(hobbies);
        when(request.getSession().getAttribute("userHairFromDatingUserPersonalData")).thenReturn(hairColor);
        when(request.getSession().getAttribute("userSkinFromDatingUserPersonalData")).thenReturn(skinColor);
        when(request.getSession().getAttribute("userEyeFromDatingUserPersonalData")).thenReturn(eyeColor);
        when(usersRepository.saveAndFlush(user)).thenReturn(user);  
        when(imagesRepository.saveAndFlush(new Images(imgData))).thenReturn(new Images(imgData));
        when(usersRepository.getLastId()).thenReturn(userId);
        when(imagesRepository.getLastId()).thenReturn(userImageId);
        when(userHasImagesRepository.saveAndFlush(new UserHasImages(userId, userImageId, isProfilePic)))
                                                .thenReturn(new UserHasImages(userId, userImageId, isProfilePic));
        usersServices.saveUserToDatabase(httpSession);

        //then
        //verify(imagesRepository,times(1)).saveAndFlush(new Images(imgData));
        //verify(usersRepository,times(1)).getLastId();
        //verify(imagesRepository,times(1)).getLastId();
        //verify(userHasImagesRepository,times(1)).saveAndFlush(new UserHasImages(userId, userImageId, isProfilePic));
    }*/

    @Test
    @DisplayName("Testing method matchingProfiles when no null")
    public void canMatchProfiles() throws ParseException
    {
        //given
        HashMap<String,List<String>> map = new HashMap<>();
        List<Users> users = new ArrayList<Users>();
        ArrayList<String> bdays = new ArrayList<String>();
        bdays.add("2000-02-04");
        bdays.add("1996-01-02");
        bdays.add("1999-07-01");
        ArrayList<String> emails = new ArrayList<String>();
        emails.add("MikeJohnSon@gmail.com");
        emails.add("AlexPerez@gmail.com");
        emails.add("DanielLeblanc@gmail.com");
        ArrayList<String> fullNames = new ArrayList<String>();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        fullNames.add("Mike Johnson");
        fullNames.add("Alex Perez");
        fullNames.add("Daniel Leblanc");
        ids.add(5);
        ids.add(4);
        ids.add(6);
        ArrayList<Integer> imagesIdsList = new ArrayList<Integer>();
        imagesIdsList.add(2);
        imagesIdsList.add(3);
        imagesIdsList.add(4);
        List <byte[]> byteImg = new ArrayList<byte[]> ();
        String img1 = "Mike Johnson image";
        String img2 = "Alex Perez image";
        String img3 = "Daniel Leblanc image";
        byteImg.add(img1.getBytes());
        byteImg.add(img2.getBytes());
        byteImg.add(img3.getBytes());
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        Date sqlDate1 = new java.sql.Date(df.parse("02-04-2000").getTime());
        Date sqlDate2 = new java.sql.Date(df.parse("16-04-1996").getTime());
        Date sqlDate3 = new java.sql.Date(df.parse("12-07-1992").getTime());
        Users user1 = new Users("MikeJohnSon@gmail.com","Mike Johnson", "abcdefg1234", "male",
        (Date) sqlDate1, "Bachelor", "Student", "California", "READING TRAVELING MUSIC MOVIES",
        190.0,85.5, "brown", "black", "blue", '0', true, "user");

        Users user2 = new Users("AlexPerez@gmail.com","Alex Perez", "AlexPerezIsABeAST1!", "male",
        (Date) sqlDate2, "Bachelor", "UFC FIGHTER", "Houston", "MMA MUSIC GYM GAMING",
        195.0,95.5, "black", "brown", "brown", '0', true, "user");

        Users user3 = new Users("DanielLeblanc@gmail.com","Daniel Leblanc", "ParisEloiseMaria17", "male",
        (Date) sqlDate3, "Master", "Software Developer", "California", "ACTING TRAVELING FOOD SPORTS",
        187.0,77.0, "brown", "white", "brown", '0', true, "user");
        users.add(user1);
        users.add(user2);
        users.add(user3);

        String email = "JasmineWhite@gmail.com";
        String location = "California";
        int id =7;

        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn(email);
        when(usersRepository.getIdByEmail(email)).thenReturn(id);
        when(usersRepository.getLocationByEmail(email)).thenReturn(location);
        when(usersRepository.getStarFromUsersNeqToId(id)).thenReturn(users);
        when(request.getSession().getAttribute("mateSkinFromDatingUserPersonalData")).thenReturn("white");
        when(request.getSession().getAttribute("mateGenderFromDatingUserPersonalData")).thenReturn("male");
        when(request.getSession().getAttribute("rangeMaxFromDatingUserPersonalData")).thenReturn("30");
        when(request.getSession().getAttribute("rangeMinFromDatingUserPersonalData")).thenReturn("20");
        when(request.getSession().getAttribute("mateHeightFromDatingUserPersonalData")).thenReturn("180");
        when(request.getSession().getAttribute("mateWeightFromDatingUserPersonalData")).thenReturn("80");
        for(int i=0; i<users.size(); i++)
        {
            when(usersRepository.getBdayByEmail(emails.get(i))).thenReturn(bdays.get(i));
        }
        for(int i=0; i<users.size();i++)
        {
            when(usersRepository.getIdByFullname(fullNames.get(i))).thenReturn(ids.get(i));

        }
        for(int i=0; i<users.size(); i++)
        {
            when(userHasImagesRepository.getImageIdByUserId(ids.get(i))).thenReturn(imagesIdsList.get(i));
        }
        for(int i=0; i<users.size();i++)
        {
            when(imagesRepository.getDataById(imagesIdsList.get(i))).thenReturn(byteImg.get(i));
        }
        map = usersServices.matchingProfiles(httpSession);

        //then
        assertEquals("Daniel Leblanc", map.get("fullnames").get(0));
        verify(usersRepository,times(1)).getIdByEmail(email);
        verify(usersRepository,times(1)).getLocationByEmail(email);
    }

    @Test
    @DisplayName("Testing method cannotMatchProfiles")
    public void cannotMatchProfiles()
    {
        //given
        String email ="JasmineWhite@gmail.com";
        int id = 7;
        
        //when
        when(request.getSession()).thenReturn(httpSession);
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn(email);
        when(usersRepository.getIdByEmail(email)).thenReturn(id);
        when(usersRepository.getStarFromUsersNeqToId(id)).thenReturn(null);
        ProfileNotFoundException profileNotFoundException = assertThrows(ProfileNotFoundException.class,
        ()-> usersServices.matchingProfiles(httpSession), "Session variables returning null");

        //then
        assertTrue(profileNotFoundException.getMyMessage().contains("null"));
    }
}