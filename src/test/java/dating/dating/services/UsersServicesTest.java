package dating.dating.services;

import static org.junit.jupiter.api.Assertions.*;
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
        //then
        when(request.getSession()).thenReturn(HttpSession);
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn("JasmineWhite@gmail.com");
        when(request.getSession().getAttribute("userUnhashedPasswordFromSignup")).thenReturn("abcdefg1234");
        when(request.getSession().getAttribute("userFullnameFromSignup")).thenReturn("Jasmine White");
        when(request.getSession().getAttribute("userAgeFromSignup")).thenReturn("23");
        map = usersServices.serveDatatoGetSession(HttpSession);

        //given
        assertNotNull(map);
    }

    @Test
    @DisplayName("Testing the method serveDatatoGetSession for empty as result")
    public void canNotServeDatatoGetSession()
    {
        //then
        when(request.getSession()).thenReturn(HttpSession);
        when(request.getSession().getAttribute("userEmailFromSignup")).thenReturn(null);
        when(request.getSession().getAttribute("userUnhashedPasswordFromSignup")).thenReturn(null);
        when(request.getSession().getAttribute("userFullnameFromSignup")).thenReturn(null);
        when(request.getSession().getAttribute("userAgeFromSignup")).thenReturn(null);
        map = usersServices.serveDatatoGetSession(HttpSession);

        //given
        assertAll("Order: John,Eric,Lana",
                ()-> assertEquals("empty", map.get("email")),
                ()-> assertEquals("empty", map.get("password")),
                ()-> assertEquals("empty", map.get("fullname")),
                ()-> assertEquals("empty", map.get("age"))
        );
    }

    ;
}
