package com.java.main.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.java.main.tree.SudokuTree;
import com.java.main.tree.TreeNode;

public class Sudoku {

	private List<EntidadSudoku> filas;
	private List<EntidadSudoku> columnas;
	private List<EntidadSudoku> cuadrados;
	private int orden;
	private SudokuTree sudokuTree;
	private int[][] sudo;
	String sudokuSuperViejo = "";
	String sudokuViejo = "";
	String sudokuActual = "";

	public Sudoku() {
		super();
		// Sudoku Vacio
		int sudoku[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
		this.initSudoku(sudoku);
	}

	public Sudoku(int[][] sudo) {
		super();
		this.sudo = sudo;
		this.initSudoku(sudo);
	}

	private void initSudoku(int[][] sudo) {
		filas = new ArrayList<>();
		columnas = new ArrayList<>();
		cuadrados = new ArrayList<>();
		// Esqueleto de sudokus sin Espacios
		for (int i = 0; i < sudo.length; i++) {
			filas.add(new EntidadSudoku());
			columnas.add(new EntidadSudoku());
			cuadrados.add(new EntidadSudoku());
		}
		for (int i = 1; i <= sudo.length; i++) {
			for (int j = 1; j <= sudo.length; j++) {
				// Si te preguntas "por que -1?", es que hice el
				// obtenerCuadrado y me dio paja cambiarlo :D
				Espacio espacio = new Espacio(sudo[i - 1][j - 1], sudo.length);
				filas.get(i - 1).getEspacios().add(espacio);
				columnas.get(j - 1).getEspacios().add(espacio);
				// Agregar los cudrados es mas dificil!
				int cuadrado = obtenerCuadrado(i, j);
				cuadrados.get(cuadrado).getEspacios().add(espacio);
			}
		}
		orden = filas.size();
		sudokuTree = new SudokuTree(new TreeNode(this, null));
	}

	private int obtenerCuadrado(int fila, int columna) {
		int cuadrado = -1;
		if ((fila == 1) || (fila == 2) || (fila == 3)) {
			if ((columna == 1) || (columna == 2) || (columna == 3)) {
				cuadrado = 0;
			} else if ((columna == 4) || (columna == 5) || (columna == 6)) {
				cuadrado = 1;
			} else if ((columna == 7) || (columna == 8) || (columna == 9)) {
				cuadrado = 2;
			}
		} else if ((fila == 4) || (fila == 5) || (fila == 6)) {
			if ((columna == 1) || (columna == 2) || (columna == 3)) {
				cuadrado = 3;
			} else if ((columna == 4) || (columna == 5) || (columna == 6)) {
				cuadrado = 4;
			} else if ((columna == 7) || (columna == 8) || (columna == 9)) {
				cuadrado = 5;
			}
		} else if ((fila == 7) || (fila == 8) || (fila == 9)) {
			if ((columna == 1) || (columna == 2) || (columna == 3)) {
				cuadrado = 6;
			} else if ((columna == 4) || (columna == 5) || (columna == 6)) {
				cuadrado = 7;
			} else if ((columna == 7) || (columna == 8) || (columna == 9)) {
				cuadrado = 8;
			}
		}
		return cuadrado;
	}

	public boolean resolver() {
		Sudoku sudokuTratado = this;
		boolean resuelto = false;
		boolean resueltoPorTree = false;
		while (!resuelto) {
			sudokuTratado.reducirOpciones();
			resuelto = sudokuTratado.manejarResultado();
			sudokuActual = sudokuTratado.toString();
			if (sudokuTratado.existeError()) {
				sudokuTratado = sudokuTratado.reestablecerSudoku().getSudoku();
			} else {
				if (sudokuActual.equals(sudokuViejo) && sudokuViejo.equals(sudokuSuperViejo)) {
					sudokuTratado.getSudokuTree().setLastNode(sudokuTratado.generarCaminos());
					sudokuTratado = sudokuTratado.getSudokuTree().getLastNode().getSudoku();
					resueltoPorTree = true;
				}
			}
			sudokuSuperViejo = sudokuViejo;
			sudokuViejo = sudokuActual;
		}
		sudokuTratado.mostrar();
		return resueltoPorTree;
	}

	public void resolverFullRandomMode() {
		boolean resuelto = false;
		while (!resuelto) {
			this.initSudoku(sudo);
			int length = filas.size();
			for (int i = 0; i < length; i++) {
				filas.get(i).resolverFullRandomMode();
			}
			resuelto = !this.existeError();
		}
		this.mostrar();
	}

	private TreeNode generarCaminos() {
		TreeNode lastNode = getSudokuTree().getLastNode();
		int[][] sudoku = toArraySudo();
		Sudoku sudoParaOpcions;
		sudoParaOpcions = new Sudoku(sudoku);
		sudoParaOpcions.setSudokuTree(this.getSudokuTree());
		sudoParaOpcions.setOrden(9);
		sudoParaOpcions.reducirOpciones();
		sudoParaOpcions.manejarResultado();
		for (int i = 0; i < orden; i++) {
			EntidadSudoku entidad = sudoParaOpcions.getFilas().get(i);

			for (int j = 0; j < orden; j++) {
				Espacio espacio = entidad.getEspacios().get(j);
				int length = espacio.getOpciones().size();
				if (length > 1) {
					for (int k = 0; k < length; k++) {
						crearCamino(k, lastNode, i, j, sudoParaOpcions);
					}
					if (lastNode.getCaminos().size() != 0) {
						lastNode = lastNode.getNewCamino();
					}
					return lastNode;
				}
			}
		}
		return lastNode;
	}

	private void crearCamino(int numeroDeOpcion, TreeNode lastNode, int fila, int columna, Sudoku sudoParaOpciones) {
		Sudoku opcion;
		int[][] sudoku = toArraySudo();
		opcion = new Sudoku(sudoku);
		opcion.setSudokuTree(this.getSudokuTree());
		opcion.setOrden(9);
		opcion.reducirOpciones();
		opcion.manejarResultado();

		Espacio espacio = opcion.getFilas().get(fila).getEspacios().get(columna);
		Espacio espacioParaOpciones = sudoParaOpciones.getFilas().get(fila).getEspacios().get(columna);
		espacio.setValor(espacioParaOpciones.getOpciones().get(numeroDeOpcion));
		TreeNode newLastNode = new TreeNode(opcion, lastNode);
		lastNode.addCaminos(newLastNode);
	}

	private int[][] toArraySudo() {
		int[][] sudoku = { { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 }, { 0 } };
		for (int i = 0; i < orden; i++) {
			EntidadSudoku entidad = filas.get(i);
			int[] fila = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			for (int j = 0; j < orden; j++) {
				Espacio espacio = entidad.getEspacios().get(j);
				int valor = espacio.getValor();
				if (valor == -1) {
					valor = 0;
				}
				fila[j] = valor;
			}
			sudoku[i] = fila;
		}
		return sudoku;
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
			List<Integer> opcionesLinea1 = new ArrayList<>();
			List<Integer> opcionesLinea2 = new ArrayList<>();
			List<Integer> opcionesLinea3 = new ArrayList<>();
			// Busco en las lineas horizontales del cuadrado
			for (int j = 0; j < 3; j++) {
				opcionesLinea1.addAll(cuadrado.getEspacios().get(j * 3).getOpciones());
				opcionesLinea2.addAll(cuadrado.getEspacios().get(j * 3 + 1).getOpciones());
				opcionesLinea3.addAll(cuadrado.getEspacios().get(j * 3 + 2).getOpciones());
			}
			// Saco los repetidos
			HashSet<Integer> hashSet1 = new HashSet<>(opcionesLinea1);
			opcionesLinea1.clear();
			opcionesLinea1.addAll(hashSet1);

			HashSet<Integer> hashSet2 = new HashSet<>(opcionesLinea2);
			opcionesLinea2.clear();
			opcionesLinea2.addAll(hashSet2);

			HashSet<Integer> hashSet3 = new HashSet<>(opcionesLinea3);
			opcionesLinea3.clear();
			opcionesLinea3.addAll(hashSet3);

			List<Integer> opcionesFinalesLinea1 = new ArrayList<>();
			opcionesFinalesLinea1.addAll(opcionesLinea1);
			opcionesFinalesLinea1.removeAll(opcionesLinea2);
			opcionesFinalesLinea1.removeAll(opcionesLinea3);

			List<Integer> opcionesFinalesLinea2 = new ArrayList<>();
			opcionesFinalesLinea2.addAll(opcionesLinea2);
			opcionesFinalesLinea2.removeAll(opcionesLinea1);
			opcionesFinalesLinea2.removeAll(opcionesLinea3);

			List<Integer> opcionesFinalesLinea3 = new ArrayList<>();
			opcionesFinalesLinea3.addAll(opcionesLinea3);
			opcionesFinalesLinea3.removeAll(opcionesLinea1);
			opcionesFinalesLinea3.removeAll(opcionesLinea2);

			// Si hay alguna opciones que exista solo en la linea 1, la saco de
			// toda la linea
			if (!opcionesFinalesLinea1.isEmpty()) {
				int numeroFila = obtenerColumna(i, 0);
				columnas.get(numeroFila).sacarValoresColumna(i, opcionesFinalesLinea1);

			}
			if (!opcionesFinalesLinea2.isEmpty()) {
				int numeroFila = obtenerColumna(i, 1);
				columnas.get(numeroFila).sacarValoresColumna(i, opcionesFinalesLinea2);

			}
			if (!opcionesFinalesLinea3.isEmpty()) {
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
			List<Integer> opcionesLinea1 = new ArrayList<>();
			List<Integer> opcionesLinea2 = new ArrayList<>();
			List<Integer> opcionesLinea3 = new ArrayList<>();
			// Busco en las lineas horizontales del cuadrado
			for (int j = 0; j < 3; j++) {
				opcionesLinea1.addAll(cuadrado.getEspacios().get(j).getOpciones());
				opcionesLinea2.addAll(cuadrado.getEspacios().get(j + 3).getOpciones());
				opcionesLinea3.addAll(cuadrado.getEspacios().get(j + 6).getOpciones());
			}

			// Saco los repetidos
			HashSet<Integer> hashSet1 = new HashSet<>(opcionesLinea1);
			opcionesLinea1.clear();
			opcionesLinea1.addAll(hashSet1);

			HashSet<Integer> hashSet2 = new HashSet<>(opcionesLinea2);
			opcionesLinea2.clear();
			opcionesLinea2.addAll(hashSet2);

			HashSet<Integer> hashSet3 = new HashSet<>(opcionesLinea3);
			opcionesLinea3.clear();
			opcionesLinea3.addAll(hashSet3);

			List<Integer> opcionesFinalesLinea1 = new ArrayList<>();
			opcionesFinalesLinea1.addAll(opcionesLinea1);
			opcionesFinalesLinea1.removeAll(opcionesLinea2);
			opcionesFinalesLinea1.removeAll(opcionesLinea3);

			List<Integer> opcionesFinalesLinea2 = new ArrayList<>();
			opcionesFinalesLinea2.addAll(opcionesLinea2);
			opcionesFinalesLinea2.removeAll(opcionesLinea1);
			opcionesFinalesLinea2.removeAll(opcionesLinea3);

			List<Integer> opcionesFinalesLinea3 = new ArrayList<>();
			opcionesFinalesLinea3.addAll(opcionesLinea3);
			opcionesFinalesLinea3.removeAll(opcionesLinea1);
			opcionesFinalesLinea3.removeAll(opcionesLinea2);

			// Si hay alguna opciones que exista solo en la linea 1, la saco de
			// toda la linea
			if (!opcionesFinalesLinea1.isEmpty()) {
				int numeroFila = obtenerFila(i, 0);
				filas.get(numeroFila).sacarValoresFila(i, opcionesFinalesLinea1);

			}
			if (!opcionesFinalesLinea2.isEmpty()) {
				int numeroFila = obtenerFila(i, 1);
				filas.get(numeroFila).sacarValoresFila(i, opcionesFinalesLinea2);

			}
			if (!opcionesFinalesLinea3.isEmpty()) {
				int numeroFila = obtenerFila(i, 2);
				filas.get(numeroFila).sacarValoresFila(i, opcionesFinalesLinea3);
			}
		}
	}

	// TODO falta refactor, pero paja, solo quiero que resuelva toooooodo
	// Sirve para columnas y filas
	private int obtenerFila(int numeroDeCuadrado, int numeroLineaCuadrado) {
		if ((numeroDeCuadrado == 0) || (numeroDeCuadrado == 1) || (numeroDeCuadrado == 2)) {
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
		if ((numeroDeCuadrado == 3) || (numeroDeCuadrado == 4) || (numeroDeCuadrado == 5)) {
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
		if ((numeroDeCuadrado == 6) || (numeroDeCuadrado == 7) || (numeroDeCuadrado == 8)) {
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
		if ((numeroDeCuadrado == 0) || (numeroDeCuadrado == 3) || (numeroDeCuadrado == 6)) {
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
		if ((numeroDeCuadrado == 1) || (numeroDeCuadrado == 4) || (numeroDeCuadrado == 7)) {
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
		if ((numeroDeCuadrado == 2) || (numeroDeCuadrado == 5) || (numeroDeCuadrado == 8)) {
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

	public String mostrar() {
		System.out.println(this);
		return this.toString();
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

	public String toString() {
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
		return aMostrar;
	}

	private void probarValor() {
		for (int i = 0; i < 9; i++) {
			filas.get(i).probarValor();
		}
	}

	public boolean existeError() {
		boolean hayError = false;
		for (int i = 0; i < 9; i++) {
			if (filas.get(i).hayError()) {
				return true;
			}
			if (columnas.get(i).hayError()) {
				return true;
			}
			if (cuadrados.get(i).hayError()) {
				return true;
			}
		}
		return hayError;
	}

	private TreeNode reestablecerSudoku() {
		return sudokuTree.findNewCamino();
	}

	public List<EntidadSudoku> getFilas() {
		return filas;
	}

	public void setFilas(List<EntidadSudoku> filas) {
		this.filas = filas;
	}

	public List<EntidadSudoku> getColumnas() {
		return columnas;
	}

	public void setColumnas(List<EntidadSudoku> columnas) {
		this.columnas = columnas;
	}

	public List<EntidadSudoku> getCuadrados() {
		return cuadrados;
	}

	public void setCuadrados(List<EntidadSudoku> cuadrados) {
		this.cuadrados = cuadrados;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public SudokuTree getSudokuTree() {
		return sudokuTree;
	}

	public void setSudokuTree(SudokuTree sudokuTree) {
		this.sudokuTree = sudokuTree;
	}

}
