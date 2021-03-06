package fr.istic.foucaultbertier.aco.moteur;

/**
 * Le presse-papier stocke une chaîne de caractère et la restitue sur demande
 */
public final class PressePapier
{

	/**
	 * Le contenu du presse-papier
	 */
	private String contenu;

	/**
	 * Crée le presse-papier avec une chaîne vide en contenu
	 */
	public PressePapier(){
		
		contenu = "";
	}

	/**
	 * Retourne le contenu du presse-papier
	 * @return Le contenu du presse-papier
	 */
	public final String getContenu() {
		
		return new String(contenu);
	}
	
	/**
	 * Change le contenu du presse-papier
	 * @param chaine Le nouveau contenu du presse-papier
	 */
	public final void setContenu(String chaine) {
		
		/* Préconditions */
		
		if(chaine == null){
			
			throw new IllegalArgumentException("Chaine est à null");
		}
		
		//On crée une nouvelle instance de string pour assurer l'int�grit� de nos données
		contenu = new String(chaine);
	}
	
	/**
	 * @return Vrai si le presse-papier est vide, faux sinon
	 */
	public boolean vide(){
		
		return contenu.isEmpty();
	}
}