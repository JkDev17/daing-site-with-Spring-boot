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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import dating.dating.services.UsersServices;


@WebMvcTest(DefaultController.class)
public class DefaultControllerTest 
{
    @MockBean
    DefaultController defaultController;

    @MockBean
    HttpSession httpSession;

    @MockBean
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
    public void canVisitProfile() throws Exception
    {
        //given
        String fullName = "Jasmine White";
        String exprectedForwardedURL = "Person.html";
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
        assertEquals(exprectedForwardedURL, actualForwardedURL);
    }

    @Test
    @DisplayName("Testing method defaultPage")
    public void canGetDefaultPage() throws Exception
    {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

}