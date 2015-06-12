package backend;

import java.util.ArrayList;
import java.util.List;

public class Espacio {
	

	private List<Integer> opciones;
	private Integer valor;

	public Espacio(int valor,int ordenSudoku)
	{
		inicializarOpciones(ordenSudoku);
		if(valor==0)
		{
			this.valor=-1;
		}
		else
		{
			setValor(valor);
		}
		
	}
	

	public Boolean estaResuelto() {
		boolean resuelto = false;
		if (opciones.size() == 1) {
			if (valor == -1) {
				setValor(opciones.get(0));
			}
			resuelto = true;
		}
		return resuelto;

	}

	public void reducirOpciones(List<Espacio> otrosEspacios) {
		// Reduzco las opciones segun los valores de otros Espacios de la
		// EntidadSudoku
		for (int i = 0; i < otrosEspacios.size(); i++) {
			Espacio espacioNemesis = otrosEspacios.get(i);
			this.comparar(espacioNemesis);
		}

	}

	public void comparar(Espacio espacioNemesis) {
		int valorNemesis = espacioNemesis.getValor();
		if ((espacioNemesis.tieneValor()) && (opciones.contains(valorNemesis))) {
			opciones.remove((Object)valorNemesis);
		}
	}

	public boolean tieneValor() {
		return valor != -1;
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
		opciones.clear();
		opciones.add(valor);
	}
	
	private void inicializarOpciones(int orden) {
		opciones = new ArrayList<>();
		for(int i=1;i<=orden;i++)
		{
			opciones.add(i);
		}
		
	}


	public String mostrarOpciones() {
		String aMostrar ="";
		for(int i=0; i<opciones.size();i++)
		{
			aMostrar += opciones.get(i)+", ";
		}
		return aMostrar;
	}

}
