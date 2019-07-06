package it.polito.tdp.flight.model;

public class TestSimulatore {

	public static void main(String[] args) {
		Simulatore sim = new Simulatore();
		
		sim.init(20);
		sim.run();

	}

}
