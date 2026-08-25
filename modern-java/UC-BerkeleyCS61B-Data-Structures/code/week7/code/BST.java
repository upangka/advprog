///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public class BST<T extends Comparable<? super T>>{
	private T key;
	private BST<T> left;
	private BST<T> right;

	public BST(T key){
		this(key,null,null);

	}

	public BST(T key,BST<T> left,BST<T> right){
		this.key = key;
		this.left = left;
		this.right = right;
	}


	/*
	 * 内部辅助方法，二分遍历binary search tree
	 */
	private static<T extends Comparable<? super T>> BST<T> find(
		BST<T> bst, T searchKey
	){
		if(bst == null){
			// 代表没有找到
			return null;
		}

		var ret = bst.key.compareTo(searchKey);
		if(ret == 0){
			// find target
			return bst;
		}else if(ret < 0){
			// 此时bst的key小于searchKey，说明在右边
			return find(bst.right,searchKey);
		}else{
			// 此时bst的key大于searchKey，说明目标在左边
			return find(bst.left, searchKey);
		}
	}

	public boolean contains(T key){
		BST<T> bst = find(this, key);
		return bst != null;
	}


	private static<T extends Comparable<? super T>> BST<T> insert(BST<T> bst,T key){

		if(bst == null){
			// always insert at a leaf node
			return new BST<T>(key);

		}

		// 找到要插入的位置,
		int ret = bst.key.compareTo(key);

		if(ret == 0){
			// 在binary search tree中不允许存在重复的元素
			return bst;
		}
		else if(ret < 0){
			// 此时说明要插入的元素大于当前节点，需要往右边插入
			bst.right = insert(bst.right,key);
		}else{
			// 反之，则左边
			bst.left = insert(bst.left,key);
		}

		return bst;
	}


	private static<T extends Comparable<? super T>> BST<T> delete(BST<T> bst,T key){
		if(bst == null){
			return null;
		}

		var ret = bst.key.compareTo(key);

		if(ret < 0){
			// 要删除的key在右边
			bst.right = delete(bst.right, key);
		}else if(ret > 0){
			// 要删除的key在左边
			bst.left = delete(bst.left, key);
		}else {
			// 找到了要删除的节点

			// 情况 1 & 2：没有子节点或只有一个子节点
			if(bst.left == null){
				return bst.right; // 用右子树（或 null）替代当前节点
			}

			if(bst.right == null){
				return bst.left; // 用左子树替代当前节点	
			}

			// 情况3有两个节点：hibbard deletion
			var predecessor = bst.left;
			while(predecessor.right != null){
				predecessor = predecessor.right;
			}
			bst.key = predecessor.key;
			bst.left = delete(bst.left, key);
			return bst;


			// dst是要删除的节点 找到前驱节点
			// var predecessor = bst.left;
			// var predecessorParent = bst;

			// while(predecessor.right != null){
			// 	predecessorParent = predecessor;
			// 	predecessor = predecessor.right;
			// }

			// // 继承要删除节点右节点	
			// predecessor.right = bst.right;
			// // 处理前驱节点要继承的左节点，如果前驱节点的父节点不是要删除dst节点，才需要处理
			// // 如果是dst不需要处理left,因为predecessor本身就是dst的左节点，它本身的左节点就是最终的左节点
			// if(predecessorParent != bst){
			// 	predecessorParent.right = predecessor.left;
			// 	predecessor.left = bst.left;
			// }
			// return predecessor;

		}


		// 返回当前节点，让递归的上层将其挂接到父节点
		return bst; 
	}

}
