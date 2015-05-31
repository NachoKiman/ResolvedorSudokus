package backend;

import java.util.List;

public class Espacio {

	private List<Integer> opciones;
	private Integer valor =-1;
	private List<Integer> valoresFantasma;
	
	public Boolean estaResuelto()
	{
		boolean resuelto = false;
		if (opciones.size() == 1)
		{
			setValor(opciones.get(0));
			resuelto = true;
		}
		return resuelto;
		
	}
	
	public void reducirOpciones(List<Espacio> otrosEspacios)
	{
		//Reduzco las opciones segun los valores de otros Espacios de la EntidadSudoku
		for(int i=0; i<otrosEspacios.size();i++)
		{
			Espacio espacioNemesis = otrosEspacios.get(i);
			this.comparar(espacioNemesis);
		}
		
		//Reduzco opciones 
	}
	
	
	public void comparar(Espacio espacioNemesis)
	{
		int valorNemesis = espacioNemesis.getValor();
		if((valorNemesis!=-1)&&(opciones.contains(valorNemesis)))
		{
			opciones.remove(valorNemesis);
		}
	}

	public List<Integer> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<Integer> opciones) {
		this.opciones = opciones;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
	
}
