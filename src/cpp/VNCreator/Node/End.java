package cpp.VNCreator.Node;

import cpp.VNCreator.Model.NodeType.nodeType;

/**
 * Contains the next Chapter when the node
 * reaches the end of the tree.
 * 
 * @author Stephen Jackson
 *
 */
public class End extends Node {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2840181516929447475L;

	public End(int id) {
		super(id, nodeType.End);
	}
}
