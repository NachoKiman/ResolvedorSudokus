package backend;

import java.util.List;

public class Sudoku {

	private List<EntidadSudoku> filas;
	private List<EntidadSudoku> columnas;
	private List<EntidadSudoku> cuadrados;
	private int orden;

	public Sudoku(List<EntidadSudoku> filas, List<EntidadSudoku>columnas, List<EntidadSudoku> cuadrados) {
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
			mostrar();
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

		return resuelto;

	}

	// Se itera por fila, porque es lo mismo cualquier EntidadSudoku
	private boolean buscarValoresNuevos() {
		boolean resuelto = true;
		for (int i = 0; i < orden; i++) {
			resuelto = filas.get(i).buscarValoresNuevos()&&resuelto ;
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
		String aMostrar="";
		String valor="";
		for(int i=0; i<orden;i++)
		{
			EntidadSudoku entidad = filas.get(i);
			for(int j=0; j<orden;j++)
			{
				if(entidad.getEspacios().get(j).getValor()!=-1)
				{
					valor = " "+entidad.getEspacios().get(j).getValor();
				}
				else
				{
					valor = ""+entidad.getEspacios().get(j).getValor();
				}
				aMostrar+=valor+" ";
			}
			aMostrar+="\n";
		}
		
		System.out.println(aMostrar);
		
	}
	
	public void mostrarEstadoActual()
	{
		String aMostrar="";
		String valor="";
		for(int i=0; i<orden;i++)
		{
			EntidadSudoku entidad = filas.get(i);
			for(int j=0; j<orden;j++)
			{
				Espacio espacio = entidad.getEspacios().get(j);
				if(espacio.getValor()!=-1)
				{
					valor = " "+espacio.getValor();
				}
				else
				{
					valor = ""+espacio.getValor();
				}
				aMostrar+=valor+": "+espacio.mostrarOpciones();
				aMostrar+="\n";
			}
			
		}
		
		System.out.println(aMostrar);
	}

}
