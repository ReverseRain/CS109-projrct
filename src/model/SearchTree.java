package model;
class Node{
    public int value;
    public Node left;
    public Node right;
    public Node(int value){
        this.value=value;
    }
}

public class SearchTree {
    public Node root=null;
    public Node Search(int v){
        Node cur=root;
        while (cur!=null){
            if (cur.value==v){
                return cur;
            } else if (cur.value>v) {
                cur=cur.left;
            }else
                cur=cur.right;
        }
        return null;
    }
    public boolean insert (int v){
        if (root==null){
            root =new Node(v);
            return true;
        }
        Node parent=null;
        Node temp=root;
        while (temp!=null){
            parent=temp;
            if (temp.value==v){
                return false;
            } else if (temp.value>v) {
                temp=temp.left;
            }else temp=temp.right;
        }
        Node node=new Node(v);
        if (parent.value>v){
            parent.left=node;
        }else parent.right=node;
        return true;
    }
}
