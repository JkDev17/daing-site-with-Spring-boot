package dating.dating.services;

import java.sql.Date;
import java.text.ParseException;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import dating.dating.entity.Users;
import dating.dating.entity.Images;
import dating.dating.entity.UserHasImages;
import dating.dating.repositories.ImagesRepository;
import dating.dating.repositories.UserHasImagesRepository;
import dating.dating.repositories.UsersRepository;

@Service
public class UsersServices
{
    @Autowired
    UsersRepository userRepo;

    @Autowired
    UserHasImagesRepository userHasImagesRepository;

    @Autowired
    ImagesRepository imagesRepository;

    Logger LOGGER = LoggerFactory.getLogger(UsersServices.class);
    
    public String checkDuplicateEmail(String email)
    {
        Optional <String> u=userRepo.findByEmail(email);
        if(u.isPresent())
        {
            String res="taken";
            return res;
        }
        return "new";
    }


    public char getUserRole(String email)
    {
        return userRepo.userIsPrem(email);
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

        userRepo.saveAndFlush(new Users(session.getAttribute("userEmailFromSignup").toString(),  
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

        userId = userRepo.getLastId();
        imageId = imagesRepository.getLastId();
        userHasImagesRepository.saveAndFlush(new UserHasImages(userId, imageId, '1'));
        LOGGER.info("User created with id:"+ userId+" and email:"+ session.getAttribute("userEmailFromSignup").toString());
    }
}