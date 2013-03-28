/**
* @Author：胡家威  
* @CreateTime：2011-5-23 下午10:47:38
* @Description：AC
*/

package ACM.CSU;

import java.util.ArrayList;
import java.util.Scanner;

public class CSU1005 {
	
	public static ArrayList<TreeNode> pre=new ArrayList<TreeNode>();
	public static ArrayList<TreeNode> in=new ArrayList<TreeNode>();
	public static ArrayList<TreeNode> post=new ArrayList<TreeNode>();
	
	public static void main(String[] args) {
		Scanner sin=new Scanner(System.in);
		int t,key,s,i,root;
		t=sin.nextInt();
		while(t-->0){
			pre.clear();
			in.clear();
			post.clear();
			s=sin.nextInt();
			root=sin.nextInt();
			TreeNode tree=new TreeNode(root);
			s--;
			while(s-->0){
				key=sin.nextInt();
				TreeNode node=new TreeNode(key);
				insert(tree,node);
			}
			preorder(tree);
			inorder(tree);
			postorder(tree);
			s=pre.size();
			for(i=0;i<s;i++){
				System.out.print(pre.get(i).key);
				if(i!=s-1){
					System.out.print(" ");
				}
			}
			System.out.println();
			for(i=0;i<s;i++){
				System.out.print(in.get(i).key);
				if(i!=s-1){
					System.out.print(" ");
				}
			}
			System.out.println();
			for(i=0;i<s;i++){
				System.out.print(post.get(i).key);
				if(i!=s-1){
					System.out.print(" ");
				}
			}
			System.out.println();
			System.out.println();
		}
	}
	
	/**
	 * 插入操作 
	 */
	public static void insert(TreeNode t,TreeNode n){
		if(t.key>n.key){
			if(t.lc!=null){
				insert(t.lc,n);
			}else{
				t.lc=n;
			}
		}else{
			if(t.rc!=null){
				insert(t.rc,n);
			}else{
				t.rc=n;
			}
		}
	}
	
	/**
	 * 前序遍历
	 */
	public static void preorder(TreeNode t){
		if(t!=null){
			pre.add(t);
			preorder(t.lc);
			preorder(t.rc);
		}
	}
	
	/**
	 * 中序遍历
	 */
	public static void inorder(TreeNode t){
		if(t!=null){
			inorder(t.lc);
			in.add(t);
			inorder(t.rc);
		}
	}
	
	/**
	 * 后序遍历
	 */
	public static void postorder(TreeNode t){
		if(t!=null){
			postorder(t.lc);
			postorder(t.rc);
			post.add(t);
		}
	}

}

class TreeNode{
	int key;
	TreeNode lc;
	TreeNode rc;
	
	public TreeNode(int key){
		this.key=key;
		this.lc=null;
		this.rc=null;
	}
}

