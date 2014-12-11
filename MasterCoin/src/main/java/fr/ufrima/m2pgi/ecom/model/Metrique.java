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
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class Metrique implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;

    @ManyToOne
	@JoinColumn(name = "idm1", nullable = false)
	private Monnaie monnaie1;

    @ManyToOne
	@JoinColumn(name = "idm2", nullable = false)
	private Monnaie monnaie2;

   @Column(nullable = false)
   @NotNull
   private Double montantMonnaie1;

   @Column(nullable = false)
   @NotNull
   private Double montantMonnaie2;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

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
      if (!(obj instanceof Metrique))
      {
         return false;
      }
      Metrique other = (Metrique) obj;
      if (id != null)
      {
         if (!id.equals(other.id))
         {
            return false;
         }
      }
      return true;
   }

   public Monnaie getMonnaie1() {
		return monnaie1;
	}

	public void setMonnaie1(Monnaie monnaie1) {
		this.monnaie1 = monnaie1;
	}

	public Monnaie getMonnaie2() {
		return monnaie2;
	}

	public void setMonnaie2(Monnaie monnaie2) {
		this.monnaie2 = monnaie2;
	}

	public Double getMontantMonnaie1() {
		return montantMonnaie1;
	}

	public void setMontantMonnaie1(Double montantMonnaie1) {
		this.montantMonnaie1 = montantMonnaie1;
	}

	public Double getMontantMonnaie2() {
		return montantMonnaie2;
	}

	public void setMontantMonnaie2(Double montantMonnaie2) {
		this.montantMonnaie2 = montantMonnaie2;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
   
   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
   }
   
   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      if (monnaie1 != null)
         result += ", monnaie1" + monnaie1;
      if (monnaie2 != null)
    	  result += ", monnaie2" + monnaie2;
      if (montantMonnaie1 != null)
         result += ", montantMonnaie1: " + montantMonnaie1;
      if (montantMonnaie1 != null)
         result += ", montantMonnaie2: " + montantMonnaie2;
      if (date != null)
         result += ", date: " + date;
      return result;
   }


}