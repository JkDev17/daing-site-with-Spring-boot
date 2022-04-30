package dating.dating.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    private HttpSession HttpSession;

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
        when(request.getSession()).thenReturn(HttpSession);
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn("JasmineWhite@gmail.com");
        when(request.getSession().getAttribute("userUnhashedPasswordFromSignup")).thenReturn("abcdefg1234");
        when(request.getSession().getAttribute("userFullnameFromSignup")).thenReturn("Jasmine White");
        when(request.getSession().getAttribute("userAgeFromSignup")).thenReturn("23");
        map = usersServices.serveDatatoGetSession(HttpSession);

        //then
        assertNotNull(map);
    }

    @Test
    @DisplayName("Testing the method serveDatatoGetSession for empty as result")
    public void canNotServeDatatoGetSession()
    {
        //when
        when(request.getSession()).thenReturn(HttpSession);
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn(null);
        when(request.getSession().getAttribute("userUnhashedPasswordFromSignup")).thenReturn(null);
        when(request.getSession().getAttribute("userFullnameFromSignup")).thenReturn(null);
        when(request.getSession().getAttribute("userAgeFromSignup")).thenReturn(null);
        map = usersServices.serveDatatoGetSession(HttpSession);

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
        when(request.getSession()).thenReturn(HttpSession);
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
        when(request.getSession()).thenReturn(HttpSession);
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
}