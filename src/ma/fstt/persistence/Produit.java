package ma.fstt.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produit")
public class Produit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private int idProd;

	@Column(nullable = true, length = 45)
	private String nomProd;

	@Column(nullable = true)
	private int QuantiteProd;

	@Column(nullable = true)
	private Float puProd;

	@Column(nullable = true, length = 500)
	private String descProd;
	
	@ManyToOne
	@JoinColumn(name = "id_cat")
	private Categorie cat;

	public Produit() {
		super();
	}

	public Produit(int idProd, String nomProd, Integer quantiteProd, Float puProd, String descProd, int idCat) {
		super();
		this.idProd = idProd;
		this.nomProd = nomProd;
		this.QuantiteProd = quantiteProd;
		this.puProd = puProd;
		this.descProd = descProd;
		this.cat.setIdCat(idCat);
	}

	public int getIdProd() {
		return idProd;
	}

	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}

	public String getNomProd() {
		return nomProd;
	}

	public void setNomProd(String nomProd) {
		this.nomProd = nomProd;
	}

	public int getQuantiteProd() {
		return QuantiteProd;
	}

	public void setQuantiteProd(int quantiteProd) {
		QuantiteProd = quantiteProd;
	}

	public Float getPuProd() {
		return puProd;
	}

	public void setPuProd(Float puProd) {
		this.puProd = puProd;
	}

	public String getDescProd() {
		return descProd;
	}

	public void setDescProd(String descProd) {
		this.descProd = descProd;
	}

	public Categorie getCat() {
		return cat;
	}

	public void setCat(Categorie cat) {
		this.cat = cat;
	}

	@Override
	public String toString() {
		return "Produit [idProd=" + idProd + ", nomProd=" + nomProd + ", QuantiteProd=" + QuantiteProd + ", puProd="
				+ puProd + ", descProd=" + descProd + "]";
	}
}
