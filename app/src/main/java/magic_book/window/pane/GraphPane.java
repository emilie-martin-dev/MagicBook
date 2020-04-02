package magic_book.window.pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
* Pane comprenant tout le milieu de la fenêtre : l'édition des noeuds
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
	* Le prélude est le premier noeud lors de la création du lien
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
	* Le Pane du GraphPane qui contient tout
	*/
	private Pane rootPane;
	
	/**
	* le zoom
	*/
	private SimpleFloatProperty zoom;
	
	
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
			float newZoomLevel = ((float)event.getDeltaY() / SCROLL_RATIO) + zoom.getValue();
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
	* Création d'un NodeFx et du rectangle
	* @param node Contient le noeud qui va être lié au NodeFx
	* @param x Contient le x du clique de la souris
	* @param y Contient le y du clique de la souris
	* @return le rectangle
	*/
	public NodeFx createNode(AbstractBookNode node, double x, double y) {
		NodeFx nodeFx = new NodeFx(node, zoom);
		nodeFx.setRealX(x);
		nodeFx.setRealY(y);
		
		nodeFx.addNodeFxObserver(new NodeFxListener());
		
		listeNoeud.add(nodeFx);
		rootPane.getChildren().add(nodeFx);
		
		return nodeFx;
	}
	
	/**
	* Création d'un noeud
	* @param event Clique de la souris
	* @return null
	*/
	public NodeFx createNodeFxDialog(MouseEvent event){
		NodeDialog nodeDialog = new NodeDialog(book);
		AbstractBookNode node = nodeDialog.getNode();
		
		if(node != null) {
			createNode(node, (int) event.getX(), (int) event.getY());
			
			book.addNode(node);
		}
		
		return null;
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
		PreludeFx preludeFx = new PreludeFx(null, zoom);
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
						preludeFx.setText(dialog.getTextePrelude());
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
	* Ajout de toutes les informations dans le livre
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
		preludeFx.setText(book.getTextPrelude());
		
		HashMap<AbstractBookNode, NodeFx> nodeNodeFxMapping = new HashMap<>();
		if(!book.getNodes().isEmpty()) {
			int i = 0;
			double angle = (Math.PI * 2) / book.getNodes().size();
			float radius = (UiConsts.RECTANGLE_FX_SIZE * book.getNodes().size()) / 4;

			float deltaPosition = radius + UiConsts.RECTANGLE_FX_SIZE;

			for(AbstractBookNode node : book.getNodes().values()) {
				NodeFx createdNodeFx = createNode(node, deltaPosition + Math.cos(i * angle) * radius, deltaPosition + Math.sin(i * angle) * radius);
				nodeNodeFxMapping.put(node, createdNodeFx);
				i++;
			}
		
			for(AbstractBookNode node : book.getNodes().values()) {
				for(BookNodeLink choice : node.getChoices()) {
					createNodeLink(choice, nodeNodeFxMapping.get(node), nodeNodeFxMapping.get(book.getNodes().get(choice.getDestination())));
				}
			}
		}
	
		setFirstNode(nodeNodeFxMapping.get(book.getRootNode()));		
	}
	
	/**
	* Le premier noeud du livre est le prélude
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

	}

	@Override
	public void nodeEdited(AbstractBookNode oldNode, AbstractBookNode newNode) {

	}

	@Override
	public void nodeDeleted(AbstractBookNode node) {

	}

	@Override
	public void nodeLinkAdded(BookNodeLink nodeLink) {

	}

	@Override
	public void nodeLinkEdited(BookNodeLink oldNodeLink, BookNodeLink newNodeLink) {

	}

	@Override
	public void nodeLinkDeleted(BookNodeLink nodeLink) {

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
	* Récupère le NodeFx (rectangle) sélectionné
	* @return NodeFx sélectionné
	*/	
	public NodeFx getSelectedNodeFx() {
		return selectedNodeFx;
	}
	
	/**
	* Récupère le prélude (rectangle)
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
	* Permet de gérer les évènements sur les noeuds (rectangle)
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
						if(dialog.getNode() instanceof BookNodeCombat || selectedNodeFx.getNode() instanceof BookNodeCombat) {
							List<NodeLinkFx> postRemove = new ArrayList<>();
							for(NodeLinkFx nodeLinkFx : listeNoeudLien) {
								if(nodeLinkFx.getStart().getNode() == selectedNodeFx.getNode()) {
									nodeLinkFx.unregisterComponent(rootPane);
									postRemove.add(nodeLinkFx);
								}
							}
							
							for(NodeLinkFx nodeLinkFxToRemove : postRemove) {
								listeNoeudLien.remove(nodeLinkFxToRemove);
							}
						}
						
						book.updateNode(nodeFx.getNode(), dialog.getNode());
						nodeFx.setNode(dialog.getNode());
					}
				}
			} else if(mode == Mode.ADD_NODE_LINK) {
				if(selectedNodeFx == null && !(nodeFx.getNode() instanceof AbstractBookNodeWithChoices)) {
					return;
				} else if(selectedNodeFx == null && nodeFx.getNode() instanceof AbstractBookNodeWithChoices) {
					selectedNodeFx = nodeFx;
				} else {
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
					
					NodeLinkDialog nodeLinkDialog = new NodeLinkDialog(selectedNodeFx.getNode());
					BookNodeLink bookNodeLink = nodeLinkDialog.getNodeLink();

					if(bookNodeLink != null) {
						if(selectedNodeFx.getNode() instanceof BookNodeCombat) {
							BookNodeCombat bookNodeCombat = (BookNodeCombat) selectedNodeFx.getNode();
							
							if(nodeLinkDialog.getLinkType() == NodeLinkDialog.EVASION) {
								bookNodeCombat.setEvasionBookNodeLink(bookNodeLink);
							} else if(nodeLinkDialog.getLinkType() == NodeLinkDialog.PERDRE) {
								bookNodeCombat.setLooseBookNodeLink(bookNodeLink);
							} else if(nodeLinkDialog.getLinkType() == NodeLinkDialog.GAGNE) {
								bookNodeCombat.setWinBookNodeLink(bookNodeLink);
							}
						} else {
							book.addNodeLink(bookNodeLink, (AbstractBookNodeWithChoices) selectedNodeFx.getNode());
						}
						
						bookNodeLink.setDestination(book.getNodeIndex(nodeFx.getNode()));
						
						createNodeLink(bookNodeLink, selectedNodeFx, nodeFx);
					}
					
					selectedNodeFx = null;
				}
				
				
			} else if(mode == Mode.DELETE) {
				if(confirmDeleteDialog()){
					GraphPane.this.rootPane.getChildren().remove(nodeFx);
				
					List<NodeLinkFx> nodeFxToRemove = new ArrayList();

					for(NodeLinkFx nodeLinkFx: listeNoeudLien){
						NodeFx nodeFxStart = nodeLinkFx.getStart();
						NodeFx nodeFxEnd = nodeLinkFx.getEnd();

						if(nodeFxStart == nodeFx || nodeFxEnd == nodeFx){
							nodeFxToRemove.add(nodeLinkFx);
							nodeLinkFx.unregisterComponent(rootPane);
						}
					}

					for(NodeLinkFx nodeLinkRemove:nodeFxToRemove){
						listeNoeudLien.remove(nodeLinkRemove);
					}

					if(book.getNodeIndex(nodeFx.getNode()) == 1)
						preludeFxFirstNodeLine.setVisible(false);
					
					book.removeNode(nodeFx.getNode());
				}
			} else if(mode == Mode.FIRST_NODE) {
				setFirstNode(nodeFx);
			}
			
			event.consume();
		}
	}
	
	/**
	* Permet de gérer les évènements sur les liens (ligne)
	*/
	class NodeLinkFxListener implements NodeLinkFxObserver {

		public void onNodeLinkFXClicked(NodeLinkFx nodeLinkFx, MouseEvent event) {
			if(mode == Mode.SELECT) {
				if(event.getClickCount() == 2) {
					NodeLinkDialog nodeLinkDialog = new NodeLinkDialog(nodeLinkFx.getNodeLink(), nodeLinkFx.getStart().getNode());
					if(nodeLinkDialog.getNodeLink() != null) {
						if(nodeLinkFx.getStart().getNode() instanceof BookNodeCombat) {
							BookNodeCombat bookNodeCombat = (BookNodeCombat) nodeLinkFx.getStart().getNode();

							bookNodeCombat.removeChoice(nodeLinkFx.getNodeLink());
							
							if(nodeLinkDialog.getLinkType() == NodeLinkDialog.EVASION) {
								bookNodeCombat.setEvasionBookNodeLink(nodeLinkDialog.getNodeLink());
							} else if(nodeLinkDialog.getLinkType() == NodeLinkDialog.PERDRE) {
								bookNodeCombat.setLooseBookNodeLink(nodeLinkDialog.getNodeLink());
							} else if(nodeLinkDialog.getLinkType() == NodeLinkDialog.GAGNE) {
								bookNodeCombat.setWinBookNodeLink(nodeLinkDialog.getNodeLink());
							}
						} else {							
							book.updateNodeLink(nodeLinkFx.getNodeLink(), nodeLinkDialog.getNodeLink());
						}
						
						nodeLinkDialog.getNodeLink().setDestination(book.getNodeIndex(nodeLinkFx.getEnd().getNode()));
						
						nodeLinkFx.setNodeLink(nodeLinkDialog.getNodeLink());
					}
				}
			} else if(mode == Mode.DELETE) {
				if (confirmDeleteDialog()== true){
					listeNoeudLien.remove(nodeLinkFx);
					GraphPane.this.rootPane.getChildren().remove(nodeLinkFx);
					
					book.removeNodeLink(nodeLinkFx.getNodeLink());
				}
			}
			
			event.consume();
		}
	}
}

