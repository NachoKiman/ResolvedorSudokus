package tree;

import java.util.ArrayList;


public class SudokuTree {
	
	private TreeNode lastNode;

	public SudokuTree(TreeNode lastNode) {
		this.lastNode = lastNode;
	}

	public TreeNode getLastNode() {
		return lastNode;
	}

	public void setLastNode(TreeNode lastNode) {
		if(!this.lastNode.equals(lastNode)){
			this.lastNode = lastNode;
		}else{
			this.lastNode.setCaminos(new ArrayList<TreeNode>());
			this.lastNode=findNewCamino();
		}
		
	}	
	
	public TreeNode findNewCamino(){
		
		lastNode = lastNode.getParent();
		if(lastNode!=null){
			lastNode.getCaminos().remove(0);
			if(lastNode.getCaminos().size()!=0){
				lastNode =  lastNode.getNewCamino();
			}else{
//				lastNode.getSudoku().mostrar();
				lastNode= findNewCamino();
			}
		}else{
			System.out.println("El sudoku no tiene solucion");
		}
		return lastNode;
		
	}
}
