package backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Sudoku {

	private List<EntidadSudoku> filas;
	private List<EntidadSudoku> columnas;
	private List<EntidadSudoku> cuadrados;
	private int orden;

	public Sudoku(List<EntidadSudoku> filas, List<EntidadSudoku> columnas,
			List<EntidadSudoku> cuadrados) {
		super();
		this.filas = filas;
		this.columnas = columnas;
		this.cuadrados = cuadrados;
		orden = filas.size();
	}

	public void resolver() {
		boolean resuelto = false;
		while (!resuelto) {
			reducirOpciones();
			resuelto = manejarResultado();
//			mostrar();
			mostrarEstadoActual();
		}
	}

	public void reducirOpciones() {

		reducirOpcionesEntidad(filas);
		reducirOpcionesEntidad(columnas);
		reducirOpcionesEntidad(cuadrados);
	}

	private void reducirOpcionesEntidad(List<EntidadSudoku> entidades) {
		for (int i = 0; i < orden; i++) {
			entidades.get(i).reducirOpciones();
		}
	}

	public boolean manejarResultado() {
		boolean resuelto;
		resuelto = buscarValoresNuevos();
		buscarValoresOcultos();
		buscarValoresFantasmas();
		buscarValoresLineales();

		return resuelto;

	}

	private void buscarValoresLineales() {
		// Solo se busca en columnas y filas y los cuadrados ayudan
		buscarValoresLinealesVertical();
		buscarValoresLinealesHorizontal();
		

	}

	// TODO Hacer generico para cualquier tamaño, pero paja
	private void buscarValoresLinealesVertical() {
		// Itero los cuadrados
				for (int i = 0; i < orden; i++) {
					EntidadSudoku cuadrado = cuadrados.get(i);
					List<Integer> opcionesLinea1 = new ArrayList<Integer>();
					List<Integer> opcionesLinea2 = new ArrayList<Integer>();
					List<Integer> opcionesLinea3 = new ArrayList<Integer>();
					// Busco en las lineas horizontales del cuadrado
					for (int j = 0; j < 3; j++) {
						opcionesLinea1.addAll(cuadrado.getEspacios().get(j * 3)
								.getOpciones());
						opcionesLinea2.addAll(cuadrado.getEspacios().get(j * 3 + 1)
								.getOpciones());
						opcionesLinea3.addAll(cuadrado.getEspacios().get(j * 3 + 2)
								.getOpciones());
					}
					//Saco los repetidos
					HashSet<Integer> hashSet1 = new HashSet<Integer>(opcionesLinea1);
					opcionesLinea1.clear();
					opcionesLinea1.addAll(hashSet1);
					
					HashSet<Integer> hashSet2 = new HashSet<Integer>(opcionesLinea2);
					opcionesLinea2.clear();
					opcionesLinea2.addAll(hashSet2);
					
					HashSet<Integer> hashSet3 = new HashSet<Integer>(opcionesLinea3);
					opcionesLinea3.clear();
					opcionesLinea3.addAll(hashSet3);
					
					List<Integer> opcionesFinalesLinea1 = new ArrayList<Integer>();
					opcionesFinalesLinea1.addAll(opcionesLinea1);
					opcionesFinalesLinea1.removeAll(opcionesLinea2);
					opcionesFinalesLinea1.removeAll(opcionesLinea3);

					List<Integer> opcionesFinalesLinea2 = new ArrayList<Integer>();
					opcionesFinalesLinea2.addAll(opcionesLinea2);
					opcionesFinalesLinea2.removeAll(opcionesLinea1);
					opcionesFinalesLinea2.removeAll(opcionesLinea3);

					List<Integer> opcionesFinalesLinea3 = new ArrayList<Integer>();
					opcionesFinalesLinea3.addAll(opcionesLinea3);
					opcionesFinalesLinea3.removeAll(opcionesLinea1);
					opcionesFinalesLinea3.removeAll(opcionesLinea2);

					// Si hay alguna opciones que exista solo en la linea 1, la saco de
					// toda la linea
					if (opcionesFinalesLinea1.size() != 0) {
						int numeroFila = obtenerColumna(i, 0);
						columnas.get(numeroFila).sacarValoresColumna(i, opcionesFinalesLinea1);
						
					}
					if (opcionesFinalesLinea2.size() != 0) {
						int numeroFila = obtenerColumna(i, 1);
						columnas.get(numeroFila).sacarValoresColumna(i, opcionesFinalesLinea2);
						
					}
					if (opcionesFinalesLinea3.size() != 0) {
						int numeroFila = obtenerColumna(i, 2);
						columnas.get(numeroFila).sacarValoresColumna(i, opcionesFinalesLinea3);
						
					}

				}

	}

	

	// TODO Hacer generico para cualquier tamaño, pero paja
	private void buscarValoresLinealesHorizontal() {
		// Itero los cuadrados
		for (int i = 0; i < orden; i++) {
			EntidadSudoku cuadrado = cuadrados.get(i);
			List<Integer> opcionesLinea1 = new ArrayList<Integer>();
			List<Integer> opcionesLinea2 = new ArrayList<Integer>();
			List<Integer> opcionesLinea3 = new ArrayList<Integer>();
			// Busco en las lineas horizontales del cuadrado
			for (int j = 0; j < 3; j++) {
				opcionesLinea1.addAll(cuadrado.getEspacios().get(j)
						.getOpciones());
				opcionesLinea2.addAll(cuadrado.getEspacios().get(j + 3)
						.getOpciones());
				opcionesLinea3.addAll(cuadrado.getEspacios().get(j + 6)
						.getOpciones());
			}
			
			//Saco los repetidos
			HashSet<Integer> hashSet1 = new HashSet<Integer>(opcionesLinea1);
			opcionesLinea1.clear();
			opcionesLinea1.addAll(hashSet1);
			
			HashSet<Integer> hashSet2 = new HashSet<Integer>(opcionesLinea2);
			opcionesLinea2.clear();
			opcionesLinea2.addAll(hashSet2);
			
			HashSet<Integer> hashSet3 = new HashSet<Integer>(opcionesLinea3);
			opcionesLinea3.clear();
			opcionesLinea3.addAll(hashSet3);
			
			List<Integer> opcionesFinalesLinea1 = new ArrayList<Integer>();
			opcionesFinalesLinea1.addAll(opcionesLinea1);
			opcionesFinalesLinea1.removeAll(opcionesLinea2);
			opcionesFinalesLinea1.removeAll(opcionesLinea3);

			List<Integer> opcionesFinalesLinea2 = new ArrayList<Integer>();
			opcionesFinalesLinea2.addAll(opcionesLinea2);
			opcionesFinalesLinea2.removeAll(opcionesLinea1);
			opcionesFinalesLinea2.removeAll(opcionesLinea3);

			List<Integer> opcionesFinalesLinea3 = new ArrayList<Integer>();
			opcionesFinalesLinea3.addAll(opcionesLinea3);
			opcionesFinalesLinea3.removeAll(opcionesLinea1);
			opcionesFinalesLinea3.removeAll(opcionesLinea2);

			// Si hay alguna opciones que exista solo en la linea 1, la saco de
			// toda la linea
			if (opcionesFinalesLinea1.size() != 0) {
				int numeroFila = obtenerFila(i, 0);
				filas.get(numeroFila).sacarValoresFila(i, opcionesFinalesLinea1);
				
			}
			if (opcionesFinalesLinea2.size() != 0) {
				int numeroFila = obtenerFila(i, 1);
				filas.get(numeroFila).sacarValoresFila(i, opcionesFinalesLinea2);
				
			}
			if (opcionesFinalesLinea3.size() != 0) {
				int numeroFila = obtenerFila(i, 2);
				filas.get(numeroFila).sacarValoresFila(i, opcionesFinalesLinea3);
				
			}

		}

	}
	//TODO falta refactor, pero paja, solo quiero que resuelva toooooodo
	// Sirve para columnas y filas
	private int obtenerFila(int numeroDeCuadrado, int numeroLineaCuadrado) {
		if ((numeroDeCuadrado == 0)||(numeroDeCuadrado == 1)||(numeroDeCuadrado == 2)) {
			switch (numeroLineaCuadrado) {
			case 0:
				return 0;

			case 1:
				return 1;

			case 2:
				return 2;

			default:
				return -1;
			}
		}
		if ((numeroDeCuadrado == 3)||(numeroDeCuadrado == 4)||(numeroDeCuadrado == 5)) {
			switch (numeroLineaCuadrado) {
			case 0:
				return 3;

			case 1:
				return 4;

			case 2:
				return 5;

			default:
				return -1;
			}
		}
		if ((numeroDeCuadrado == 6)||(numeroDeCuadrado == 7)||(numeroDeCuadrado == 8)) {
			switch (numeroLineaCuadrado) {
			case 0:
				return 6;

			case 1:
				return 7;

			case 2:
				return 8;

			default:
				return -1;
			}
		}
		return -1;

	}
	
	private int obtenerColumna(int numeroDeCuadrado, int numeroLineaCuadrado) {
		if ((numeroDeCuadrado == 0)||(numeroDeCuadrado == 3)||(numeroDeCuadrado == 6)) {
			switch (numeroLineaCuadrado) {
			case 0:
				return 0;

			case 1:
				return 1;

			case 2:
				return 2;

			default:
				return -1;
			}
		}
		if ((numeroDeCuadrado == 1)||(numeroDeCuadrado == 4)||(numeroDeCuadrado == 7)) {
			switch (numeroLineaCuadrado) {
			case 0:
				return 3;

			case 1:
				return 4;

			case 2:
				return 5;

			default:
				return -1;
			}
		}
		if ((numeroDeCuadrado == 2)||(numeroDeCuadrado == 5)||(numeroDeCuadrado == 8)) {
			switch (numeroLineaCuadrado) {
			case 0:
				return 6;

			case 1:
				return 7;

			case 2:
				return 8;

			default:
				return -1;
			}
		}
		return -1;
	}

	private void buscarValoresFantasmas() {
		buscarValoresFantasmasEntidad(filas);
		buscarValoresFantasmasEntidad(columnas);
		buscarValoresFantasmasEntidad(cuadrados);

	}

	private void buscarValoresFantasmasEntidad(List<EntidadSudoku> entidades) {
		for (int i = 0; i < orden; i++) {
			entidades.get(i).buscarValoresFantasmas();
		}
	}

	// Se itera por fila, porque es lo mismo cualquier EntidadSudoku
	private boolean buscarValoresNuevos() {
		boolean resuelto = true;
		for (int i = 0; i < orden; i++) {
			resuelto = filas.get(i).buscarValoresNuevos() && resuelto;
		}

		return resuelto;

	}

	// Se itera por todas las entidades buscando valores ocultos
	// Valores ocultos: Si en las opciones de un Espacio hay un numero que solo
	// aparece en este Espacio y no aparece
	// en las opciones de ningun otro Espacio de la entidad, ese numero es el
	// valor del Espacio en cuestion.
	private void buscarValoresOcultos() {

		buscarValoresOcultosEntidad(filas);
		buscarValoresOcultosEntidad(columnas);
		buscarValoresOcultosEntidad(cuadrados);

	}

	private void buscarValoresOcultosEntidad(List<EntidadSudoku> entidades) {
		for (int i = 0; i < orden; i++) {
			entidades.get(i).buscarValoresOcultos();
		}

	}

	public void mostrar() {
		String aMostrar = "";
		String valor = "";
		for (int i = 0; i < orden; i++) {
			EntidadSudoku entidad = filas.get(i);
			for (int j = 0; j < orden; j++) {
				if (entidad.getEspacios().get(j).getValor() != -1) {
					valor = " " + entidad.getEspacios().get(j).getValor();
				} else {
					valor = "" + entidad.getEspacios().get(j).getValor();
				}
				aMostrar += valor + " ";
			}
			aMostrar += "\n";
		}

		System.out.println(aMostrar);

	}

	public void mostrarEstadoActual() {
		String aMostrar = "";
		String valor = "";
		for (int i = 0; i < orden; i++) {
			EntidadSudoku entidad = filas.get(i);
			for (int j = 0; j < orden; j++) {
				Espacio espacio = entidad.getEspacios().get(j);
				if (espacio.getValor() != -1) {
					valor = " " + espacio.getValor();
				} else {
					valor = "" + espacio.getValor();
				}
				aMostrar += valor + ": " + espacio.mostrarOpciones();
				aMostrar += "\n";
			}

		}

		System.out.println(aMostrar);
	}

}
