package com.java.main.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		buscarValoresBloqueantes(opciones);
	}

	private int[] inicioArrayOpciones() {
		int[] opciones = new int[espacios.size()];
		for (int i = 0; i < espacios.size(); i++) {
			opciones[i] = 0;
		}
		return opciones;
	}

	// Voy llenando el array de opciones con la cantidad de veces que aparece
	// cada opcion en la entidad
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

	// Busco si hay valores ocultos
	private boolean hayValoresOcultos(int[] opciones) {
		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i] == 1) {
				return true;
			}
		}
		return false;
	}

	// Busco que Espacio de la Entidad es el unico que tiene los valores ocultos
	// y los asigno
	private void asignoValoresOcultos(int[] opciones) {
		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i] == 1) {
				buscarEspacioConOpcion(i + 1);
			}
		}
	}

	// Busco que espacio de la Entidad tiene dentro de sus @opciones la opcion
	// @numero
	private void buscarEspacioConOpcion(int numero) {
		for (int j = 0; j < espacios.size(); j++) {
			if (espacios.get(j).getOpciones().contains(numero)) {
				espacios.get(j).setValor(numero);
			}
		}
	}

	// Busco si hay espacios de la entidad que tengan la mismas opciones(n
	// espacios con n opciones iguales)
	public void buscarValoresFantasmas() {
		int cantidadOpcionesEncontradas;
		for (int i = 0; i < espacios.size(); i++) {
			// Analizo espacio a espacio
			Espacio espacioAnalizado = espacios.get(i);
			// Lo inicializo en -1 porque siempre va a haber un espacio que
			// tenga las misma opciones...el mismo espacio que preguntó
			cantidadOpcionesEncontradas = -1;
			for (int j = 0; j < espacios.size(); j++) {
				cantidadOpcionesEncontradas += espacioAnalizado.sonFantasma(espacios.get(j));
			}
			// Si hay n espacios con n opciones iguales, saco estas n opciones
			// de los espacios que no son fantasma
			if (cantidadOpcionesEncontradas == espacioAnalizado.getOpciones().size()) {
				for (int j = 0; j < espacios.size(); j++) {
					// Si no es un espacio fantasma, le saco los valores
					// fantasmas a las opciones
					if (espacioAnalizado.sonFantasma(espacios.get(j)) != 1) {
						espacios.get(j).quitarValores(espacioAnalizado.getOpciones());
					}
				}
			}
		}

	}

	// TODO falta refactorizar
	public void sacarValoresFila(int numeroDeCuadrado, List<Integer> opcionesAQuitar) {
		if ((numeroDeCuadrado == 0) || (numeroDeCuadrado == 3) || (numeroDeCuadrado == 6)) {
			// Empieza por el que tiene que empezar
			for (int i = 3; i < 9; i++) {
				espacios.get(i).quitarValores(opcionesAQuitar);
			}
		} else if ((numeroDeCuadrado == 1) || (numeroDeCuadrado == 4) || (numeroDeCuadrado == 7)) {
			// PERDON PROGRAMACION, PERO NO TENIA GANAS DE PENSAR COMO SACAR EL
			// DEL MEDIO
			// Empieza por el que tiene que empezar
			for (int i = 0; i < 3; i++) {
				espacios.get(i).quitarValores(opcionesAQuitar);
			}
			// Empieza por el que tiene que empezar
			for (int i = 6; i < 9; i++) {
				espacios.get(i).quitarValores(opcionesAQuitar);
			}
		} else if ((numeroDeCuadrado == 2) || (numeroDeCuadrado == 5) || (numeroDeCuadrado == 8)) {
			// Empieza por el que tiene que empezar
			for (int i = 0; i < 6; i++) {
				espacios.get(i).quitarValores(opcionesAQuitar);
			}
		}

	}

	// TODO falta refactorizar
	public void sacarValoresColumna(int numeroDeCuadrado, List<Integer> opcionesAQuitar) {
		if ((numeroDeCuadrado == 0) || (numeroDeCuadrado == 1) || (numeroDeCuadrado == 2)) {
			// Empieza por el que tiene que empezar
			for (int i = 3; i < 9; i++) {
				espacios.get(i).quitarValores(opcionesAQuitar);
			}
		} else if ((numeroDeCuadrado == 3) || (numeroDeCuadrado == 4) || (numeroDeCuadrado == 5)) {
			// PERDON PROGRAMACION, PERO NO TENIA GANAS DE PENSAR COMO SACAR EL
			// DEL MEDIO
			// Empieza por el que tiene que empezar
			for (int i = 0; i < 3; i++) {
				espacios.get(i).quitarValores(opcionesAQuitar);
			}
			// Empieza por el que tiene que empezar
			for (int i = 6; i < 9; i++) {
				espacios.get(i).quitarValores(opcionesAQuitar);
			}
		} else if ((numeroDeCuadrado == 6) || (numeroDeCuadrado == 7) || (numeroDeCuadrado == 8)) {
			// Empieza por el que tiene que empezar
			for (int i = 0; i < 6; i++) {
				espacios.get(i).quitarValores(opcionesAQuitar);
			}
		}
	}

	// Quiero buscar valores que solo puedan ir en un lugar particular
	// TODO falta refactorizar para que sean mas que 9

	public void buscarValoresBloqueantes(int[] opciones) {
		for (int i = 2; i < 9; i++) {
			hayValorBloqueante(opciones, i);
		}
	}

	/**
	 * Si hay n numeros que aparecen solo n veces, es posible que sea un valor
	 * bloqueante, para checkearlo hay que ver si esos valores pertenecen a solo
	 * n espacios.
	 */
	// TODO QUE ASCO DE CODIGO, es lo que hay
	private void hayValorBloqueante(int[] opciones, int numeroABuscar) {
		int i = 0;
		List<Integer> opcionesEncontradas = new ArrayList<>();
		int[] indicesBloqueantes = inicioArrayOpciones();
		for (int j = 0; j < opciones.length; j++) {
			if (opciones[j] == numeroABuscar) {
				opcionesEncontradas.add(j + 1);
				i++;
			}
		}
		if (i == numeroABuscar) {
			int h = 0;
			for (int k = 0; k < espacios.size(); k++) {
				Espacio espacio = espacios.get(k);
				if (espacio.getOpciones().containsAll(opcionesEncontradas)) {
					indicesBloqueantes[h] = k;
					h++;
				}
			}
			if (h == numeroABuscar) {
				for (int n = 0; n < h; n++) {
					espacios.get(indicesBloqueantes[n]).getOpciones().clear();
					espacios.get(indicesBloqueantes[n]).getOpciones().addAll(opcionesEncontradas);
				}
			}
		}
	}

	public boolean hayError() {
		ArrayList<Integer> numeros = new ArrayList<>();
		numeros.add(1);
		numeros.add(2);
		numeros.add(3);
		numeros.add(4);
		numeros.add(5);
		numeros.add(6);
		numeros.add(7);
		numeros.add(8);
		numeros.add(9);
		for (int i = 0; i < 9; i++) {
			Integer valorACorroborar = getEspacios().get(i).getValor();
			if (!valorACorroborar.equals(-1) && !numeros.contains(valorACorroborar)) {
				return true;
			}
			numeros.remove(valorACorroborar);
		}
		return false;
	}

	public void probarValor() {
		for (int i = 0; i < 9; i++) {
			if (espacios.get(i).esProbable()) {
				espacios.get(i).probar();
			}
		}
	}

	public boolean estaResuelto() {
		boolean estaResuelto = true;
		int length = espacios.size();
		for (int i = 0; i < length; i++) {
			estaResuelto = estaResuelto && espacios.get(i).estaResuelto();
		}
		return estaResuelto;
	}

	public void resolverFullRandomMode() {
		Random random = new Random();
		int length = espacios.size();
		for (int i = 0; i < length; i++) {
			Espacio espacio = espacios.get(i);
			if (!espacio.estaResuelto()) {
				espacio.setValor(random.nextInt(length) + 1);
			}
		}
	}
}
