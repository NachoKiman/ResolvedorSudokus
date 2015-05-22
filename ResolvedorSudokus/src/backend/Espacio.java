package backend;

import java.util.List;

public class Espacio {

	private List<Integer> opciones;
	private Integer valor =-1;
	
	public Boolean estaResuelto()
	{
		return opciones.size() == 1;
	}
	
	public void reducirOpciones(List<Espacio> otrosEspacios)
	{
		for(int i=0; i<otrosEspacios.size();i++)
		{
			Espacio espacioNemesis = otrosEspacios.get(i);
			this.comparar(espacioNemesis);
		}
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
