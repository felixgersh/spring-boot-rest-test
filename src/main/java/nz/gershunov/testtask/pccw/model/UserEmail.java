package nz.gershunov.testtask.pccw.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user_emails")
@EntityListeners(AuditingEntityListener.class)
public class UserEmail
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "email_text", nullable = false)
    private String emailText;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
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
