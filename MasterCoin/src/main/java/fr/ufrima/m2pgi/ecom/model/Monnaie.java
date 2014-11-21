package fr.ufrima.m2pgi.ecom.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
public class Monnaie implements Serializable
{
	

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", updatable = false, nullable = false)
   private Long idMonnaie;

   @Column(nullable = false)
   @NotNull
   @NotEmpty
   private String nom;

   @Column(nullable = false)
   @NotNull
   @NotEmpty
   private String acroyme;
   
   public Long getId()
   {
      return this.idMonnaie;
   }

   public void setId(final Long id)
   {
      this.idMonnaie = id;
   }

   public String getNom()
   {
      return nom;
   }

   public void setNom(String Nom)
   {
      this.nom = Nom;
   }

   public String getAcroyme()
   {
      return acroyme;
   }

   public void setAcroyme(String acroyme)
   {
      this.acroyme = acroyme;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof Monnaie))
      {
         return false;
      }
      Monnaie other = (Monnaie) obj;
      if (idMonnaie != null)
      {
         if (!idMonnaie.equals(other.idMonnaie))
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
      result = prime * result + ((idMonnaie == null) ? 0 : idMonnaie.hashCode());
      return result;
   }

@Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (idMonnaie != null)
         result += "idMonnaie: " + idMonnaie;
      if (nom != null && !nom.trim().isEmpty())
         result += ", Nom: " + nom;
      if (acroyme != null && !acroyme.trim().isEmpty())
         result += ", acroyme: " + acroyme;
      return result;
   }
}