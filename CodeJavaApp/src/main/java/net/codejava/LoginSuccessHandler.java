package net.codejava;
 
import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
 
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 
	//méthode permettant la redirection en fonction du role de l'utilisateur quand il se connecte
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
 
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
         
        String redirectURL = request.getContextPath();
         
        if (userDetails.hasRole("Internaute")) {
            redirectURL = "internaute";
        } else if (userDetails.hasRole("Employe")) {
            redirectURL = "employe";
        } else if (userDetails.hasRole("Responsable")) {
            redirectURL = "responsable";
        }else if (userDetails.hasRole("Directeur")) {
            redirectURL = "directeur";
        }
         
        response.sendRedirect(redirectURL);
         
    }
 
}