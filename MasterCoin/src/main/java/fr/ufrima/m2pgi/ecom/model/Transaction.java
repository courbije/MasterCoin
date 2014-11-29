
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
public class Transaction implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "idt", updatable = false, nullable = false)
   private Long idt;

   @ManyToOne
   @JoinColumn(name = "idcV", nullable = false)
   private Compte compteVendeur;

   @ManyToOne
   @JoinColumn(name = "idcA", nullable = false)
   private Compte compteAcheteur;
   
   @ManyToOne
   @JoinColumn(name = "idmV", nullable = false)
   private Monnaie monnaieVendre;

   @ManyToOne
   @JoinColumn(name = "idmA", nullable = false)
   private Monnaie monnaieAchat;

   @Column(nullable = false)
   private Double montantVendre;

   @Column(nullable = false)
   private Double montantAchat;

   @Column(nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date dateCreation;
   
   @Column(nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date dateValidation;
   
   public Long getId()
   {
      return this.idt;
   }

   public void setId(final Long id)
   {
      this.idt = id;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
      {
         return true;
      }
      if (!(obj instanceof Transaction))
      {
         return false;
      }
      Transaction other = (Transaction) obj;
      if (idt != null)
      {
         if (!idt.equals(other.idt))
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
      result = prime * result + ((idt == null) ? 0 : idt.hashCode());
      return result;
   }

   public Compte getCompteVendeur()
   {
      return this.compteVendeur;
   }

   public void setCompteVendeur(final Compte compte)
   {
      this.compteVendeur = compte;
   }

   public Compte getCompteAcheteur()
   {
      return this.compteAcheteur;
   }

   public void setCompteAcheteur(final Compte compte)
   {
      this.compteAcheteur = compte;
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

   public Double getMontantVendre()
   {
      return montantVendre;
   }

   public void setMontantVendre(Double montantVendre)
   {
      this.montantVendre = montantVendre;
   }

   public Double getMontantAchat()
   {
      return montantAchat;
   }

   public void setMontantAchat(Double montantAchat)
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
   
   public Date getDateValidation()
   {
      return dateValidation;
   }

   public void setDateValidation(Date dateCreation)
   {
      this.dateValidation = dateCreation;
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