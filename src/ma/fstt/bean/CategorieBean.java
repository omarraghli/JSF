package ma.fstt.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ma.fstt.persistence.Categorie;
import ma.fstt.persistence.DatabaseOperations;

@ApplicationScoped
@ManagedBean(name = "CategorieBean")
@MultipartConfig
public class CategorieBean {
	private int idCat;
	private String nomCat;
	private Part img;
	private StreamedContent image;

	public String save(CategorieBean bean) throws IOException {
		InputStream input = img.getInputStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[10240];
		for (int length = 0; (length = input.read(buffer)) > 0;)
			output.write(buffer, 0, length);
		return DatabaseOperations.creerCategorie(bean.nomCat, output.toByteArray());
	}

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			this.image = new DefaultStreamedContent();
			return this.image;
		} else {
			String imageId = context.getExternalContext().getRequestParameterMap().get("idCat");
			// System.out.println("IdCat : " + imageId);
			byte[] myImage = null;
			for (Categorie cat : listCategories()) {
				if (cat.getIdCat() == Integer.valueOf(imageId)) {
					myImage = cat.getImgCat();
					break;
				}
			}
			this.image = new DefaultStreamedContent(new ByteArrayInputStream(myImage));
			return this.image;
		}
	}

	public List<Categorie> listCategories() {
		return DatabaseOperations.getAllCategories();
	}

	public String redirectUpdate(int idCat) {
		return "updateCategorie.xhtml?faces-redirect=true&idCat="+idCat;
	}
	
	public String redirectProduit(int idCat)
	{
		return "listProduitsByCat.xhtml?faces-redirect=true&idCat="+idCat;
	}
	
	public String redirectDelete(int idCat)
	{
		return DatabaseOperations.deleteCategorie(idCat);
	}
	
	public int setCategorie(int id)
	{
		this.idCat = DatabaseOperations.getCategorieById(id).getIdCat();
		this.nomCat = DatabaseOperations.getCategorieById(id).getNomCat();
		return this.idCat;
	}

	public String updateCategorie(CategorieBean bean) throws IOException
	{
		InputStream input = img.getInputStream();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[10240];
		for (int length = 0; (length = input.read(buffer)) > 0;)
			output.write(buffer, 0, length);
		
		Categorie cat = new Categorie(bean.idCat, bean.nomCat, output.toByteArray());
		System.out.println("$$$$$$$$$$$$$$$$$$******************$"+cat.getIdCat());
		return DatabaseOperations.updateCategorie(cat);
	}
	// Redirection vers la page de modification
//	public String redirectUpdate(int idCat) {
//		return "updateCategorie.xhtml?faces-redirect=true&idCat=" + idCat;
//	}
//
//	// Redirection vers les produits qui ont id_cat = idCat
//	public String redirectProduit(int idCat) {
//		return "listProduitsByCat.xhtml?faces-redirect=true&idCat=" + idCat;
//	}
//
//	// Pour récupérer le id passé dans l'url
//	public int setCategorie(int idCat) {
//		this.idCat = DatabaseOperations.getCategorieById(idCat).getIdCat();
//		this.nomCat = DatabaseOperations.getCategorieById(idCat).getNomCat();
//		return this.idCat;
//	}
//
//	public String update(CategorieBean bean) throws IOException {
//		InputStream input = img.getInputStream();
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		byte[] buffer = new byte[10240];
//		for (int length = 0; (length = input.read(buffer)) > 0;)
//			output.write(buffer, 0, length);
//		Categorie cat = new Categorie(bean.idCat, bean.nomCat, output.toByteArray());
//		System.out.println("------ id   " + bean.getIdCat() + "------ nom   " + bean.getNomCat());
//		return DatabaseOperations.updateCategorie(cat);
//	}
//
//	public String delete(int idCat) {
//		return DatabaseOperations.deleteCategorie(idCat);
//	}

	public void setImage(StreamedContent image) {
		this.image = image;
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

	public Part getImg() {
		return img;
	}

	public void setImg(Part img) {
		this.img = img;
	}
}
