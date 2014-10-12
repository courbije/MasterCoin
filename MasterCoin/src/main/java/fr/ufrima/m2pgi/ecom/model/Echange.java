package fr.ufrima.m2pgi.ecom.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class Echange implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id;
  
   @Column
   @NotNull
   @Min(0)
   private Integer vendre;

   @Column
   @NotNull
   @Min(0)
   private Integer contre;

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
      if (!(obj instanceof Echange))
      {
         return false;
      }
      Echange other = (Echange) obj;
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

   public Integer getVendre()
   {
      return vendre;
   }

   public void setVendre(Integer vendre)
   {
      this.vendre = vendre;
   }

   public Integer getContre()
   {
      return contre;
   }

   public void setContre(Integer contre)
   {
      this.contre = contre;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      if (vendre != null)
         result += ", vendre: " + vendre;
      if (contre != null)
         result += ", contre: " + contre;
      return result;
   }
}