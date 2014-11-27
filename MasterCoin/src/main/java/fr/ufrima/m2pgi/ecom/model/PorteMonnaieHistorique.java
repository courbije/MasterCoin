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
public class PorteMonnaieHistorique implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "idc", nullable = false)
	private Compte compte;

	@ManyToOne
	@NotNull
	@JoinColumn(name = "idm", nullable = false)
	private Monnaie monnaie;

	@Column(nullable = false)
	@NotNull
	private Double montant;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PorteMonnaieHistorique)) {
			return false;
		}
		PorteMonnaieHistorique other = (PorteMonnaieHistorique) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Compte getCompte() {
		return this.compte;
	}

	public void setCompte(final Compte compte) {
		this.compte = compte;
	}

	public Monnaie getMonnaie() {
		return this.monnaie;
	}

	public void setMonnaie(final Monnaie monnaie) {
		this.monnaie = monnaie;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (montant != null)
			result += "montant: " + montant;
		return result;
	}
}
