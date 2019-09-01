package nz.gershunov.testtask.pccw.model;

import nz.gershunov.testtask.pccw.model.User;
import nz.gershunov.testtask.pccw.model.UserEmail;
import java.util.Date;

public class UserFull extends User
{
    private String emailText;

    public UserFull(User user, UserEmail userEmail)
    {
        setId(user.getId());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setEmail(user.getEmail());
        setIsDeleted(user.getIsDeleted());
        setCreatedAt(user.getCreatedAt());
        setUpdatedAt(user.getUpdatedAt());
        if (userEmail != null) {
            emailText = userEmail.getEmailText();
        } else {
            emailText = "";
        }
    }

    public String getEmailText()
    {
        return emailText;
    }

    public void setEmailText(String emailText)
    {
        this.emailText = emailText;
    }

}
