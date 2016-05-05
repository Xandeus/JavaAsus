package binaryTrees;
//� A+ Computer Science  -  www.apluscompsci.com

import java.awt.Point;
import java.util.ArrayList;

public class BinarySearchTree {
	private TreeNode root;
	private ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
	private ArrayList<Point> points = new ArrayList<Point>();
	public BinarySearchTree() {
		root = null;
	}

	public void add(Comparable val) {
		root = add(val, root, 0, false);
	}

	private TreeNode add(Comparable val, TreeNode tree, int level,boolean isLeft) {
		if (tree == null)
			tree = new TreeNode(val, level, isLeft);

		Comparable treeValue = tree.getValue();
		int dirTest = val.compareTo(treeValue);

		if (dirTest < 0)
			tree.setLeft(add(val, tree.getLeft(), level + 1,true));
		else if (dirTest > 0)
			tree.setRight(add(val, tree.getRight(), level + 1,false));

		return tree;
	}

	public void inOrder() {
		inOrder(root);
		System.out.println("\n\n");
	}

	private void inOrder(TreeNode tree) {
		if (tree != null) {
			inOrder(tree.getLeft());
			System.out.print(tree.getValue() + " ");
			inOrder(tree.getRight());
		}
	}

	public void preOrder() {
		preOrder(root);
		System.out.println("\n\n");
	}

	private void preOrder(TreeNode tree) {
		if (tree != null) {
			System.out.print(tree.getValue() + " ");
			preOrder(tree.getLeft());
			preOrder(tree.getRight());
		}
	}

	public void postOrder() {
		postOrder(root);
		System.out.println("\n\n");
	}

	private void postOrder(TreeNode tree) {
		if (tree != null) {
			postOrder(tree.getLeft());
			postOrder(tree.getRight());
			System.out.print(tree.getValue() + " ");
		}
	}

	public void revOrder() {
		revOrder(root);
		System.out.println("\n\n");
	}

	private void revOrder(TreeNode tree) {
		if (tree != null) {
			revOrder(tree.getRight());
			System.out.print(tree.getValue() + " level: " + tree.getLevel() + "\n");
			revOrder(tree.getLeft());

		}
	}

	public int getNumLevels() {
		return getNumLevels(root);
	}

	private int getNumLevels(TreeNode tree) {
		if (tree == null)
			return 0;
		else if (getNumLevels(tree.getLeft()) > getNumLevels(tree.getRight()))
			return 1 + getNumLevels(tree.getLeft());
		else
			return 1 + getNumLevels(tree.getRight());
	}

	public int getTreeHeight() {
		return getTreeHeight(root) - 1;
	}

	public int getTreeHeight(TreeNode tree) {
		if (tree == null)
			return 0;
		else if (getTreeHeight(tree.getLeft()) > getNumLevels(tree.getRight()))
			return 1 + getTreeHeight(tree.getLeft());
		else
			return 1 + getTreeHeight(tree.getRight());
	}

	public int getNumNodes() {
		return getNumNodes(root);
	}

	public int getNumNodes(TreeNode tree) {
		if (tree == null) {
			return 0;
		} else
			return 1 + getNumNodes(tree.getLeft()) + getNumNodes(tree.getRight());
	}

	public int getNumLeaves() {
		return getNumLeaves(root);
	}

	public int getNumLeaves(TreeNode tree) {
		if (tree == null) {
			return 0;
		} else if (tree.getLeft() == null && tree.getRight() == null)
			return 1;
		else
			return getNumLeaves(tree.getLeft()) + getNumLeaves(tree.getRight());
	}

	public boolean isFull() {
		return isFull(root);
	}

	public boolean isFull(TreeNode tree) {
		if (tree.getRight() == null && tree.getLeft() == null) {
			return true;
		} else if (tree.getRight() == null || tree.getLeft() == null) {
			return false;
		} else {
			return isFull(tree.getRight()) && isFull(tree.getLeft());
		}
	}

	public ArrayList<Point> toPointArray() {
		points.clear();
		addToArray(root,0,0);
		return points;
	}

	private void addToArray(TreeNode tree,int x, int y) {
		if (tree != null) {
			points.add(new Point(x,y));
			addToArray(tree.getLeft(),x-100,y+100);
			addToArray(tree.getRight(),x+100,y+100);
		}
	}
	public ArrayList<TreeNode> toArray() {
		nodes.clear();
		addToArray(root);
		return nodes;
	}

	private void addToArray(TreeNode tree) {
		if (tree != null) {
			nodes.add(tree);
			addToArray(tree.getLeft());
			addToArray(tree.getRight());
		}
	}
	// public int getTreeWidth() {
	// return getTreeWidth(root)-1;
	// }
	// public int getTreeWidth(TreeNode tree) {
	// if (tree == null)
	// return 0;
	// else if(tree.)
	// return 1 + getTreeWidth(tree.getRight());
	// }
	// add getNumLeaves, getWidth, getHeight, getNumNodes, and isFull
	// public int getNumLeaves() {
	//
	// }
	//
	// public int getWidth() {
	//
	// }
	//
	//
	// public int getNodes() {
	//
	// }
	//
	//
	// public boolean isFull() {
	//
	// }
	// add extra credit options here - 10 points each

	// search

	// maxNode

	// minNode

	// level order

	// display like a tree

	// remove

	public String toString() {
		inOrder();
		return "Tree as a string ";

	}

	private String toString(TreeNode tree) {
		return "";
	}
}
