package dating.dating.security;

import java.io.Serializable;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements Serializable
{
  
  @Autowired
  private DataSource dataSource;
  
    @Autowired
    public void configAuthentication( AuthenticationManagerBuilder auth) throws Exception
    {
      auth.jdbcAuthentication()
          .passwordEncoder(new BCryptPasswordEncoder())
          .dataSource(dataSource)
          .usersByUsernameQuery("SELECT email , password, enabled  FROM USERS WHERE email=?")
          .authoritiesByUsernameQuery("SELECT email, password, role FROM USERS where email=?");
    }

  @Override
  protected void configure(HttpSecurity http) throws Exception  
  {
    http.authorizeRequests()
    .antMatchers("/" , "/Signup"  , "/checkDuplicateEmail" , "/success",  "/saveVarsToSession" , "/getSessionData" ,
    "/CustomizeProfile" , "/fetchUserPersonalDataIdEq2" ,  "/fetchUserPersonalDataProfilePic", "/TestingImagesApi", "/TestingImages",
    "/fetchUserPersonalDataIdEq3", "/fetchUserPersonalDataIdEq4" , "/saveDataToDatabase" , "/sessionEmail", "/getUserLogInEmail" ,
    "/TestingImagesApi-v2", "/TestingImagesApi-v3", "/fetchDataFromDatabase" , "/fetchUserVisitedUsersList", "/fetchBasicDataFromDB"
    , "/fetchStarFromUsersNeqId", "/findSoulmate", "/fetchStarFromUImagesNeqToId", "/updateUserVisitedUser", "/FilterProfiles",
    "/VisitProfile").permitAll()
    //.antMatchers("/PremiumApi").hasRole("PREMIUM_USER") 
    .anyRequest().authenticated()
    .and()
    .formLogin()
    .loginPage("/login")
    .permitAll()
    .defaultSuccessUrl("/success",true)
    .failureUrl("/login?error")
    .and()
    .csrf().disable()
    .sessionManagement()
    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
  }

  @Override
  public void configure(WebSecurity web) throws Exception 
  {
    web.ignoring().antMatchers("/icons/*","/**/*.css","/static/**");//provide access to resources folder
  }
} 