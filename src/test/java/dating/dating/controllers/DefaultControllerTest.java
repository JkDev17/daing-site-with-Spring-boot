package dating.dating.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import dating.dating.services.UsersServices;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DefaultController.class)
public class DefaultControllerTest 
{
    @MockBean
    DefaultController defaultController;

    @Mock
    HttpSession httpSession;

    @Mock
    HttpServletRequest request;

    static MockMvc mockMvc;

    @MockBean
    javax.sql.DataSource dataSource;

    @MockBean
    static UsersServices usersServices;

    @BeforeAll
    public static void setUp() 
    {
        final DefaultController controller = new DefaultController(usersServices);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                 .setViewResolvers(new StandaloneMvcTestViewResolver())
                                 .build();
    }

    @Test
    @DisplayName("Testing method /VisitProfile when params are valid")
    @WithMockUser(username = "MariaLima@gmail.com", password = "abcdefg1234")
    public void canGetVisitProfilePage() throws Exception
    {
        //given
        String fullName = "Jasmine White";
        String expectedForwardedURL = "Person.html";

        //when
        when(request.getSession()).thenReturn(httpSession);
        MvcResult mvcResult = mockMvc.perform(get("/VisitProfile")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .param("fullname", fullName))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        String actualForwardedURL = mvcResult.getResponse().getForwardedUrl();

        //then
        assertEquals(expectedForwardedURL, actualForwardedURL);
    }

    @Test
    @DisplayName("Testing method /VisitProfile when params are null")
    public void cannotGetVisitProfilePage() throws Exception
    {
        //given
        String fullName = null;
        int expectedStatusCode = 400;

        //when
        when(request.getSession()).thenReturn(httpSession);
        MvcResult mvcResult= mockMvc.perform(get("/VisitProfile")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("fullname", fullName))
                                    .andExpect(status().isBadRequest())
                                    .andDo(MockMvcResultHandlers.print())
                                    .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("Testing method defaultPage")
    public void canGetDefaultPage() throws Exception
    {
        //given
        String expectedForwardedURL = "index.html";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String actualForwardedURL = mvcResult.getResponse().getForwardedUrl(); 

        //then
        assertEquals(expectedForwardedURL, actualForwardedURL);
    }

    @Test
    @DisplayName("Testing method defaultPage when isNotFound")
    public void cannotGetDefaultPage() throws Exception
    {
        //given
        int expectedStatusCode = 404;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/defaultPage"))
                                     .andExpect(status().isNotFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }
    
    @Test
    @DisplayName("Testing /login ")
    public void canGetLoginPage() throws Exception
    {
        //given
        String expectedForwardedURL = "login.html";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/login"))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        String actualForwardedURL = mvcResult.getResponse().getForwardedUrl();

        //then
        assertEquals(expectedForwardedURL, actualForwardedURL);
    }

    @Test
    @DisplayName("Testing /login when isNotFound")
    public void cannotGetLoginPage() throws Exception
    {
        //given
        int expectedStatusCode = 404;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/log"))
                                     .andExpect(status().isNotFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }
    
    @Test
    @DisplayName("Testing /signup")
    public void canGetSignupPage() throws Exception
    {
        //given
        String expectedForwardedURL = "Signup.html";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/Signup"))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        String actualForwardedURL = mvcResult.getResponse().getForwardedUrl();

        //then
        assertEquals(expectedForwardedURL, actualForwardedURL);
    }

    @Test
    @DisplayName("Testing /signup when isNotFound")
    public void cannotGetSignupPage() throws Exception
    {
        //given
        int expectedStatusCode = 404;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/signUP"))
                                     .andExpect(status().isNotFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then                       
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("Testing /api")
    public void canGetApiPage() throws Exception
    {
        //given
        String expectedForwardedURL = "home.html";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/api"))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        String actualFordwardedURL = mvcResult.getResponse().getForwardedUrl();

        //then                      
        assertEquals(expectedForwardedURL, actualFordwardedURL);
    }

    @Test
    @DisplayName("Testing /api when isNotFound")
    public void cannotGetApiPage() throws Exception
    {
        //given
        int expectedStatusCode = 404;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/Api"))
                                     .andExpect(status().isNotFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("testing /CustomizeProfile")
    public void canGetCustomizeProfilePage() throws Exception
    {
        //given
        String expectedForwardedURL = "DatingUserPersonalData.html";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/CustomizeProfile"))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        String actualForwardedURL = mvcResult.getResponse().getForwardedUrl();

        //then
        assertEquals(expectedForwardedURL, actualForwardedURL);
    }

    @Test
    @DisplayName("testing /CustomizeProfile when isNotFound")
    public void cannotGetCustomizeProfilePage() throws Exception
    {
        //given
        int expectedStatusCode = 404;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/CustomizeUsersProfile"))
                                     .andExpect(status().isNotFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("testing /TestingImages")
    public void cangetTestImagesPage() throws Exception
    {
        //given
        String expectedForwardedURL = "TestingImages.html";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/TestingImages"))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        String actualForwardedURL = mvcResult.getResponse().getForwardedUrl();

        //then
        assertEquals(expectedForwardedURL, actualForwardedURL);
    }
    
    @Test
    @DisplayName("testing /TestingImages when isNotFound")
    public void cannotGetTestImagesPage() throws Exception
    {
        //given
        int expectedStatusCode = 404;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/TestImages"))
                                     .andExpect(status().isNotFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    
    @Test
    @DisplayName("testing /DownloadApp")
    public void canGetDowloadAppPage() throws Exception
    {
        //given
        String expectedForwardedURL = "DownloadApp.html";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/DownloadApp"))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        String actualForwardedURL = mvcResult.getResponse().getForwardedUrl();

        //then
        assertEquals(expectedForwardedURL, actualForwardedURL);
    }
    
    @Test
    @DisplayName("testing /DownloadApp when isNotFound")
    public void cannotGetDowloadAppPage() throws Exception
    {
        //given
        int expectedStatusCode = 404;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/downloadApp"))
                                     .andExpect(status().isNotFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }    

    @Test
    @DisplayName("testing /getPremium")
    public void canGetPremiumPage() throws Exception
    {
        //given
        String expectedForwardedURL = "Premium.html";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/getPremium"))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        String actualForwardedURL = mvcResult.getResponse().getForwardedUrl();

        //then
        assertEquals(expectedForwardedURL, actualForwardedURL);
    }
    
    @Test
    @DisplayName("testing /getPremium when isNotFound")
    public void cannotGetPremiumPage() throws Exception
    {
        //given
        int expectedStatusCode = 404;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/Premium"))
                                     .andExpect(status().isNotFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }
   
    @Test
    @DisplayName("testing /findSoulmate")
    public void canFindSoulMatePage() throws Exception
    {
        //given
        String expectedForwardedURL = "findSoulmate.html";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/findSoulmate"))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        String actualForwardedURL = mvcResult.getResponse().getForwardedUrl();

        //then
        assertEquals(expectedForwardedURL, actualForwardedURL);
    }
    
    @Test
    @DisplayName("testing /findSoulmate when isNotFound")
    public void cannotFindSoulMatePage() throws Exception
    {
        //given
        int expectedStatusCode = 404;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/findSoulmates"))
                                     .andExpect(status().isNotFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("testing /ProfilesMatched")
    public void canGetProfilesMatchedPage() throws Exception
    {
        //given
        String expectedForwardedURL = "ProfilesMatched.html";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/ProfilesMatched"))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        String actualForwardedURL = mvcResult.getResponse().getForwardedUrl();

        //then
        assertEquals(expectedForwardedURL, actualForwardedURL);
    }
    
    @Test
    @DisplayName("testing /ProfilesMatched when isNotFound")
    public void cannotGetProfilesMatchedPage() throws Exception
    {
        //given
        int expectedStatusCode = 404;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/ProfileMatched"))
                                     .andExpect(status().isNotFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    //test /PremiumApi
    
}