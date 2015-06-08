package backend;

import java.util.ArrayList;
import java.util.List;

public class Espacio {

	private List<Integer> opciones;
	private Integer valor = -1;

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
			opciones.remove(valorNemesis);
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

}
