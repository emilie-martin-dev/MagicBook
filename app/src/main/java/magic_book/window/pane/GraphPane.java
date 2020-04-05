package magic_book.window.pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.observer.book.BookNodeLinkObserver;
import magic_book.observer.book.BookNodeObserver;
import magic_book.observer.fx.NodeLinkFxObserver;
import magic_book.observer.fx.RectangleFxObserver;
import magic_book.window.Mode;
import magic_book.window.UiConsts;
import magic_book.window.dialog.NodeDialog;
import magic_book.window.dialog.NodeLinkDialog;
import magic_book.window.dialog.PreludeDialog;
import magic_book.window.gui.NodeFx;
import magic_book.window.gui.NodeLinkFx;
import magic_book.window.gui.PreludeFx;
import magic_book.window.gui.RectangleFx;

/**
* Création du centre de la MainWindows (édition des noeuds)
*/
public class GraphPane extends ScrollPane implements BookNodeObserver, BookNodeLinkObserver{
	
	/**
	* Ratio lors du scroll
	*/
	private static final float SCROLL_RATIO = 400f;
	
	/**
	* Minimum du zoom
	*/
	private static final float MIN_ZOOM = 0.2f;
	
	/**
	* Maximum du zoom
	*/
	private static final float MAX_ZOOM = 4f;
	
	/**
	* Liste de noeud existant
	*/
	private List<NodeFx> listeNoeud;
	
	/**
	* Liste de lien existant
	*/
	private List<NodeLinkFx> listeNoeudLien;
	
	/**
	* Le premier noeud sélectionné
	*/
	private NodeFx selectedNodeFx;
	
	/**
	* Le lien vers le premier noeud
	*/
	private Line preludeFxFirstNodeLine;
	
	/**
	* Mode sélectionné
	*/
	private Mode mode;
	
	/**
	* Noeud du prélude
	*/
	private PreludeFx preludeFx;
	
	/**
	* Le livre contenant toutes les informations
	*/
	private Book book;
	
	/**
	* Le Pane du GraphPane qui contient les noeuds
	*/
	private Pane rootPane;
	
	/**
	* Le zoom
	*/
	private SimpleFloatProperty zoom;
	
	private double lastXClick = 0;
	private double lastYClick = 0;
	
	
	/**
	* Création du pane
	* @param book Le livre contenant toutes les informations
	*/
	public GraphPane(Book book){
		listeNoeud = new ArrayList<>();
		listeNoeudLien = new ArrayList<>();
		rootPane = new Pane();
		
		this.setContent(rootPane);
		this.setFitToWidth(true);
		this.setFitToHeight(true);
		this.setPannable(true);
		
		zoom = new SimpleFloatProperty(1);
		
		rootPane.setOnScroll((ScrollEvent event) -> {
			// On réduit la puissance du scroll
			float newZoomLevel = ((float)event.getDeltaY() / SCROLL_RATIO) + zoom.getValue();
			
			// On limite la valeur possible du zoom
			if(newZoomLevel < MIN_ZOOM) {
				newZoomLevel = MIN_ZOOM;
			} else if(newZoomLevel > MAX_ZOOM) {
				newZoomLevel = MAX_ZOOM;
			}
			
			zoom.set(newZoomLevel);
			
			event.consume();
		});
		
		rootPane.setStyle("-fx-background-color: #dddddd;");
		rootPane.setCursor(Cursor.CLOSED_HAND);
		rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			selectedNodeFx = null;
			
			if (mode == Mode.ADD_NODE) {
				createNodeFxDialog(event);
			}
		});
		
		preludeFxFirstNodeLine = new Line();
		preludeFxFirstNodeLine.strokeWidthProperty().bind(zoom.multiply(3));
		preludeFxFirstNodeLine.setStroke(Color.BLACK);
		
		
		rootPane.minHeightProperty().bind(zoom.multiply(10000));
		rootPane.minWidthProperty().bind(zoom.multiply(10000));
		
		createNodePrelude();
		setBook(book);
	}
	
	/**
	* Création d'un NodeFx 
	* @param node Contient le noeud qui va être lié au NodeFx
	* @param x Contient le x du clique de la souris
	* @param y Contient le y du clique de la souris
	* @return le NodeFx
	*/
	public NodeFx createNode(AbstractBookNode node, double x, double y) {
		NodeFx nodeFx = new NodeFx(node, zoom);
		nodeFx.setRealX(x/zoom.get());
		nodeFx.setRealY(y/zoom.get());
		
		nodeFx.addNodeFxObserver(new NodeFxListener());
		
		listeNoeud.add(nodeFx);
		rootPane.getChildren().add(nodeFx);
		
		lastXClick = 0;
		lastYClick = 0;
		
		return nodeFx;
	}
	
	/**
	* Création d'une boite de dialogue pour renseigner un noeud
	* @param event Clique de la souris
	*/
	public void createNodeFxDialog(MouseEvent event){
		NodeDialog nodeDialog = new NodeDialog(book);
		AbstractBookNode node = nodeDialog.getNode();
		
		if(node != null) {
			lastXClick = event.getX();
			lastYClick = event.getY();
			
			book.addNode(node);
		}
	}
	
	/**
	* Création du NodeLinkFx et de la ligne graphique du lien
	* @param bookNodeLink lien rataché au NodeLinkFx
	* @param firstNodeFx premier noeud (début du lien)
	* @param secondNodeFx deuxième noeud (fin du lien)
	* @return un lien
	*/
	public NodeLinkFx createNodeLink(BookNodeLink bookNodeLink, NodeFx firstNodeFx, NodeFx secondNodeFx) {
		NodeLinkFx nodeLinkFx = new NodeLinkFx(bookNodeLink, firstNodeFx, secondNodeFx, zoom);
		nodeLinkFx.addNodeLinkFxObserver(new NodeLinkFxListener());

		// Lie la line avec les deux autres noeuds
		nodeLinkFx.startXProperty().bind(firstNodeFx.xProperty().add(firstNodeFx.widthProperty().divide(2)));
		nodeLinkFx.startYProperty().bind(firstNodeFx.yProperty().add(firstNodeFx.heightProperty().divide(2)));

		nodeLinkFx.endXProperty().bind(secondNodeFx.xProperty().add(secondNodeFx.widthProperty().divide(2)));
		nodeLinkFx.endYProperty().bind(secondNodeFx.yProperty().add(secondNodeFx.heightProperty().divide(2)));

		listeNoeudLien.add(nodeLinkFx);
		
		nodeLinkFx.registerComponent(rootPane);
		
		return nodeLinkFx;
	}
	
	/**
	* Demande de confirmation pour la supression du lien
	* @return Choix
	*/
	public boolean confirmDeleteDialog() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Suppression");
		alert.setHeaderText("Voulez vous vraiment supprimer ?");

		Optional<ButtonType> choix = alert.showAndWait();
		if (choix.get() == ButtonType.OK){
			return true;
		}
		
		return false;
	}
	
	
	/**
	* Création du prélude et de son NodeFx
	*/
	private void createNodePrelude() {
		PreludeFx preludeFx = new PreludeFx(zoom);
		preludeFx.setRealX(10);
		preludeFx.setRealY(10);
		
		preludeFx.addNodeFxObserver((RectangleFx rectangleFx, MouseEvent event) -> {
			if(mode == Mode.SELECT) {
				if(event.getClickCount() == 2) {
					PreludeDialog dialog = new PreludeDialog(book);
					
					if(dialog.getTextePrelude() != null) {
						book.setTextPrelude(dialog.getTextePrelude());
						book.setMainCharacter(dialog.getMainCharacter());
						book.setCharacterCreations(dialog.getCharacterCreations());
					}
				}
			}
		});
		
		preludeFxFirstNodeLine.startXProperty().bind(preludeFx.xProperty().add(preludeFx.widthProperty().divide(2)));
		preludeFxFirstNodeLine.startYProperty().bind(preludeFx.yProperty().add(preludeFx.heightProperty().divide(2)));
		
		rootPane.getChildren().add(preludeFx);
		this.setPreludeFx(preludeFx);
	}
	
	/**
	* Change le livre en cours d'édition
	* @param book Le livre contenant toutes les informations
	*/
	public void setBook(Book book){	
		if(this.book != null) {
			this.book.removeNodeObserver(this);
			this.book.removeNodeLinkObserver(this);
		}
			
		this.book = book;
		
		this.book.addNodeObserver(this);
		this.book.addNodeLinkObserver(this);
		
		listeNoeud.clear();
		listeNoeudLien.clear();
		selectedNodeFx = null;
		rootPane.getChildren().clear();	
		
		createNodePrelude();
		
		HashMap<AbstractBookNode, NodeFx> nodeNodeFxMapping = new HashMap<>();
		
		//Positionnement des noeuds du livre chargé
		if(!book.getNodes().isEmpty()) {
			int i = 0;
			// Détermine l'angle entre chaque noeud
			double angle = (Math.PI * 2) / book.getNodes().size();
			float radius = (UiConsts.RECTANGLE_FX_SIZE * book.getNodes().size()) / 4;

			// Position du centre du cercle
			float deltaPosition = radius + UiConsts.RECTANGLE_FX_SIZE;

			for(AbstractBookNode node : book.getNodes().values()) {
				// Utilisation de la trigonométrie pour placer les noeuds
				NodeFx createdNodeFx = createNode(node, deltaPosition + Math.cos(i * angle) * radius, deltaPosition + Math.sin(i * angle) * radius);
				nodeNodeFxMapping.put(node, createdNodeFx);
				i++;
			}
		
			for(AbstractBookNode node : book.getNodes().values()) {
				for(BookNodeLink choice : node.getChoices()) {
					// Ajoute les liens
					createNodeLink(choice, nodeNodeFxMapping.get(node), nodeNodeFxMapping.get(book.getNodes().get(choice.getDestination())));
				}
			}
		}
	
		setFirstNode(nodeNodeFxMapping.get(book.getRootNode()));		
	}
	
	/**
	* Change le premier noeud du livre
	* @param newFirstNode Prélude
	*/	
	public void setFirstNode(NodeFx newFirstNode) {
		preludeFx.setFirstNode(newFirstNode);
		
		if(newFirstNode == null) {
			preludeFxFirstNodeLine.setVisible(false);
		} else {
			preludeFxFirstNodeLine.endXProperty().bind(newFirstNode.xProperty().add(newFirstNode.widthProperty().divide(2)));
			preludeFxFirstNodeLine.endYProperty().bind(newFirstNode.yProperty().add(newFirstNode.heightProperty().divide(2)));
			
			preludeFxFirstNodeLine.setVisible(true);
			
			if(!rootPane.getChildren().contains(preludeFxFirstNodeLine)) {
				rootPane.getChildren().add(preludeFxFirstNodeLine);
			}			
			
			book.changeFirstNode(newFirstNode.getNode());
		}
	}
	
	@Override
	public void nodeAdded(AbstractBookNode node) {
		createNode(node, lastXClick, lastYClick);
	}

	@Override
	public void nodeEdited(AbstractBookNode oldNode, AbstractBookNode newNode) {
		for(NodeFx nodeFx : listeNoeud) {
			if(nodeFx.getNode() == oldNode) {
				nodeFx.setNode(newNode);
				break;
			}
		}
	}

	@Override
	public void nodeDeleted(AbstractBookNode node) {
		NodeFx nodeFxToDelete = null;
		for(NodeFx nodeFx : listeNoeud) {
			if(nodeFx.getNode() == node) {
				// On cherche le noeud à supprimer
				rootPane.getChildren().remove(nodeFx);
				nodeFxToDelete = nodeFx;
				break;
			}
		}
		
		// On le supprime
		listeNoeud.remove(nodeFxToDelete);
				
		// Si c'était le premier noeud on cache la ligne qui le reliait au prélude
		if(preludeFx.getFirstNode() == nodeFxToDelete) {
			preludeFxFirstNodeLine.setVisible(false);
			preludeFx.setFirstNode(null);
		}
	}

	@Override
	public void nodeLinkAdded(BookNodeLink nodeLink, AbstractBookNodeWithChoices node) {
		NodeFx start = null;
		NodeFx end = null;
		
		// On cherche le noeud de début et le noeud de fin
		for(NodeFx nodeFx : listeNoeud) {
			if(nodeFx.getNode() == node) 
				start = nodeFx;
			else if(nodeFx.getNode() == book.getNodes().get(nodeLink.getDestination()))
				end = nodeFx;
				
			if(start != null && end != null)
				break;
		}
		
		// Si on les a trouvé on ajoute le lien entre eux
		if(start != null && end != null)
			createNodeLink(nodeLink, start, end);
	}

	@Override
	public void nodeLinkEdited(BookNodeLink oldNodeLink, BookNodeLink newNodeLink) {
		for(NodeLinkFx nodeLinkFx : listeNoeudLien) {
			if(nodeLinkFx.getNodeLink() == oldNodeLink) {
				// On cherche le noeud et on le met à jour
				nodeLinkFx.setNodeLink(newNodeLink);
				break;
			}
		}
	}

	@Override
	public void nodeLinkDeleted(BookNodeLink nodeLink) {
		List<NodeLinkFx> postRemove = new ArrayList<>();
		for(NodeLinkFx nodeLinkFx : listeNoeudLien) {
			if(nodeLinkFx.getNodeLink() == nodeLink) {
				//On cherche les noeuds correspondant et on les supprime du GraphPane et de la liste (après cette boucle)
				nodeLinkFx.unregisterComponent(rootPane);
				postRemove.add(nodeLinkFx);
			}
		}					

		for(NodeLinkFx nodeLinkFxToRemove : postRemove) {
			listeNoeudLien.remove(nodeLinkFxToRemove);
		}
	}
	
	/**
	* Récupère la liste de tout les noeuds existant
	* @return liste de tout les noeuds existant
	*/	
	public List<NodeFx> getListeNoeud() {
		return listeNoeud;
	}

	/**
	* Récupère la liste de tout les liens existant
	* @return liste de tout les liens existant
	*/	
	public List<NodeLinkFx> getListeNoeudLien() {
		return listeNoeudLien;
	}

	/**
	* Récupère le NodeFx sélectionné
	* @return NodeFx sélectionné
	*/	
	public NodeFx getSelectedNodeFx() {
		return selectedNodeFx;
	}
	
	/**
	* Récupère le prélude
	* @return Prélude
	*/	
	public PreludeFx getPreludeFx() {
		return preludeFx;
	}
	
	/**
	* Récupère le mode sélectionné
	* @return Mode sélectionné
	*/
	public Mode getMode() {
		return mode;
	}

	/**
	* Modifie le noeud sélectionné
	* @param selectedNodeFx noeud sélétionné
	*/
	public void setSelectedNodeFx(NodeFx selectedNodeFx) {
		this.selectedNodeFx = selectedNodeFx;
	}

	/**
	* Modifie rectangle contenant le prélude
	* @param preludeFx prélude
	*/
	public void setPreludeFx(PreludeFx preludeFx) {
		this.preludeFx = preludeFx;
	}

	/**
	* Modifie le mode sélectionné
	* @param mode mode sélectionné
	*/
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	/**
	* Modifie la liste de noeud existant
	* @param listeNoeud liste de noeud existant
	*/
	public void setListeNoeud(List<NodeFx> listeNoeud) {
		this.listeNoeud = listeNoeud;
	}

	/**
	* Modifie la liste de lien existant
	* @param listeNoeudLien liste de lien existant
	*/
	public void setListeNoeudLien(List<NodeLinkFx> listeNoeudLien) {
		this.listeNoeudLien = listeNoeudLien;
	}

	
	/**
	* Permet de gérer les évènements sur les NodeFx en fonction du mode sélectionné
	*/
	class NodeFxListener implements RectangleFxObserver {
		
		@Override
		public void onRectangleFXClicked(RectangleFx rectangleFx, MouseEvent event) {	
			NodeFx nodeFx = (NodeFx) rectangleFx;
			if(mode == Mode.SELECT){
				selectedNodeFx = nodeFx;
				if(event.getClickCount() == 2) {
					NodeDialog dialog = new NodeDialog(book, selectedNodeFx.getNode());
					if(dialog.getNode() != null) {
						book.updateNode(nodeFx.getNode(), dialog.getNode());
					}
				}
			} else if(mode == Mode.ADD_NODE_LINK) {
				// Si le premier clic est sur un noeud qui ne peut pas contenir de choix
				if(selectedNodeFx == null && !(nodeFx.getNode() instanceof AbstractBookNodeWithChoices)) {
					return;
				//Si c'est le premier clic sur un noeud qui peut contenir des choix
				} else if(selectedNodeFx == null && nodeFx.getNode() instanceof AbstractBookNodeWithChoices) {
					selectedNodeFx = nodeFx;
				// 2 ème clic
				} else {
					// SI c'est un noeud de combat on vérifie qu'il reste des choix de libre
					if (selectedNodeFx.getNode() instanceof BookNodeCombat){
						BookNodeCombat firstNodeCombat = (BookNodeCombat) selectedNodeFx.getNode();
						if(firstNodeCombat.getEvasionBookNodeLink() != null 
								&& firstNodeCombat.getLooseBookNodeLink() != null
								&& firstNodeCombat.getWinBookNodeLink() != null) {
							Alert alertDialog = new Alert(Alert.AlertType.ERROR);

							alertDialog.setTitle("Erreur");
							alertDialog.setHeaderText("Veuillez supprimer un lien de victoire / defaite / evasion pour pouvoir rajouter un autre lien.");
							alertDialog.show();
			
							return;
						}
					}
					
					//On affiche la boite de dialogue pour afficher le noeud
					NodeLinkDialog nodeLinkDialog = new NodeLinkDialog(selectedNodeFx.getNode());
					BookNodeLink bookNodeLink = nodeLinkDialog.getNodeLink();

					if(bookNodeLink != null) {
						bookNodeLink.setDestination(book.getNodeIndex(nodeFx.getNode()));
						
						// Si c'est un noeud combat, on met à jour le lien correspondant
						if(selectedNodeFx.getNode() instanceof BookNodeCombat) {
							BookNodeCombat bookNodeCombat = (BookNodeCombat) selectedNodeFx.getNode();
							
							if(nodeLinkDialog.getLinkType() == NodeLinkDialog.EVASION) {
								bookNodeCombat.setEvasionBookNodeLink(bookNodeLink);
							} else if(nodeLinkDialog.getLinkType() == NodeLinkDialog.PERDRE) {
								bookNodeCombat.setLooseBookNodeLink(bookNodeLink);
							} else if(nodeLinkDialog.getLinkType() == NodeLinkDialog.GAGNE) {
								bookNodeCombat.setWinBookNodeLink(bookNodeLink);
							}
							
							createNodeLink(bookNodeLink, selectedNodeFx, nodeFx);
						} else {
							// Sinon, on ajoute juste le lien au noeud
							book.addNodeLink(bookNodeLink, (AbstractBookNodeWithChoices) selectedNodeFx.getNode());
						}
					}
					
					
					selectedNodeFx = null;
				}
				
			} else if(mode == Mode.DELETE) {
				if(confirmDeleteDialog()){
					book.removeNode(nodeFx.getNode());
				}
			} else if(mode == Mode.FIRST_NODE) {
				setFirstNode(nodeFx);
			}
			
			event.consume();
		}
	}
	
	/**
	* Permet de gérer les évènements sur les liens en fontion du mode sélectionné
	*/
	class NodeLinkFxListener implements NodeLinkFxObserver {

		public void onNodeLinkFXClicked(NodeLinkFx nodeLinkFx, MouseEvent event) {
			if(mode == Mode.SELECT) {
				if(event.getClickCount() == 2) {
					NodeLinkDialog nodeLinkDialog = new NodeLinkDialog(nodeLinkFx.getNodeLink(), nodeLinkFx.getStart().getNode());
					
					// Si on a validé les modifications sur un lien
					if(nodeLinkDialog.getNodeLink() != null) {
						nodeLinkDialog.getNodeLink().setDestination(book.getNodeIndex(nodeLinkFx.getEnd().getNode()));
						
						//On met à jour les liens pour un noeud de combat
						if(nodeLinkFx.getStart().getNode() instanceof BookNodeCombat) {
							BookNodeCombat bookNodeCombat = (BookNodeCombat) nodeLinkFx.getStart().getNode();

							book.removeNodeLink(nodeLinkFx.getNodeLink());
							
							if(nodeLinkDialog.getLinkType() == NodeLinkDialog.EVASION) {
								bookNodeCombat.setEvasionBookNodeLink(nodeLinkDialog.getNodeLink());
							} else if(nodeLinkDialog.getLinkType() == NodeLinkDialog.PERDRE) {
								bookNodeCombat.setLooseBookNodeLink(nodeLinkDialog.getNodeLink());
							} else if(nodeLinkDialog.getLinkType() == NodeLinkDialog.GAGNE) {
								bookNodeCombat.setWinBookNodeLink(nodeLinkDialog.getNodeLink());
							}
							
							createNodeLink(nodeLinkDialog.getNodeLink(), nodeLinkFx.getStart(), nodeLinkFx.getEnd());
						} else {							
							book.updateNodeLink(nodeLinkFx.getNodeLink(), nodeLinkDialog.getNodeLink());
						}
					}
				}
			} else if(mode == Mode.DELETE) {
				if (confirmDeleteDialog()== true){
					book.removeNodeLink(nodeLinkFx.getNodeLink());
				}
			}
			
			event.consume();
		}
	}
}

