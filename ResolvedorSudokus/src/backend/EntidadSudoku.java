package backend;

import java.util.List;

public class EntidadSudoku 
{
	
	private List<Espacio> entidadSudoku;

	public EntidadSudoku(List<Espacio> entidadSudoku) 
	{
		super();
		this.entidadSudoku = entidadSudoku;
	}
	
	private Boolean resolver(Espacio espacio)
	{
		boolean resuelto = true;
		if(!espacio.estaResuelto())
		{
			resuelto=false;
		}
		return resuelto;
	}
}

