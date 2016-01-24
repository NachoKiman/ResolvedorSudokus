package tree;

import java.util.ArrayList;
import java.util.List;

import backend.Sudoku;

public class TreeNode {

	private Sudoku sudoku;
	private TreeNode parent;
	private List<TreeNode> caminos = new ArrayList<>();

	public TreeNode(Sudoku sudoku, TreeNode parent) {
		this.sudoku = sudoku;
		this.parent = parent;
	}

	public TreeNode getParent() {
		return this.parent;
	}

	public Sudoku getSudoku() {
		return sudoku;
	}

	public void addCaminos(TreeNode camino) {
		caminos.add(camino);
	}

	public TreeNode getNewCamino() {

		return caminos.get(0);

	}
	
	

	public List<TreeNode> getCaminos() {
		return caminos;
	}

	/**
	 * Borra la primer opcion de camino.
	 * 
	 * @return true si pudo borrar.
	 */
	public boolean deleteCamino() {
		boolean puedeBorrar = caminos.size() != 0;
		if (puedeBorrar) {
			caminos.remove(0);
		}
		return puedeBorrar;
	}

	public void setCaminos(List<TreeNode> caminos) {
		this.caminos = caminos;
	}
	
	

}
