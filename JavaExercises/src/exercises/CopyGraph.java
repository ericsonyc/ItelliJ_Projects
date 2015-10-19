package exercises;

import java.util.*;

/**
 * Created by ericson on 2015/3/1 0001.
 */
public class CopyGraph {
    static class Node{
        static int value=0;
        public Node(){
            value++;
        }
        public int getValue(){
            return value;
        }
        Vector<Node> neighbors=new Vector<Node>();
    }

    public static void main(String[] args){
        CopyGraph copy=new CopyGraph();
        Node start=new Node();
        copy.startNode(start);
        copy.printNode(start,"begin:");
        Node x=copy.copyGraph(start);
        copy.printNode(x,"end:");
    }

    private void printNode(Node start,String prefix){
        LinkedList<Node> list=new LinkedList<Node>();
        list.add(start);
        while (!list.isEmpty()){
            Node temp=list.pop();
            System.out.println(prefix+temp.getValue());
            int n=temp.neighbors.size();
            for (int i=0;i<n;i++){
                Node p=temp.neighbors.get(i);
                list.add(p);
            }
        }
    }

    private void startNode(Node start){
        Random random=new Random();
        LinkedList<Node> list=new LinkedList<Node>();
        list.add(start);
        int count=0;
        while (!list.isEmpty()){
            Node p=list.pop();
            int n=random.nextInt(3);
            for (int i=0;i<n;i++){
                Node temp=new Node();
                p.neighbors.add(temp);
                list.add(temp);
                count++;
            }
            if(count>10){
                return;
            }
        }
    }

    private Node copyGraph(Node start){
        if(start==null) return null;

        Map<Node,Node> map=new HashMap<Node, Node>();
        Node node=new Node();
        map.put(start,node);
        LinkedList<Node> linkedList=new LinkedList<Node>();
        linkedList.add(start);
        while(!linkedList.isEmpty()){
            Node temp=linkedList.pop();
            int n=temp.neighbors.size();
            Node tt=map.get(temp);
            for(int i=0;i<n;i++){
                Node neighbor=temp.neighbors.get(i);
                if(!map.containsKey(neighbor)){
                    Node p=new Node();
                    tt.neighbors.add(p);
                    map.put(neighbor,p);
                }else{
                    Node p=map.get(neighbor);
                    tt.neighbors.add(p);
                }
            }
        }

        return node;
    }
}
