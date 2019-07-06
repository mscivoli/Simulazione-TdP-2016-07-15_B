package it.polito.tdp.flight.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.PriorityQueue;
import java.util.Queue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.flight.model.Evento.TipoEvento;

public class Simulatore {
	
	private int K;
	private PriorityQueue<Evento> queue;
	Model model = new Model();
	private Map<Integer, Aereoporto> passeggeri;
	Graph<Aereoporto, DefaultWeightedEdge> grafo;
	Random r = new Random();
	List<LocalTime> orari;
	
	
	public void init(int K) {
		this.K = K;
		this.queue = new PriorityQueue<>();
		this.passeggeri = new HashMap<Integer, Aereoporto>();
		this.orari = new LinkedList<LocalTime>();
		model.creaGrafo(800);
		
		grafo = model.getGrafo();
		
		List<Aereoporto> aereoportiParigi = new LinkedList<Aereoporto>();
		for(Aereoporto a : grafo.vertexSet()) {
			if(a.getCitta().equals("Paris")) {
				aereoportiParigi.add(a);
			}
		}
		
		List<Aereoporto> aereoportiNewYork = new LinkedList<Aereoporto>();
		for(Aereoporto a : grafo.vertexSet()) {
			if(a.getCitta().equals("New York")) {
				aereoportiNewYork.add(a);
			}
		}
		
		int k = r.nextInt(aereoportiParigi.size());
		int j = r.nextInt(aereoportiNewYork.size());
		
		for(int i=0; i<K/2; i++) {
			passeggeri.put(i, aereoportiParigi.get(k));
		}
		
		for(int i=k/2; i<K; i++) {
			passeggeri.put(i, aereoportiNewYork.get(j));
		}
		
		for(Integer i : passeggeri.keySet()) {
			queue.add(new Evento(TipoEvento.PARTENZA, passeggeri.get(i), i, LocalTime.of(07, 00)));
		}
		
		for(LocalTime i = LocalTime.of(7, 00); i.isBefore(LocalTime.of(23, 00)); i = i.plusHours(2)) {
			this.orari.add(i);
		}
		
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Evento e = queue.poll();
			System.out.println(e.toString());
			switch(e.getTipo()){
			
			case PARTENZA:
				if(passeggeri.get(e.getCodicePasseggero()).getN()<5) {
					List<Aereoporto> aereoportiPossibili = Graphs.neighborListOf(this.grafo, e.getAereoporto());
					if(!aereoportiPossibili.isEmpty()) {
						int k = r.nextInt(aereoportiPossibili.size());
						Aereoporto arrivo = aereoportiPossibili.get(k);
						if(grafo.containsEdge(e.getAereoporto(), arrivo)) {
							double minutiVolo = grafo.getEdgeWeight(grafo.getEdge(e.getAereoporto(), arrivo))/900*60;
							
							for(LocalTime l : this.orari) {
								LocalTime orario = e.getOrario().plusMinutes((long)minutiVolo);
								if(orario.isBefore(l)) {
									long durataAttesa = Duration.between(orario, l).toMinutes();
									queue.add(new Evento(TipoEvento.PARTENZA, arrivo, e.getCodicePasseggero(), orario.plusMinutes(durataAttesa)));
									passeggeri.put(e.getCodicePasseggero(), arrivo);
									passeggeri.get(e.getCodicePasseggero()).setN();
									break;
								}
						
							}
						}
					}
					
				}
				
		}
		
	}
	}
}










