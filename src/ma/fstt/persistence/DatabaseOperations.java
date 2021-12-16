package ma.fstt.persistence;

import java.awt.Image;
import java.io.InputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class DatabaseOperations {

	private static final String PERSISTENCE_UNIT_NAME = "unit";
	private static EntityManager entityMgrObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
			.createEntityManager();
	private static EntityTransaction transactionObj = entityMgrObj.getTransaction();

	/* ---------- Création d'un produit ---------- */
	public static String creerProduit(String nomProd, int quantiteProd, Float puProd, String descProd,
			int idCat) {
		if (!transactionObj.isActive()) {
			transactionObj.begin();
		}
		
		Produit p = new Produit(0, nomProd, quantiteProd, puProd, descProd, idCat);
		System.out.println(p.toString());
		entityMgrObj.persist(p);
		transactionObj.commit();
		return "listProduits.xhtml?faces-redirect=true";
	}

	/* ---------- Création d'une catégorie ---------- */
	public static String creerCategorie(String nom, byte[] img) {
		if (!transactionObj.isActive()) {
			transactionObj.begin();
		}

		Categorie c = new Categorie(0, nom, img);

		entityMgrObj.persist(c);
		transactionObj.commit();

		return "listCategories.xhtml?faces-redirect=true";
	}

	/* ---------- Affichage des catégories ---------- */
	@SuppressWarnings("unchecked")
	public static List getAllCategories() {
		Query queryObj = entityMgrObj.createQuery("SELECT e FROM Categorie e");
		List<Categorie> cList = queryObj.getResultList();
		if (cList != null && cList.size() > 0) {
			return cList;
		} else {
			return null;
		}
	}

	/* ---------- Affichage des produits d'une catégorie ---------- */
	@SuppressWarnings("unchecked")
	public static List getAllProduits(int idCat) {
		Query queryObj = entityMgrObj.createQuery("SELECT e FROM Produit e WHERE id_cat = ?");
		queryObj.setParameter(1, idCat);
		List<Produit> pList = queryObj.getResultList();
		if (pList != null && pList.size() > 0) {
			return pList;
		} else {
			return null;
		}
	}

	/* --- Update catégorie --- */
	public static String updateCategorie(Categorie cat) {
		if (!transactionObj.isActive()) {
			transactionObj.begin();
		}

		System.out.println("Cat : " + cat.toString());
		Query queryObj = entityMgrObj
				.createQuery("UPDATE Categorie c SET c.nomCat=:nom WHERE c.idCat = " + cat.getIdCat());

		queryObj.setParameter("nom", cat.getNomCat());
		queryObj.executeUpdate();

		transactionObj.commit();
		return "listCategories.xhtml?faces-redirect=true";
	}

	/* --- Update produit --- */
	public static String updateProduit(Produit prod) {
		if (!transactionObj.isActive()) {
			transactionObj.begin();
		}

		Query queryObj = entityMgrObj.createQuery(
				"UPDATE Produit p SET p.nomProd=:nom, p.quantiteProd=:quantite, p.descProd=:desc, p.puProd=:pu, p.cat=:cat p WHERE p.idProd = "
						+ prod.getIdProd());

		queryObj.setParameter("nom", prod.getNomProd());
		queryObj.setParameter("quantite", prod.getQuantiteProd());
		queryObj.setParameter("desc", prod.getDescProd());
		queryObj.setParameter("pu", prod.getPuProd());
		queryObj.setParameter("cat", prod.getCat());

		queryObj.executeUpdate();

		transactionObj.commit();
		return "listProduits.xhtml?faces-redirect=true";
	}

	/* --- pour supprimer une catégorie --- */
	public static String deleteCategorie(int idCat) {
		if (!transactionObj.isActive()) {
			transactionObj.begin();
		}
		Categorie cat = entityMgrObj.find(Categorie.class, idCat);

		entityMgrObj.remove(cat);

		transactionObj.commit();

		return "listCategories.xhtml?faces-redirect=true";
	}

	/* --- pour supprimer un produit --- */
	public static String deleteProduit(int idProd) {
		if (!transactionObj.isActive()) {
			transactionObj.begin();
		}
		Produit prod = entityMgrObj.find(Produit.class, idProd);

		entityMgrObj.remove(prod);

		transactionObj.commit();

		return "listProduits.xhtml?faces-redirect=true";
	}

	/* --- pour vérifier si l'id de la catégorie existe dans la BDD --- */
	public static boolean idCatExist(int idCat) {
		boolean exist = false;
		Query queryObj = entityMgrObj.createQuery("SELECT c FROM Categorie c WHERE c.idCat = " + idCat);
		EntityManager idC = (EntityManager) queryObj.getSingleResult();
		if (idC != null) {
			exist = true;
		}
		return exist;
	}

	/* --- Récupérer la catégorie par id --- */
	public static Categorie getCategorieById(int id) {
		if (!transactionObj.isActive()) {
			transactionObj.begin();
		}

		Categorie cat = entityMgrObj.find(Categorie.class, id);

		System.out.println("GetCatById : " + cat.getNomCat());
		return cat;
	}

	/* --- Récupérer le produit par id --- */
	public static Produit getProduitById(int id) {
		if (!transactionObj.isActive()) {
			transactionObj.begin();
		}

		Produit prod = entityMgrObj.find(Produit.class, id);

		System.out.println("GetProdById : " + prod.getNomProd());
		return prod;
	}

	/* --- pour récuperer la liste de tous les produits --- */
	@SuppressWarnings("unchecked")
	public static List getAllProduits() {
		Query queryObj = entityMgrObj.createQuery("SELECT p FROM Produit p");
		List<Produit> pList = queryObj.getResultList();
		if (pList != null && pList.size() > 0) {
			return pList;
		} else {
			System.out.println("--> null");
			return null;
		}
	}

	/* --- pour récuperer la liste des produits suivant une catégorie --- */
	@SuppressWarnings("unchecked")
	public static List getProduitsByCat(Integer idCat) {
		Categorie cat = entityMgrObj.find(Categorie.class, idCat);

		System.out.println("Categorie : " + cat);
		Query queryObj = entityMgrObj.createQuery("SELECT p FROM Produit p WHERE p.cat.idCat = " + idCat);
		List<Produit> pList = queryObj.getResultList();
		if (pList != null && pList.size() > 0) {
			return pList;
		} else {
			System.out.println("--> null");
			return null;
		}
	}
}
