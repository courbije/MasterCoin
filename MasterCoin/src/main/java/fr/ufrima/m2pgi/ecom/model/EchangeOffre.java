package fr.ufrima.m2pgi.ecom.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class EchangeOffre implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "idc", nullable = false)
   private Compte compte;

   @ManyToOne
   @JoinColumn(name = "idmV", nullable = false)
   private Monnaie monnaieVendre;

   @ManyToOne
   @JoinColumn(name = "idmA", nullable = false)
   private Monnaie monnaieAchat;

   @Column(nullable = false)
   private Integer montantVendre;

   @Column(nullable = false)
   private Integer montantAchat;

   @Column(nullable = false)
   @Temporal(TemporalType.DATE)
   private Date dateCreation;
   
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
      if (!(obj instanceof EchangeOffre))
      {
         return false;
      }
      EchangeOffre other = (EchangeOffre) obj;
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

   public Compte getCompte()
   {
      return this.compte;
   }

   public void setCompte(final Compte compte)
   {
      this.compte = compte;
   }

   public Monnaie getMonnaieVendre()
   {
      return this.monnaieVendre;
   }

   public void setMonnaieVendre(final Monnaie monnaieVendre)
   {
      this.monnaieVendre = monnaieVendre;
   }

   public Monnaie getMonnaieAchat()
   {
      return this.monnaieAchat;
   }

   public void setMonnaieAchat(final Monnaie monnaieAchat)
   {
      this.monnaieAchat = monnaieAchat;
   }

   public Integer getMontantVendre()
   {
      return montantVendre;
   }

   public void setMontantVendre(Integer montantVendre)
   {
      this.montantVendre = montantVendre;
   }

   public Integer getMontantAchat()
   {
      return montantAchat;
   }

   public void setMontantAchat(Integer montantAchat)
   {
      this.montantAchat = montantAchat;
   }

   public Date getDateCreation()
   {
      return dateCreation;
   }

   public void setDateCreation(Date dateCreation)
   {
      this.dateCreation = dateCreation;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (montantVendre != null)
         result += "montantVendre: " + montantVendre;
      if (montantAchat != null)
         result += ", montantAchat: " + montantAchat;
      return result;
   }
}