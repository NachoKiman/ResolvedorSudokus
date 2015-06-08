package backend;

import java.util.List;

public abstract class EntidadSudoku {

	private List<Espacio> espacios;

	public EntidadSudoku(List<Espacio> entidadSudoku) {
		super();
		this.espacios = entidadSudoku;
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
			espaciosAComparar = espacios;
			espaciosAComparar.remove(espacios.get(j));

			espacios.get(j).reducirOpciones(espaciosAComparar);
		}

	}

	public boolean buscarValoresNuevos() {
		boolean resuelto = true;
		for (int i = 0; i < espacios.size(); i++) {
			resuelto = resuelto && espacios.get(i).estaResuelto();
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
			// Si solo tiene una opcion, quiere decir que está resuelto.
			if (opcionesEspacio.size() != 1) {
				// Itero las opciones del Espacio incrementando el contador de
				// opciones.
				for (int j = 0; j < opcionesEspacio.size(); j++) {
					opciones[opcionesEspacio.get(j)]++;
				}
			}

		}
		// Esto es re sucio, pero nadie me puede decir que no es genial!
		return opciones.toString().contains("1");
	}

	private void asignoValoresOcultos(int[] opciones) {

		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i] == 1) {
				buscarEspacioConOpcion(opciones[i]);
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
