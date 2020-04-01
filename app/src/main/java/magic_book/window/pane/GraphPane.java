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

public class GraphPane extends ScrollPane {
	
	private static final float SCROLL_RATIO = 400f;
	private static final float MIN_ZOOM = 0.2f;
	private static final float MAX_ZOOM = 4f;
	
	private List<NodeFx> listeNoeud;
	private List<NodeLinkFx> listeNoeudLien;
	private NodeFx selectedNodeFx;
	
	private Line preludeFxFirstNodeLine;
	
	private Mode mode;
	private PreludeFx preludeFx;	
	private Book book;
	
	private Pane rootPane;
	
	private SimpleFloatProperty zoom;
	
	public GraphPane(Book book){
		listeNoeud = new ArrayList<>();
		listeNoeudLien = new ArrayList<>();
		rootPane = new Pane();
		
		this.setContent(rootPane);
		this.setFitToWidth(true);
		this.setFitToHeight(true);
		this.setPannable(true);
		
		zoom = new SimpleFloatProperty(1);
		
		new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> ov, Object t, Object t1) {
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}
			
		};
		
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
	
	public NodeFx createNode(AbstractBookNode node, double x, double y) {
		NodeFx nodeFx = new NodeFx(node, zoom);
		nodeFx.setRealX(x);
		nodeFx.setRealY(y);
		
		nodeFx.addNodeFxObserver(new NodeFxListener());
		
		listeNoeud.add(nodeFx);
		rootPane.getChildren().add(nodeFx);
		
		return nodeFx;
	}
	
	public NodeFx createNodeFxDialog(MouseEvent event){
		NodeDialog nodeDialog = new NodeDialog(book);
		AbstractBookNode node = nodeDialog.getNode();
		
		if(node != null) {
			createNode(node, (int) event.getX(), (int) event.getY());
			
			book.addNode(node);
		}
		
		return null;
	}
	

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
	
	public void setBook(Book book){	
		listeNoeud.clear();
		listeNoeudLien.clear();
		selectedNodeFx = null;
		rootPane.getChildren().clear();	
		
		this.book = book;	
		
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
	
	public List<NodeFx> getListeNoeud() {
		return listeNoeud;
	}

	public List<NodeLinkFx> getListeNoeudLien() {
		return listeNoeudLien;
	}

	public NodeFx getSelectedNodeFx() {
		return selectedNodeFx;
	}
	
	public PreludeFx getPreludeFx() {
		return preludeFx;
	}
	
	public Mode getMode() {
		return mode;
	}

	public void setSelectedNodeFx(NodeFx selectedNodeFx) {
		this.selectedNodeFx = selectedNodeFx;
	}

	public void setPreludeFx(PreludeFx preludeFx) {
		this.preludeFx = preludeFx;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public void setListeNoeud(List<NodeFx> listeNoeud) {
		this.listeNoeud = listeNoeud;
	}

	public void setListeNoeudLien(List<NodeLinkFx> listeNoeudLien) {
		this.listeNoeudLien = listeNoeudLien;
	}
	
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

