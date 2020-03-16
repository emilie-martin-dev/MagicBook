package magic_book.window.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import magic_book.core.Book;
import magic_book.core.node.AbstractBookNode;
import magic_book.core.node.BookNodeLink;
import magic_book.observer.NodeLinkFxObserver;
import magic_book.observer.RectangleFxObserver;
import magic_book.window.Mode;
import magic_book.window.dialog.NodeDialog;
import magic_book.window.dialog.NodeLinkDialog;
import magic_book.window.dialog.PreludeDialog;
import magic_book.window.gui.NodeFx;
import magic_book.window.gui.NodeLinkFx;
import magic_book.window.gui.PreludeFx;
import magic_book.window.gui.RectangleFx;



public class GraphPane extends Pane {
	

	private List<NodeFx> listeNoeud;
	private List<NodeLinkFx> listeNoeudLien;
	private NodeFx firstNodeFxSelected;
	
	private Mode mode;
	private PreludeFx preludeFx;	
	
	public GraphPane(){
		listeNoeud = new ArrayList<>();
		listeNoeudLien = new ArrayList<>();

		this.setStyle("-fx-background-color: #dddddd;");
		this.setCursor(Cursor.CLOSED_HAND);
		this.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
			if (mode == Mode.ADD_NODE) {
				NodeFx createnode = createNodeFxDialog(event);
				if (createnode != null){
					createnode.addNodeFxObserver(new NodeFxListener());
					this.getChildren().add(createnode);
				}
			}
		});
		createNodePrelude();
	}
	
	public NodeFx createNode(AbstractBookNode node, int x, int y) {
		NodeFx nodeFx = new NodeFx(node);
		nodeFx.setX(x);
		nodeFx.setY(y);

		listeNoeud.add(nodeFx);
		return nodeFx;
	}
	
	public NodeFx createNodeFxDialog(MouseEvent event){
		NodeFx createnode = null;
		NodeDialog nodeDialog = new NodeDialog();
		AbstractBookNode node = nodeDialog.getNode();
		if(node != null) {
			createnode = createNode(node, (int) event.getX(), (int) event.getY());
			return createnode;
		}
		return createnode;
	}
	

	public NodeLinkFx createNodeLink(BookNodeLink bookNodeLink, NodeFx firstNodeFx, NodeFx secondNodeFx) {
		NodeLinkFx nodeLinkFx = new NodeLinkFx(bookNodeLink, firstNodeFx, secondNodeFx);

		nodeLinkFx.startXProperty().bind(firstNodeFx.xProperty().add(firstNodeFx.widthProperty().divide(2)));
		nodeLinkFx.startYProperty().bind(firstNodeFx.yProperty().add(firstNodeFx.heightProperty().divide(2)));

		nodeLinkFx.endXProperty().bind(secondNodeFx.xProperty().add(secondNodeFx.widthProperty().divide(2)));
		nodeLinkFx.endYProperty().bind(secondNodeFx.yProperty().add(secondNodeFx.heightProperty().divide(2)));

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

		this.getChildren().add(preludeFx);
		this.setPreludeFx(preludeFx);
	}
	
	public void setBookNode(Book book){	
		for(AbstractBookNode node : book.getNodes().values()) {					
			createNode(node, 0, 0);
		}

		for(AbstractBookNode node : book.getNodes().values()) {
			NodeFx first = null;
			for(NodeFx fx : getListeNoeud()) {
				if(fx.getNode() == node) {
					first = fx;
					break;
				}
			}

			for(BookNodeLink choice : node.getChoices()) {
				NodeFx second = null;
				for(NodeFx fx : getListeNoeud()) {
					if(fx.getNode() == choice.getDestination()) {
						second = fx;
						break;
					}
				}

				NodeLinkFx nodeLinkFx = createNodeLink(choice, first, second);
				this.getChildren().add(nodeLinkFx);
			}
		}
	}
	
	public List<NodeFx> getListeNoeud() {
		return listeNoeud;
	}

	public List<NodeLinkFx> getListeNoeudLien() {
		return listeNoeudLien;
	}

	public NodeFx getFirstNodeFxSelected() {
		return firstNodeFxSelected;
	}
	
	public PreludeFx getPreludeFx() {
		return preludeFx;
	}
	
	public Mode getMode() {
		return mode;
	}

	public void setFirstNodeFxSelected(NodeFx firstNodeFxSelected) {
		this.firstNodeFxSelected = firstNodeFxSelected;
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
				firstNodeFxSelected = nodeFx;
				if(event.getClickCount() == 2) {
					NodeDialog dialog = new NodeDialog(nodeFx.getNode());
					if(dialog.getNode() != null) {
						nodeFx.setNode(dialog.getNode());
					}
				}
			} else if(mode == Mode.ADD_NODE_LINK) {
				NodeLinkFx nodeLinkFx = null;
				if(firstNodeFxSelected == null) {
					firstNodeFxSelected = nodeFx;
				} else {				
					NodeLinkDialog nodeLinkDialog = new NodeLinkDialog();
					BookNodeLink bookNodeLink = nodeLinkDialog.getNodeLink();

					if(bookNodeLink != null) {
						nodeLinkFx = createNodeLink(bookNodeLink, firstNodeFxSelected, nodeFx);
						GraphPane.this.getChildren().add(nodeLinkFx);
						nodeLinkFx.addNodeLinkFxObserver(new NodeLinkFxListener());
					}

					firstNodeFxSelected = null;
				}
				
				
			} else if(mode == Mode.DELETE) {
				if(confirmDeleteDialog()){
					GraphPane.this.getChildren().remove(nodeFx);
				}
				List<NodeLinkFx> nodeFxToRemove = new ArrayList();
				NodeLinkFx nodeLinkFxRemove = null;

				for(NodeLinkFx nodeLinkFx: listeNoeudLien){
					NodeFx nodeFxStart = nodeLinkFx.getStart();
					NodeFx nodeFxEnd = nodeLinkFx.getEnd();

					if(nodeFxStart == nodeFx || nodeFxEnd == nodeFx){
						nodeFxToRemove.add(nodeLinkFx);
						GraphPane.this.getChildren().remove(nodeLinkFx);
					}
				}

				for(NodeLinkFx nodeLinkRemove:nodeFxToRemove){
					listeNoeudLien.remove(nodeLinkRemove);
				}
			}
			event.consume();
		}
	}
	



	class NodeLinkFxListener implements NodeLinkFxObserver {

		public void onNodeLinkFXClicked(NodeLinkFx nodeLinkFx, MouseEvent event) {
			if(mode == Mode.SELECT) {
				if(event.getClickCount() == 2) {
					new NodeLinkDialog(nodeLinkFx.getNodeLink());
				}
			} else if(mode == Mode.DELETE) {
				if (confirmDeleteDialog()== true){
					listeNoeudLien.remove(nodeLinkFx);
					GraphPane.this.getChildren().remove(nodeLinkFx);
				}
			}
		}
	}
}

