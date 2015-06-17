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

	//Voy llenando el array de opciones con la cantidad de veces que aparece cada opcion en la entidad
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

	//Busco si hay valores ocultos
	private boolean hayValoresOcultos(int[] opciones) {
		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i] == 1) {
				return true;
			}
		}
		return false;
	}
	//Busco que Espacio de la Entidad es el unico que tiene los valores ocultos y los asigno
	private void asignoValoresOcultos(int[] opciones) {

		for (int i = 0; i < opciones.length; i++) {
			if (opciones[i] == 1) {
				buscarEspacioConOpcion(i + 1);
			}
		}

	}

	//Busco que espacio de la Entidad tiene dentro de sus @opciones la opcion @numero
	private void buscarEspacioConOpcion(int numero) {
		for (int j = 0; j < espacios.size(); j++) {
			if (espacios.get(j).getOpciones().contains(numero)) {
				espacios.get(j).setValor(numero);
			}

		}

	}
	//Busco si hay espacios de la entidad que tengan la mismas opciones(n espacios con n opciones iguales)
	public void buscarValoresFantasmas() {
		int cantidadOpcionesEncontradas;
		for(int i = 0; i < espacios.size(); i++)
		{
			//Analizo espacio a espacio
			Espacio espacioAnalizado = espacios.get(i);
			//Lo inicializo en -1 porque siempre va a haber un espacio que tenga las misma opciones...el mismo espacio que preguntó
			cantidadOpcionesEncontradas = -1;
			for(int j = 0; j < espacios.size(); j++)
			{
				cantidadOpcionesEncontradas+=espacioAnalizado.sonFantasma(espacios.get(j));
			}
			//Si hay n espacios con n opciones iguales, saco estas n opciones de los espacios que no son fantasma
			if(cantidadOpcionesEncontradas==espacioAnalizado.getOpciones().size())
			{
				for(int j = 0; j < espacios.size(); j++)
				{
					//Si no es un espacio fantasma, le saco los valores fantasmas a las opciones
					if(espacioAnalizado.sonFantasma(espacios.get(j))!=1)
					{
						espacios.get(j).quitarValores(espacioAnalizado.getOpciones());
					}
				}
			}
		}
		
	}

}
