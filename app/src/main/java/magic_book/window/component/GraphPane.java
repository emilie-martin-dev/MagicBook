package magic_book.window.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.observer.fx.NodeLinkFxObserver;
import magic_book.observer.fx.RectangleFxObserver;
import magic_book.window.Mode;
import magic_book.window.dialog.NodeDialog;
import magic_book.window.dialog.NodeLinkDialog;
import magic_book.window.dialog.PreludeDialog;
import magic_book.window.gui.NodeFx;
import magic_book.window.gui.NodeLinkFx;
import magic_book.window.gui.PreludeFx;
import magic_book.window.gui.RectangleFx;

public class GraphPane extends ScrollPane {
	
	private List<NodeFx> listeNoeud;
	private List<NodeLinkFx> listeNoeudLien;
	private NodeFx selectedNodeFx;
	
	private Line preludeFxFirstNodeLine;
	
	private Mode mode;
	private PreludeFx preludeFx;	
	private Book book;
	
	private Pane rootPane;
	
	public GraphPane(Book book){
		listeNoeud = new ArrayList<>();
		listeNoeudLien = new ArrayList<>();
		
		preludeFxFirstNodeLine = new Line();
		preludeFxFirstNodeLine.setStrokeWidth(3);
		preludeFxFirstNodeLine.setStroke(Color.BLACK);
		
		rootPane = new Pane();
		
		this.setContent(rootPane);
		rootPane.setMinSize(10000, 10000);
		this.setFitToWidth(true);
		this.setFitToHeight(true);
		this.setPannable(true);
		
		rootPane.setStyle("-fx-background-color: #dddddd;");
		rootPane.setCursor(Cursor.CLOSED_HAND);
		rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			selectedNodeFx = null;
			
			if (mode == Mode.ADD_NODE) {
				createNodeFxDialog(event);
			}
		});
		
		createNodePrelude();
		setBook(book);
	}
	
	public NodeFx createNode(AbstractBookNode node, int x, int y) {
		NodeFx nodeFx = new NodeFx(node);
		nodeFx.setX(x);
		nodeFx.setY(y);
		nodeFx.addNodeFxObserver(new NodeFxListener());
		
		listeNoeud.add(nodeFx);
		rootPane.getChildren().add(nodeFx);
		
		return nodeFx;
	}
	
	public NodeFx createNodeFxDialog(MouseEvent event){
		NodeDialog nodeDialog = new NodeDialog();
		AbstractBookNode node = nodeDialog.getNode();
		
		if(node != null) {
			createNode(node, (int) event.getX(), (int) event.getY());
			
			book.appendNode(node);
		}
		
		return null;
	}
	

	public NodeLinkFx createNodeLink(BookNodeLink bookNodeLink, NodeFx firstNodeFx, NodeFx secondNodeFx) {
		NodeLinkFx nodeLinkFx = new NodeLinkFx(bookNodeLink, firstNodeFx, secondNodeFx);
		nodeLinkFx.addNodeLinkFxObserver(new NodeLinkFxListener());

		nodeLinkFx.startXProperty().bind(firstNodeFx.xProperty().add(firstNodeFx.widthProperty().divide(2)));
		nodeLinkFx.startYProperty().bind(firstNodeFx.yProperty().add(firstNodeFx.heightProperty().divide(2)));

		nodeLinkFx.endXProperty().bind(secondNodeFx.xProperty().add(secondNodeFx.widthProperty().divide(2)));
		nodeLinkFx.endYProperty().bind(secondNodeFx.yProperty().add(secondNodeFx.heightProperty().divide(2)));

		rootPane.getChildren().add(nodeLinkFx);
		listeNoeudLien.add(nodeLinkFx);
		
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
		PreludeFx preludeFx = new PreludeFx(null);
		preludeFx.setX(10);
		preludeFx.setY(10);
		
		preludeFx.addNodeFxObserver((RectangleFx rectangleFx, MouseEvent event) -> {
			if(mode == Mode.SELECT) {
				if(event.getClickCount() == 2) {
					PreludeDialog dialog = new PreludeDialog(preludeFx.getText());
					preludeFx.setText(dialog.getTextePrelude());
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
		for(AbstractBookNode node : book.getNodes().values()) {
			NodeFx createdNodeFx = createNode(node, 0, 0);
			nodeNodeFxMapping.put(node, createdNodeFx);
		}
		
		for(AbstractBookNode node : book.getNodes().values()) {
			for(BookNodeLink choice : node.getChoices()) {
				createNodeLink(choice, nodeNodeFxMapping.get(node), nodeNodeFxMapping.get(book.getNodes().get(choice.getDestination())));
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
					NodeDialog dialog = new NodeDialog(nodeFx.getNode());
					if(dialog.getNode() != null) {
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
					NodeLinkDialog nodeLinkDialog = new NodeLinkDialog();
					BookNodeLink bookNodeLink = nodeLinkDialog.getNodeLink();

					if(bookNodeLink != null) {
						bookNodeLink.setDestination(book.getNodeIndex(nodeFx.getNode()));

						book.addNodeLink(bookNodeLink, (AbstractBookNodeWithChoices) selectedNodeFx.getNode());
						
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
							GraphPane.this.rootPane.getChildren().remove(nodeLinkFx);
						}
					}

					for(NodeLinkFx nodeLinkRemove:nodeFxToRemove){
						listeNoeudLien.remove(nodeLinkRemove);
					}

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
					NodeLinkDialog nodeLinkDialog = new NodeLinkDialog(nodeLinkFx.getNodeLink());
					if(nodeLinkDialog.getNodeLink()!= null) {
						book.updateNodeLink(nodeLinkFx.getNodeLink(), nodeLinkDialog.getNodeLink());
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

