package dating.dating.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import dating.dating.entity.ChatMessage;
import dating.dating.services.UsersServices;
import static java.lang.String.format;

@Controller
public class DefaultController
{
    private final UsersServices userServices;

    @Autowired
    SimpMessageSendingOperations simpMessageSendingOperations;

    DefaultController(UsersServices userServices)
    {
        this.userServices = userServices;
    }

    @RequestMapping(value = "VisitProfile")
    public String VisitProfile( @RequestParam String fullname,  HttpSession session)
    {
        session.setAttribute("fullnamePerson", fullname);
        return "Person.html";
    }

    @GetMapping("/")
    public String defaultPage()
    {
        return "index.html";
    }  

    @GetMapping(value= "/login")
    public String login()
    {
        return "login.html";
    }

    @GetMapping(value= "/Signup")
    public String SignUp()
    {
        return "Signup.html";
    }

    @GetMapping(value="/api")
    public String api(HttpSession session)
    {
        System.out.println(session.getAttribute("email"));
        return "home.html";
    }

    @GetMapping(value="/CustomizeProfile")
    public String CustomizeProfile()
    {
        return "DatingUserPersonalData.html";
    }

   @GetMapping(value="/TestingImages")
    public String testingImages(@PathParam("email")String email, HttpSession httpSession)
    {
        httpSession.setAttribute("email", email);
        return "TestingImages.html";
    }

    @GetMapping(value="/DownloadApp")
    public String download()
    {
        return "DownloadApp.html";
    }

    @GetMapping(value="/getPremium")
    public String getPremium()
    {
        return "Premium.html";
    }

    @GetMapping(value="/findSoulmate")
    public String findSoulmate()
    {
        return "findSoulmate.html";
    }

    @GetMapping(value="/ProfilesMatched")
    public String profileMatching()
    {
        return "ProfilesMatched.html";
    }

    @GetMapping(value="/PremiumApi")
    public String prem_api(HttpSession session,HttpServletResponse httpServletResponse)
    {
        System.out.println(session.getAttribute("role").toString());
        if (session.getAttribute("role").toString().equals("0"))
        {   String projectUrl="http://localhost:8080/api";
            httpServletResponse.setHeader("Location", projectUrl);
            httpServletResponse.setStatus(302);
        }
        return "PremiumProfile.html";
    }

    @RequestMapping(value = "/success",  method = RequestMethod.GET)
    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response,HttpSession http, HttpSession session) throws Exception 
    {   
        char isPrem=0;
        String email="";

        if( http.getAttribute("email").toString() == "null")
        {
            email = session.getAttribute("userEmailFromSignup").toString();
            isPrem = userServices.getUserRole(email);
        }

        else
        {
            email = http.getAttribute("email").toString();
            isPrem = userServices.getUserRole(email);
            //System.out.println("IsPrem is equal to:"+isPrem);
            http.setAttribute("role", isPrem);
        }

        if(isPrem == '1')
        {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/PremiumApi"));                        
        }

        else if(isPrem == '0') 
        {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/api"));
        }
    }

    @MessageMapping("/chat/{fullname}/sendMessage")

    public void sendMessage (@DestinationVariable String fullname, @Payload ChatMessage chatMessage)
    {
        System.out.println("Message send for "+ fullname + " with context " + chatMessage.getContent() + "  " + chatMessage.getRecipientFullname());
        simpMessageSendingOperations.convertAndSend(format("/topic/%s", fullname), chatMessage);
    }

    @MessageMapping("/chat/{fullname}/addUser")
    public void addUser(@DestinationVariable String fullname, @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor  simpleMessageHeaderAccessor)
    {
        System.out.println("Accepting user " + chatMessage.getSender());
        simpleMessageHeaderAccessor.getSessionAttributes()
                                   .put("username",chatMessage.getSender());

        simpMessageSendingOperations.convertAndSend(format("/topic/%s", fullname), chatMessage);
    }
}