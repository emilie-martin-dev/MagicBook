package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.file.json.ItemLinkJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.item.BookItemLink;

/**
 * Classe mère des noeud composé de choix
 * @param <T> Le type de choix
 */
public abstract class AbstractBookNodeWithChoices <T extends BookNodeLink> extends AbstractBookNode {
	
	/**
	 * Nombre d'item à prendre, au maximum, sur le noeud (-1 représente l'infini)
	 */
	private Integer nbItemsAPrendre;
	/**
	 *  Liste des items disponible sur le noeud
	 */
	private List<BookItemLink> itemLinks;
	/**
	 * Liste des items pouvant être achetés sur le noeud
	 */
	private List<BookItemLink> shopItemLinks;
	/**
	 *  Liste de choix possible à partir de ce noeud
	 */
	private List<T> choices;
	/**
	 * Obligation ou non de manger
	 */
	private boolean mustEat;
	/**
	 * Perte ou gain de vie
	 */
	private int hp;
	
	/**
	 * Constructeur de AbstractBookNodeWithChoice
	 * @param text Texte du noeud basic
	 * @param nbItemsAPrendre Nombre d'item maximum pouvant être pris sur ce noeud (-1 représente l'infini)
	 * @param itemLinks Liste des items disponible sur ce noeud
	 * @param shopItemLinks Liste des items pouvant être acheté sur ce noeud
	 * @param choices Liste de choix possible à partir de ce noeud
	 */
	public AbstractBookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<T> choices){
		this(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices, false, 0);
	}
	
	/**
	 * Constructeur de AbstrcatBookNodeWithChoice avec super
	 * @param text Texte du noeud basic
	 * @param nbItemsAPrendre Nombre d'item maximum pouvant être pris sur ce noeud (-1 représente l'infini)
	 * @param itemLinks Liste des items disponible sur ce noeud
	 * @param shopItemLinks Liste des items pouvant être acheté sur ce noeud
	 * @param choices Liste de choix possible à partir de ce noeud
	 * @param mustEat Obligation ou non de manger
	 * @param hp Perte ou gain de vie
	 */
	public AbstractBookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<T> choices, boolean mustEat, int hp){
		super(text);
		
		this.nbItemsAPrendre = nbItemsAPrendre;
		this.itemLinks = itemLinks;
		this.shopItemLinks = shopItemLinks;
		this.choices = choices;
		this.mustEat = mustEat;
		this.hp = hp;
		
		if(this.choices == null)
			this.choices = new ArrayList<>();
		
		if(this.itemLinks == null)
			this.itemLinks = new ArrayList<>();
		
		if(this.shopItemLinks == null)
			this.shopItemLinks = new ArrayList<>();
	}
	
	/**
	 * Décrit les items disponibless
	 * @param book Livre contenant toute les informations
	 * @return Texte comprennant le nom ainsi que le nombre des items disponible
	 */
	public String getItemDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		if(!itemLinks.isEmpty()) {
			buffer.append("\n");
			buffer.append("Les items suivants sont disponibles : \n");

			for(BookItemLink il : itemLinks) {
				buffer.append("\n");
				buffer.append(il.getDescription(book));
			}
			
			if(nbItemsAPrendre == -1) {
				buffer.append("\nVous pouvez prendre autant d'items que vous le voulez.\n");
			} else {
				buffer.append("\nVous pouvez prendre ");
				buffer.append(nbItemsAPrendre);
				buffer.append(" items.\n");
			}
		}
		
		return buffer.toString();
	}
	
	/**
	 * Décrit les items qui peuvent être achetés
	 * @param book Livre contenant toute les informations
	 * @return Texte comprenant les items pouvant être achetés
	 */
	public String getShopDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		if(!shopItemLinks.isEmpty()) {
			buffer.append("\n");
			buffer.append("Les items suivants sont en vente : \n");

			for(BookItemLink il : shopItemLinks) {
				buffer.append("\n");
				buffer.append(il.getDescription(book));
			}
		}
		
		return buffer.toString();
	}
	
	/**
	 * Décrit le reste des informations
	 * @param book Livre contenant toute les informations
	 * @return Texte sur le reste des informations
	 */
	public String getMiscellaneousDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		if(mustEat) {
			buffer.append("\n");
			buffer.append("Vous devez manger pour continuer.\n");
		}
		
		if(hp < 0) {
			buffer.append("\n");
			buffer.append("Vous venez de perdre ");
			buffer.append(Math.abs(hp));
			buffer.append(" HP.\n");
		} else if(hp > 0) {
			buffer.append("\n");
			buffer.append("Vous venez de gagner ");
			buffer.append(hp);
			buffer.append(" HP.\n");
		}	
		
		return buffer.toString();
	}

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		
		buffer.append(getItemDescription(book));
		
		buffer.append(getShopDescription(book));
		
		buffer.append(getMiscellaneousDescription(book));
		
		if(!choices.isEmpty()) {
			buffer.append("\n");
			buffer.append("Que souhaitez vous faire ?\n\n");


			for(int i = 0 ; i < choices.size() ; i++) {
				buffer.append(choices.get(i).getDescription(book));
				
				if(i < choices.size() - 1) {
					buffer.append("\n");
				}
			}
		}
		
		return buffer.toString();
	}

	@Override
	public SectionJson toJson() {
		SectionJson sectionJson = super.toJson();
		
		if(hp != 0)
			sectionJson.setHp(hp);

		if(isMustEat())
			sectionJson.setMustEat(true);

		if(!itemLinks.isEmpty())
			sectionJson.setAmountToPick(nbItemsAPrendre);

		if(!shopItemLinks.isEmpty()) {
			sectionJson.setShop(new ArrayList<>());

			for(BookItemLink itemLink : shopItemLinks) {
				sectionJson.getShop().add(itemLink.toJson());
			}
		}

		if(!itemLinks.isEmpty()) {
			sectionJson.setItems(new ArrayList<>());

			for(BookItemLink itemLink : itemLinks) {
				sectionJson.getItems().add(itemLink.toJson());
			}
		}

		sectionJson.setChoices(new ArrayList<>());
		for(BookNodeLink nodeLink : choices) {
			sectionJson.getChoices().add(nodeLink.toJson());
		}
		
		return sectionJson;
	}

	@Override
	public void fromJson(SectionJson json) {
		super.fromJson(json);
		
		if(json.getAmountToPick() != null)
			nbItemsAPrendre = json.getAmountToPick();
		else
			nbItemsAPrendre = -1;
		
		if(json.getMustEat() == null) 
			mustEat = false;
		else 
			mustEat = json.getMustEat();
		
		if(json.getHp()== null) 
			hp = 0;
		else 
			hp = json.getHp();
		
		if(json.getItems() != null) {
			for(ItemLinkJson itemLinkJson : json.getItems()) {
				BookItemLink bookItemsLink = new BookItemLink();
				bookItemsLink.fromJson(itemLinkJson);

				itemLinks.add(bookItemsLink);
			}
		}
		
		if(json.getShop() != null) {
			for(ItemLinkJson itemLinkJson : json.getShop()) {
				BookItemLink bookItemsLink = new BookItemLink();
				bookItemsLink.fromJson(itemLinkJson);

				shopItemLinks.add(bookItemsLink);
			}
		}
	}
	
	/**
	 * Ajoute un choix parmis la liste de choix existant
	 * @param nodeLink Le choix à ajouter
	 */
	public void addChoice(T nodeLink) {
		this.choices.add(nodeLink);
	}

	/**
	 * Met à jour un choix
	 * @param oldNodeLink Ancien choix
	 * @param newNodeLink Nouveau choix
	 */
	public void updateChoice(T oldNodeLink, T newNodeLink) {
		removeChoice(oldNodeLink);
		addChoice(newNodeLink);
	}
	
	/**
	 * Supprime un choix
	 * @param nodeLink Choix à supprimer
	 */
	public void removeChoice(T nodeLink) {
		if(this.choices.contains(nodeLink))
			choices.remove(nodeLink);
	}
	
	@Override
	public List<T> getChoices() {
		return choices;
	}
	
	/**
	 * Ajoute un item à acheter dans le noeud
	 * @param newShopItemLink Nouvel item à acheter
	 */
	public void addShopItemLink(BookItemLink newShopItemLink){
		this.shopItemLinks.add(newShopItemLink);
	}

	/**
	 * Ajoute un item disponible dans le noeud
	 * @param newItemLink Nouvel item disponible
	 */
	public void addItemLink(BookItemLink newItemLink){
		this.itemLinks.add(newItemLink);
	}

	/**
	 * Donne le nombre d'items pouvant être pris
	 * @return Nombre d'items
	 */
	public Integer getNbItemsAPrendre() {
		return nbItemsAPrendre;
	}

	/**
	 * Modifie le nombre d'items maximum à prendre
	 * @param nbItemsAPrendre Nombre maximum d'items
	 */
	public void setNbItemsAPrendre(Integer nbItemsAPrendre) {
		this.nbItemsAPrendre = nbItemsAPrendre;
	}

	/**
	 * Donne la liste d'items disponible sur ce noeud
	 * @return Liste d'items
	 */
	public List<BookItemLink> getItemLinks() {
		return itemLinks;
	}

	/**
	 * Modifie la liste des items disponible sur ce noeud
	 * @param itemLinks Nouvelle list d'items disponible
	 */
	public void setItemLinks(List<BookItemLink> itemLinks) {
		this.itemLinks = itemLinks;
	}

	/**
	 * Donne la liste des items pouvant être acheté
	 * @return Liste des items pouvant être acheté
	 */
	public List<BookItemLink> getShopItemLinks() {
		return shopItemLinks;
	}

	/**
	 * Modifie la liste des items pouvant être acheté
	 * @param shopItemLinks Nouvelle liste des items pouvant être acheté
	 */
	public void setShopItemLinks(List<BookItemLink> shopItemLinks) {
		this.shopItemLinks = shopItemLinks;
	}

	/**
	 * Donne si il est obligé manger ou non
	 * @return True ou False
	 */
	public boolean isMustEat() {
		return mustEat;
	}

	/**
	 * Modifie le boolean si il est obligé de manger ou non
	 * @param mustEat True ou False
	 */
	public void setMustEat(boolean mustEat) {
		this.mustEat = mustEat;
	}

	/**
	 * Donne le nombre de point de vie à gagner ou à perdre
	 * @return Point de vie
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * Modifie le nombre de point de vie gagné ou perdu
	 * @param hp Nombre de point de vie positif ou négatif
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
