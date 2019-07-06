package it.polito.tdp.flight;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.model.Aereoporto;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		
		if(txtDistanzaInput != null) {
			int distanza = Integer.parseInt(txtDistanzaInput.getText());
			model.creaGrafo(distanza);
			txtResult.appendText("GRAFO CREATO\n\n");
			
			Graph<Aereoporto, DefaultWeightedEdge> grafo = model.getGrafo();
			List<Aereoporto> aereoportiTotali = model.getAereoporti();
			List<Aereoporto> aereoportiValidi = new LinkedList<Aereoporto>();
			
			for(Aereoporto a : grafo.vertexSet()) {
				Set<Aereoporto> stemp = model.getRaggiungibili(a);
				stemp.removeAll(aereoportiTotali);
				if(stemp.isEmpty()) {
					aereoportiValidi.add(a);
				}
			}
			
			if(!aereoportiValidi.isEmpty()) {
				for(Aereoporto a : aereoportiValidi) {
					txtResult.appendText(a.toString()+"\n");
				}
			}
			else txtResult.appendText("NON CI SONO AEREOPORTI PER TUTTI");
			
			Set<Aereoporto> aTotali = new HashSet<Aereoporto>();
			for(Aereoporto atemp : aereoportiTotali) {
				aTotali.add(atemp);
			}
			
			aTotali.removeAll(model.getRaggiungibili(3484));
			Aereoporto best = null;
			double min = 0.0;
			for(Aereoporto a : aTotali) {
				double dis = LatLngTool.distance(model.getAereoporto(3484).getPosizione(), a.getPosizione(), LengthUnit.KILOMETER);
				if(min == 0 || dis<min) {
					best = a;
					min = dis;
				}
			}
			
			txtResult.appendText("\nIL NODO PIU' VICINO E'\n\n");
			txtResult.appendText(best.toString()+" CON DISTANZA : "+min);
			
		}
		else txtResult.appendText("INSERIRE UNA DISTANZA IN KM VALIDA\n\n");
		
	}

	@FXML
	void doSimula(ActionEvent event) {
		
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
