package it.polito.tdp.flight.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private Graph<Aereoporto, DefaultWeightedEdge> grafo;
	private List<Aereoporto> aereoporti;
	private Map<Integer, Aereoporto> mapAereoporti;
	FlightDAO dao = new FlightDAO();

	public void creaGrafo(int distanza) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.aereoporti = dao.getAereoporti();
		this.mapAereoporti = new HashMap<Integer, Aereoporto>();
		
		for(Aereoporto a : this.aereoporti) {
			mapAereoporti.put(a.getId(), a);
		}
	
		
		for(Aereoporto a : this.aereoporti) {
			List<Aereoporto> vicini = dao.getAereoportiVicini(a.getId(), mapAereoporti);
			
			for(Aereoporto finale : vicini) {
				if(LatLngTool.distance(a.getPosizione(), finale.getPosizione(), LengthUnit.KILOMETER)>distanza) {
					double peso = LatLngTool.distance(a.getPosizione(), finale.getPosizione(), LengthUnit.KILOMETER)/900;
					Graphs.addEdgeWithVertices(grafo, a, finale, peso);
				}
			}
		}
	}

	public Graph<Aereoporto, DefaultWeightedEdge> getGrafo() {
		return this.grafo;
	}
	
	
	public Set<Aereoporto> getRaggiungibili(Aereoporto source){
		Set<Aereoporto> visitati = new HashSet<Aereoporto>();
		GraphIterator<Aereoporto, DefaultWeightedEdge> dfv = new DepthFirstIterator<Aereoporto, DefaultWeightedEdge>(grafo, source);
		while(dfv.hasNext()) {
			visitati.add(dfv.next());
		}
		
		return visitati;
		
	}

	public List<Aereoporto> getAereoporti() {
		// TODO Auto-generated method stub
		return this.aereoporti;
	}

	public Set<Aereoporto> getRaggiungibili(int i) {
		Aereoporto source = this.mapAereoporti.get(i);
		Set<Aereoporto> visitati = new HashSet<Aereoporto>();
		GraphIterator<Aereoporto, DefaultWeightedEdge> dfv = new DepthFirstIterator<Aereoporto, DefaultWeightedEdge>(grafo, source);
		while(dfv.hasNext()) {
			visitati.add(dfv.next());
		}
		
		return visitati;
	}

	public Aereoporto getAereoporto(int i) {
		// TODO Auto-generated method stub
		return this.mapAereoporti.get(i);
	}

}
