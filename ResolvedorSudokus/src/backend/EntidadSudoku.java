package backend;

import java.util.ArrayList;
import java.util.List;

public class EntidadSudoku {

	private List<Espacio> espacios;

	public EntidadSudoku() {
		super();
		espacios = new ArrayList<>();
	}

	public List<Espacio> getEspacios() {
		return espacios;
	}

	public void setEspacios(List<Espacio> espacios) {
		this.espacios = espacios;
	}

	public void reducirOpciones() {
		List<Espacio> espaciosAComparar;
		for (int j = 0; j < espacios.size(); j++) {
			// Setteo los espacios a comparar como todos menos el que estoy
			// analizando

			espaciosAComparar = this.obtenerListaEspacios();
			espaciosAComparar.remove(j);

			espacios.get(j).reducirOpciones(espaciosAComparar);
		}

	}

	// Este metodo es necesario porque necesito una lista de espacios que no sea
	// LA lista de espacios
	private List<Espacio> obtenerListaEspacios() {
		List<Espacio> listaNueva = new ArrayList<>();
		for (int i = 0; i < espacios.size(); i++) {
			listaNueva.add(espacios.get(i));
		}
		return listaNueva;
	}

	public boolean buscarValoresNuevos() {
		boolean resuelto = true;
		for (int i = 0; i < espacios.size(); i++) {
			resuelto = espacios.get(i).estaResuelto() && resuelto;
		}

		return resuelto;

	}

	public void buscarValoresOcultos() {
		// Para contabilizar cuantas veces aparece cada numero en las opciones
		// de todos los Espacios
		int[] opciones = inicioArrayOpciones();

		// Devuelve si en la Entidad hay valores ocultos
		boolean hayValoresOcultos = llenoArray(opciones);

		if (hayValoresOcultos) {
			asignoValoresOcultos(opciones);
		}

	}

	private int[] inicioArrayOpciones() {
		int[] opciones = new int[espacios.size()];
		for (int i = 0; i < espacios.size(); i++) {
			opciones[i] = 0;
		}
		return opciones;
	}

	private boolean llenoArray(int[] opciones) {
		List<Integer> opcionesEspacio;
		// Itero los espacios
		for (int i = 0; i < espacios.size(); i++) {
			opcionesEspacio = espacios.get(i).getOpciones();

			// Itero las opciones del Espacio incrementando el contador de
			// opciones.
			for (int j = 0; j < opcionesEspacio.size(); j++) {
				opciones[opcionesEspacio.get(j) - 1]++;
			}

		}
		// Esto es re sucio, pero nadie me puede decir que no es genial!

		return hayValoresOcultos(opciones);
	}

	private boolean hayValoresOcultos(int[] opciones) {
		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i] == 1) {
				return true;
			}
		}
		return false;
	}

	private void asignoValoresOcultos(int[] opciones) {

		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i] == 1) {
				buscarEspacioConOpcion(i + 1);
			}
		}

	}

	private void buscarEspacioConOpcion(int numero) {
		for (int j = 0; j < espacios.size(); j++) {
			if (espacios.get(j).getOpciones().contains(numero)) {
				espacios.get(j).setValor(numero);
			}

		}

	}

}
