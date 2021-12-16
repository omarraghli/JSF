package ma.fstt.persistence;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categorie")
public class Categorie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private int idCat;

	@Column(nullable = true, length = 45)
	private String nomCat;

	@Column(nullable = true, length = 52428800)
	@Lob
	private byte[] imgCat;

	@OneToMany
	@JoinColumn(name = "id_cat")
	private List<Produit> listProd;

	public Categorie() {
		super();
	}

	public Categorie(int idCat, String nomCat, byte[] imgCat) {
		super();
		this.idCat = idCat;
		this.nomCat = nomCat;
		this.imgCat = imgCat;
	}

	public int getIdCat() {
		return idCat;
	}

	public void setIdCat(int idCat) {
		this.idCat = idCat;
	}

	public String getNomCat() {
		return nomCat;
	}

	public void setNomCat(String nomCat) {
		this.nomCat = nomCat;
	}

	public byte[] getImgCat() {
		return imgCat;
	}

	public void setImgCat(byte[] imgCat) {
		this.imgCat = imgCat;
	}

	public List<Produit> getListProd() {
		return listProd;
	}

	public void setListProd(List<Produit> listProd) {
		this.listProd = listProd;
	}

	@Override
	public String toString() {
		return "Categorie [idCat=" + idCat + ", nomCat=" + nomCat + "]";
	}

}
