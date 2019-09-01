package nz.gershunov.testtask.pccw.model;

import nz.gershunov.testtask.pccw.exception.BadRequestException;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_address", nullable = false)
    private String email;

    @Column(name = "is_deleted", nullable = false, columnDefinition="tinyint(1) default 0")
    private byte isDeleted;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public byte getIsDeleted()
    {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public void validate() throws BadRequestException
    {
        if ((firstName == null) || (firstName.trim().length() < 1)) {
            throw new BadRequestException("First name is missing or empty");
        }
        if ((lastName == null) || (lastName.trim().length() < 1)) {
            throw new BadRequestException("Last name is missing or empty");
        }
        if ((email == null) || (email.trim().length() < 6)) {
            throw new BadRequestException("Email is missing or incorrect");
        }
        setFirstName(firstName.trim());
        setLastName(lastName.trim());
        setEmail(email.trim());
    }

}
