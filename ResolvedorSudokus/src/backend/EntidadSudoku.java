package backend;

import java.util.List;


public abstract class EntidadSudoku 
{
	
	private List<Espacio> entidadSudoku;

	public EntidadSudoku(List<Espacio> entidadSudoku) 
	{
		super();
		this.entidadSudoku = entidadSudoku;
	}
	
	private Boolean resolver(Espacio espacio)
	{
		return false;
	}
	
	public void editarValoresFaltasma()
	{
		
	}
}

