import java.util.ArrayList;
import java.util.List;

public class B_Prime {
    public static void main(String[] args) {
        QReader in=new QReader();
        QWriter out=new QWriter();
        int n=in.nextInt();
        int m=in.nextInt();
        int index=0;
        Vertex[]vertices=new Vertex[n+1];
        Edge[]edges=new Edge[m];
        long sum = 0;
        for (int i = 1; i <=n; i++) {
            Vertex tmp=new Vertex();
            vertices[i]=tmp;
        }
        for (int i = 0; i <m; i++) {
            int index1=in.nextInt();
            int index2=in.nextInt();
            int weight=in.nextInt();
            Edge edge=new Edge(vertices[index1],vertices[index2],weight);
            edges[i]=edge;
            vertices[index1].edges.add(edge);
            vertices[index2].edges.add(edge);
        }
        int num=0;
        Edge[]heap=new Edge[m+1];
        //let first vertex's edges enqueue
        vertices[1].marked=true;
        for (int i = 0; i <vertices[1].edges.size(); i++) {
            insert(num,vertices[1].edges.get(i),heap);
            num++;
        }
        while(num>0){
            while (heap[1]!=null&&heap[1].vertex1.marked && heap[1].vertex2.marked){
                delete_min(num,heap);num--;
            }

            if (num>0) {
                Edge thisEdge=heap[1];
                thisEdge.chosen=true;
                delete_min(num, heap);
                num--;
                Vertex nextVertex;
                if (thisEdge.vertex1.marked) {
                    nextVertex = thisEdge.vertex2;
                } else {
                    nextVertex = thisEdge.vertex1;
                }
                nextVertex.marked = true;
                for (int i = 0; i < nextVertex.edges.size(); i++) {
                    if (!(nextVertex.edges.get(i).vertex1.marked && nextVertex.edges.get(i).vertex2.marked)) {
                        insert(num, nextVertex.edges.get(i), heap);
                        num++;
                    }
                }
            }else {
                break;
            }
        }
        for (int i = 0; i <m; i++) {
            if (!edges[i].chosen&&edges[i].weight>0){
                sum+=edges[i].weight;
            }
        }
        out.print(sum);

        out.close();
    }
    private static class Vertex {
        List<Edge>edges;
        boolean marked;
        public Vertex(){
            edges=new ArrayList<>();
        }
    }
    private static class Edge{
        Vertex vertex1;
        Vertex vertex2;
        int weight;
        boolean chosen;
        public Edge(Vertex vertex1,Vertex vertex2,int weight){
            this.vertex1=vertex1;
            this.vertex2=vertex2;
            this.weight=weight;
        }
    }
    public static void insert(int size,Edge edge,Edge[]heap){
        size++;
        heap[size]=edge;
        int index=size;
        while (index/2>=1){
            if (heap[index/2].weight>heap[index].weight){
                swap(index/2,index,heap);
                index=index/2;
            }else {
                break;
            }
        }
    }

    public static void delete_min(int size, Edge[]heap){
        heap[1]=heap[size];
        heap[size]=null;
        size--;
        down(1,size,heap);
    }
    public static void swap(int index1, int index2, Edge[]heap){
        Edge tmp=heap[index1];
        heap[index1]=heap[index2];
        heap[index2]=tmp;
    }
    public static void down(int index, int size, Edge[]heap){
        while (index*2<=size){
            if (index*2+1<=size){
                if (heap[index].weight <=heap[index*2].weight &&heap[index].weight <=heap[index*2+1].weight){
                    break;
                }
                else if (heap[index*2].weight <=heap[index*2+1].weight){
                    swap(index,index*2,heap);
                    index=index*2;
                }
                else {
                    swap(index,index*2+1,heap);
                    index=index*2+1;
                }
            }
            else {
                if (heap[index].weight <=heap[index*2].weight){
                    break;
                }
                else {
                    swap(index,index*2,heap);
                    index=index*2;
                }
            }
        }
    }
    public static void up(int index, Edge[]heap) {
        while (index>1){
            if (heap[index].weight <heap[index/2].weight){
                swap(index,index/2,heap);
                index=index/2;
            }
            else break;
        }
    }
    public static void update(int index, int size, Edge[]heap){
        down(index,size,heap);
        up(index,heap);
    }
}
//8 13
//        1 2 1
//        2 3 3
//        1 3 3
//        2 4 13
//        2 6 10
//        3 5 5
//        3 8 6
//        1 8 8
//        4 8 9
//        1 5 7
//        5 6 2
//        6 7 12
//        4 7 11