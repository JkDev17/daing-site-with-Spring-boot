package dating.dating.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import dating.dating.services.UsersServices;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class RstControllerTest 
{
    @MockBean
    UsersServices usersServices;

    @InjectMocks
    RstController rstController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    javax.sql.DataSource dataSource;

    @MockBean
    HttpSession httpSession;

    @MockBean
    MockHttpSession mockHttpSession;

    @MockBean
    HttpServletRequest request;

    @Autowired
    ObjectMapper objectMapper;

    public String convertMaptoStringWithStream(HashMap<String, ?> map) 
    {
        String mapAsString = map.keySet().stream()
          .map(key -> key + "=" + map.get(key))
          .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }

    @Test
    @DisplayName("Testing method checkDuplicateEmail with taken as result")
    public void canCheckDuplicateEmailWhenResTaken() throws Exception
    {
        //given
        String email = "JasmineWhite@gmail.com";
        String expectedResponse = "taken";

        //when
        when(usersServices.checkDuplicateEmail(email)).thenReturn("taken");
        MvcResult mvcResult = mockMvc.perform(get("/checkDuplicateEmail")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .param("email", email))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();
        
        String actualResponse = mvcResult.getResponse().getContentAsString();

        //then
        assertEquals(expectedResponse, actualResponse);
        verify(usersServices,times(1)).checkDuplicateEmail(email);
    }

    @Test
    @DisplayName("Testing method checkDuplicateEmail with new as result")
    public void canCheckDuplicateEmailWhenResNew() throws Exception
    {
        //given
        String email = "JasmineWhite@gmail.com";
        String expectedResponse = "new";

        //when
        when(usersServices.checkDuplicateEmail(email)).thenReturn("new");
        MvcResult mvcResult = mockMvc.perform(get("/checkDuplicateEmail")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .param("email", email))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();
        
        String actualResponse = mvcResult.getResponse().getContentAsString();

        //then
        assertEquals(expectedResponse, actualResponse);
        verify(usersServices,times(1)).checkDuplicateEmail(email);
    }

    @Test
    @DisplayName("Testing method checkDuplicateEmail when wrong URL(gets redirected to LOGIN hence the 302 status)")
    public void cannotCheckDuplicateEmailWhenWrongURL() throws Exception
    {
        //given
        int expectedStatusCode = 302;// redirection to Login page
        String email = "JasmineWhite@gmail.com";

        //when
        MvcResult mvcResult = mockMvc.perform(get("/checkDuplicate")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .param("email", email))
                                     .andExpect(status().isFound()) //redirection to Login page
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();
        
        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Test
    @DisplayName("Testing method checkDuplicateEmail when null as param")
    public void cannotCheckDuplicateEmailNullAsParam() throws Exception
    {
        //given
        int expectedStatusCode = 400;
        String email = null;

        //when
        when(usersServices.checkDuplicateEmail(email)).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(get("/checkDuplicateEmail")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .param("email", email))
                                     .andExpect(status().isBadRequest())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();
        
        int actualStatusCode = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatusCode, actualStatusCode);
    }
    
    @Test
    @DisplayName("Testing /saveVarsToSession when no null values as params")
    public void canSaveVarsToSession() throws Exception
    {
        //given
        int expectedStatus = 200;
        String email = "JasmineWhite@gmail.com";
        String password = "abcdefg1234";
        String age = "21";
        String userFullname = "Jasmine White";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put("email", Collections.singletonList(email));
        parameters.put("userPassword", Collections.singletonList(password));
        parameters.put("userAge", Collections.singletonList(age));
        parameters.put("userFullName", Collections.singletonList(userFullname));
        
        final MvcResult result = mockMvc.perform(post("/saveVarsToSession")
                        .params(parameters)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk())
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn();

        int actualStatus = result.getResponse().getStatus();

        //then
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @DisplayName("Testing /saveVarsToSession when wrong URL")
    public void cannotSaveVarsToSession() throws Exception
    {
        //given
        int expectedStatus = 302;
        String email = "JasmineWhite@gmail.com";
        String password = "abcdefg1234";
        String age = "21";
        String userFullname = "Jasmine White";
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put("email", Collections.singletonList(email));
        parameters.put("userPassword", Collections.singletonList(password));
        parameters.put("userAge", Collections.singletonList(age));
        parameters.put("userFullName", Collections.singletonList(userFullname));
        
        final MvcResult result = mockMvc.perform(post("/saveToSession")
                        .params(parameters)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isFound())
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn();

        int actualStatus = result.getResponse().getStatus();

        //then
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @DisplayName("Testing /getSessionData")
    public void canGetSessionData() throws Exception
    {
        //given
        int expectedStatus = 200;
        String email = "JasmineWhite@gmail.com";
        String fullname = "Jasmine White";
        httpSession.setAttribute("userEmailFromSignUp", email);
        httpSession.setAttribute("userFullnameFromSignUp", fullname);
        HashMap <String,String> map = new HashMap<String,String>();
        map.put("userEmailFromSignUp", email);
        map.put("userFullnameFromSignUp", fullname);

        //when
        when(usersServices.serveDatatoGetSession(httpSession)).thenReturn(map);
        MvcResult mvcresult = mockMvc.perform(get("/getSessionData")
                .sessionAttr("email",email)
                .sessionAttr("fullname",fullname)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        int actualStatus = mvcresult.getResponse().getStatus();

        //then
        assertEquals(expectedStatus, actualStatus);
    }


    @Test
    @DisplayName("Testing /getSessionData with wrong URL")
    public void cannotGetSessionData() throws Exception
    {
        //given
        int expectedStatus = 302;
        String email = "JasmineWhite@gmail.com";
        String fullname = "Jasmine White";
        httpSession.setAttribute("userEmailFromSignUp", email);
        httpSession.setAttribute("userFullnameFromSignUp", fullname);
        HashMap <String,String> map = new HashMap<String,String>();
        map.put("userEmailFromSignUp", email);
        map.put("userFullnameFromSignUp", fullname);

        //when
        when(usersServices.serveDatatoGetSession(httpSession)).thenReturn(map);
        MvcResult mvcresult = mockMvc.perform(get("/getData")
                .sessionAttr("email",email)
                .sessionAttr("fullname",fullname)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isFound())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        int actualStatus = mvcresult.getResponse().getStatus();

        //then
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @DisplayName("Testing /fetchUserPersonalDataIdEq2")
    public void canFetchUserPersonalDataIdEq2() throws Exception
    {
        //given
        int expectedStatus = 200;
        String gender = "female";
        String bday = "1996-06-16";
        String educationLevel = "Bachelor";
        String job = "Software Developer";
        String hobbies = "Traveling Music Gym Fashion";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put("userGender", Collections.singletonList(gender));
        parameters.put("userBday", Collections.singletonList(bday));
        parameters.put("userEducationlevel", Collections.singletonList(educationLevel));
        parameters.put("userJob", Collections.singletonList(job));
        parameters.put("userHobbies", Collections.singletonList(hobbies));

        //when
        MvcResult mvcResult = mockMvc.perform(post("/fetchUserPersonalDataIdEq2")
                                     .params(parameters)
                                     .param("request", request.toString())
                                     .contentType(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @DisplayName("Testing /fetchUserPersonalDataIdEq2 when wrong URL")
    public void cannotFetchUserPersonalDataIdEq2() throws Exception
    {
        //given
        int expectedStatus = 302;
        String gender = "female";
        String bday = "1996-06-16";
        String educationLevel = "Bachelor";
        String job = "Software Developer";
        String hobbies = "Traveling Music Gym Fashion";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put("userGender", Collections.singletonList(gender));
        parameters.put("userBday", Collections.singletonList(bday));
        parameters.put("userEducationlevel", Collections.singletonList(educationLevel));
        parameters.put("userJob", Collections.singletonList(job));
        parameters.put("userHobbies", Collections.singletonList(hobbies));

        //when
        MvcResult mvcResult = mockMvc.perform(post("/fetchUserPersonalDataId")
                                     .params(parameters)
                                     .param("request", request.toString())
                                     .contentType(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isFound())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @DisplayName("Testing /fetchUserPersonalDataIdEq3")
    public void canfetchUserPersonalDataIdEq3() throws Exception
    {
        //given
        int expectedStatus = 200;
        String gender = "female";
        String bday = "1996-06-16";
        String educationLevel = "Bachelor";
        String job = "Software Developer";
        String hobbies = "Traveling Music Gym Fashion";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put("userGender", Collections.singletonList(gender));
        parameters.put("userBday", Collections.singletonList(bday));
        parameters.put("userEducationlevel", Collections.singletonList(educationLevel));
        parameters.put("userJob", Collections.singletonList(job));
        parameters.put("userHobbies", Collections.singletonList(hobbies));

        //when
        MvcResult mvcResult = mockMvc.perform(post("/fetchUserPersonalDataIdEq3")
                                    .params(parameters)
                                    .param("request", request.toString())
                                    .contentType(MediaType.APPLICATION_JSON))
                                    .andExpect(status().isOk())
                                    .andDo(MockMvcResultHandlers.print())
                                    .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @DisplayName("Testing /fetchUserPersonalDataIdEq4")
    public void canfetchUserPersonalDataIdEq4() throws Exception
    {
        //given
        int expectedStatus = 200;
        String gender = "female";
        String bday = "1996-06-16";
        String educationLevel = "Bachelor";
        String job = "Software Developer";
        String hobbies = "Traveling Music Gym Fashion";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.put("userGender", Collections.singletonList(gender));
        parameters.put("userBday", Collections.singletonList(bday));
        parameters.put("userEducationlevel", Collections.singletonList(educationLevel));
        parameters.put("userJob", Collections.singletonList(job));
        parameters.put("userHobbies", Collections.singletonList(hobbies));

        //when
        MvcResult mvcResult = mockMvc.perform(post("/fetchUserPersonalDataIdEq4")
                                    .params(parameters)
                                    .param("request", request.toString())
                                    .contentType(MediaType.APPLICATION_JSON))
                                    .andExpect(status().isOk())
                                    .andDo(MockMvcResultHandlers.print())
                                    .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    @DisplayName("/saveDataToDatabase")
    public void canSaveDataToDatabase() throws Exception
    {
        //given
        int expectedStatus =200;

        //when
        MvcResult mvcResult = mockMvc.perform(get("/saveDataToDatabase")
                                     .session(mockHttpSession)
                                     .contentType(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andDo(MockMvcResultHandlers.print())
                                     .andReturn();

        int actualStatus = mvcResult.getResponse().getStatus();

        //then
        assertEquals(expectedStatus, actualStatus);
        //then
    }

    //todo test "/sessionEmail"
    
    
}