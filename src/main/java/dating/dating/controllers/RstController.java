package dating.dating.controllers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dating.dating.entity.Filters;
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

    Logger LOGGER = LoggerFactory.getLogger(RestController.class);
    
    @GetMapping(value="/checkDuplicateEmail")
    public String checkDuplicateEmail(@RequestParam("email") String email)
    {
        return userService.checkDuplicateEmail(email);
    }
    
    @PostMapping(value="/saveVarsToSession" , consumes = "application/json", produces="application/json")
    public Map<String,String> getVarsTosaveToSession(@RequestBody Map<String, String> input , HttpServletRequest request) 
    {
        userService.saveVarsToSession(input, request);
        return input; 
    }

    @GetMapping(value="/getSessionData")
    public Map<String,String> getSessionData(HttpSession session)
    {
        HashMap<String, String> map = new HashMap<>();
        map = userService.serveDatatoGetSession(session);
        return map;
    }


    @PostMapping(value="/fetchUserPersonalDataIdEq2") //id equals to 2 => check js to remember
    public void fetchUserPersonalDataIdEq2(@RequestBody Map<String, String> input , HttpServletRequest request)
    {
        userService.fetchUserPersonalDataPage2(input, request);
    } 

    @PostMapping(value="/fetchUserPersonalDataIdEq3")
    public void fetchUserPersonalDataIdEq3(@RequestBody Map<String, String> input , HttpServletRequest request)
    {
        userService.fetchUserPersonalDataPage3(input, request);
    }

    @PostMapping(value="/fetchUserPersonalDataIdEq4")
    public void fetchUserPersonalDataIdEq4(@RequestBody Map<String, String> input , HttpServletRequest request)
    {
        userService.fetchUserPersonalDataPage4(input, request);
    }

    @PostMapping(value="/fetchUserPersonalDataProfilePic")
    public void fetchUserProfilePic(@RequestBody Map<String, String> input , HttpServletRequest request)
    {
        userService.saveUsersProfilePicToSessionData(input, request);
    }

    @GetMapping(value="/saveDataToDatabase")
    public void saveDataToDatabase(HttpSession session) throws ParseException
    {
        userService.saveUserToDatabase(session);
    }

    @PostMapping(value="/sessionEmail",produces = {"application/json"},consumes = {"application/json"})
    public void fetchUserLogInEmail(@RequestBody Map<String, String> input , HttpServletRequest request)
    {
        userService.fetchUserEmail(input, request);
    }

    @GetMapping(value= "/fetchDataFromDatabase")
    public Map<String,String> fetchDataFromDatabase(HttpSession session)
    {
        HashMap<String,String> map = new HashMap<>();
        map = userService.fetchDataForProfileWhoLoggedIn(session);
        return map;
    }

    @GetMapping(value= "/fetchUserVisitedUsersList")
    public Map<String,List<String>> fetchUserVisitedUsersList(HttpSession session)
    {
        HashMap<String,List<String>> map = new HashMap<>();
        map = userService.getUserVisitedUsersList(session);
        return map;
    }

    @GetMapping(value= "/fetchBasicDataFromDB")
    public Map<String,String> fetchBasicDataFromDB(HttpSession session)
    {
        HashMap<String,String> map = new HashMap<>();
        map = userService.getBasicDataFromUsers(session);
        return map;
    }

    @GetMapping(value= "/updateToPremium")
    public Map<String,Integer> updateToPremium(HttpSession session)
    {
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        map = userService.userUpdatesToPremium(session);
        return map;
    }

    @GetMapping(value = "/fetchStarFromUsersNeqId")
    public HashMap<String, List<String>> fetchStarFromUsersNeqId(HttpSession session)
    {
        HashMap <String,List<String>> map = new HashMap <String,List<String>>();
        map = userService.getStarFromUsersNeqId(session);
        return map;
    }

    @GetMapping(value = "/updateUserVisitedUser")
    public void userVisitedUser(HttpSession session, HttpServletRequest request)   
    {
        userService.updateUserVisitedUser(session, request);
    } 

    @PostMapping(value="/FilterProfiles")
    public HashMap<String,List<String>> filterProfiles(@RequestBody Filters  filter,HttpSession session, HttpServletRequest request)
    {
        HashMap<String,List<String>> map = new HashMap<String,List<String>>();
        map = userService.filterProfiles(filter, session, request);
        return map;
    }   

    @GetMapping(value = "/fetchOnePersonVisited")
    public Map<String,String> fetchOnePersonVisited(HttpSession session)
    {
        HashMap<String,String> map = new HashMap<>();
        map = userService.fetchOnePersonVisited(session);
        return map;
    }

    @GetMapping(value = "/MatchingProfiles")
    public HashMap<String,List<String>> matchingProfiles (HttpSession session)
    {
        HashMap <String,List<String>> map = new HashMap <String,List<String>>();
        map = userService.matchingProfiles(session);
        return map;
    }
}