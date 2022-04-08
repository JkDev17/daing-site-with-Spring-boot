package dating.dating.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dating.dating.services.UsersServices;

@Controller
public class DefaultController
{

    @Autowired
    UsersServices userService;

    /*@RequestMapping(value= "/VisitProfile")
    public String visitPerson(Model model) 
    {
        String userFullname = "John";
        System.out.println(userFullname);
        model.addAttribute("userFullname", userFullname);
        System.out.println(model);
        return "Person";
    }*/


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
    public String testingImages()
    {
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
            isPrem = userService.getUserRole(email);
        }

        else
        {
            email = http.getAttribute("email").toString();
            isPrem = userService.getUserRole(email);
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
}