package fr.ufrima.m2pgi.ecom.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
public class Compte implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;

   @Column(nullable = false, unique=true)
   @NotNull
   @NotEmpty
   @Length(min = 6, max = 20)
   private String login;

   @Column(nullable = false)
   @NotNull
   @NotEmpty
   @Length(min = 6, max = 20)
   private String password;

   @Column(nullable = false, unique=true)
   @NotNull
   @NotEmpty
   @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
	        +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
	        +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
	             message="{invalid.email}")
   private String mail;

   @Column(nullable = false)
   @NotNull
   @NotEmpty
   private String nom;

   @Column(nullable = false)
   @NotNull
   @NotEmpty
   private String prenom;

   @Column(nullable = false)
   @Temporal(TemporalType.DATE)
   @NotNull
   private Date dateNaissance;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof Compte))
      {
         return false;
      }
      Compte other = (Compte) obj;
      if (id != null)
      {
         if (!id.equals(other.id))
         {
            return false;
         }
      }
      return true;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   public String getLogin()
   {
      return login;
   }

   public void setLogin(String login)
   {
      this.login = login;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }

   public String getMail()
   {
      return mail;
   }

   public void setMail(String string)
   {
      this.mail = string;
   }

   public String getNom()
   {
      return nom;
   }

   public void setNom(String nom)
   {
      this.nom = nom;
   }

   public String getPrenom()
   {
      return prenom;
   }

   public void setPrenom(String prenom)
   {
      this.prenom = prenom;
   }

   public Date getDateNaissance()
   {
      return dateNaissance;
   }

   public void setDateNaissance(Date dateNaissance)
   {
      this.dateNaissance = dateNaissance;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      if (login != null && !login.trim().isEmpty())
         result += ", login: " + login;
      if (password != null && !password.trim().isEmpty())
         result += ", password: " + password;
      if (mail != null)
         result += ", mail: " + mail;
      if (nom != null && !nom.trim().isEmpty())
         result += ", nom: " + nom;
      if (prenom != null && !prenom.trim().isEmpty())
         result += ", prenom: " + prenom;
      if (dateNaissance != null)
         result += ", dateNaissance: " + dateNaissance;
      return result;
   }
}