import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Dijkstra {
    public static void main(String[] args) {
        QReader in=new QReader();
        QWriter out=new QWriter();
        int n=in.nextInt();
        int m=in.nextInt();
        Vertex[]heap=new Vertex[n+1];
        Vertex[]record=new Vertex[n+1];
        //创建小顶堆
        Vertex root=new Vertex(1);
        root.distance=0;
        heap[1]=root;
        record[1]=root;
        for (int i = 2; i <n+1; i++) {
            Vertex tmp=new Vertex(i);
            heap[i]=tmp;
            record[i]=tmp;
        }
        for (int i = 0; i <m; i++) {
            int u=in.nextInt();
            int v=in.nextInt();
            Long cost=in.nextLong();
            heap[u].adj.add(heap[v]);
            heap[u].cost.add(cost);
        }
        int num=n;
        while(num>=1){
            Vertex thisVertex=heap[1];
            delete_min(num,heap);num--;
            thisVertex.flag=true;
            for (int i = 0; i <thisVertex.adj.size(); i++) {
                if (!thisVertex.adj.get(i).flag){
                    if (thisVertex.adj.get(i).distance>thisVertex.distance+thisVertex.cost.get(i)){
                        thisVertex.adj.get(i).distance=thisVertex.distance+thisVertex.cost.get(i);
                        update(thisVertex.adj.get(i).index,num,heap);
                    }
                }
            }
        }
        if (record[n].distance==Long.MAX_VALUE){
            out.print(-1);
        }
        else{
            out.print(record[n].distance);
        }
        out.close();
    }
    private static class Vertex {
        long distance;
        int index;
        List<Vertex>adj;
        List<Long>cost;
        boolean flag;
        public Vertex(int index){
            this.index=index;
            adj=new ArrayList<>();
            distance=Long.MAX_VALUE;
            cost=new ArrayList<>();
        }
    }

    public static void delete_min(int size,Vertex[]heap){
        heap[1]=heap[size];
        heap[1].index=1;
        heap[size]=null;
        size--;
        int index=1;
        down(1,size,heap);
    }
    public static void swap(int index1,int index2,Vertex[]heap){
        Vertex tmp=heap[index1];
        heap[index1]=heap[index2];
        heap[index2]=tmp;
        tmp.index=index2;
        heap[index1].index=index1;
    }
    public static void down(int index,int size,Vertex[]heap){
        while (index*2<=size){
            if (index*2+1<=size){
                if (heap[index].distance<=heap[index*2].distance&&heap[index].distance<=heap[index*2+1].distance){
                    break;
                }
                else if (heap[index*2].distance<=heap[index*2+1].distance){
                    swap(index,index*2,heap);
                    index=index*2;
                }
                else {
                    swap(index,index*2+1,heap);
                    index=index*2+1;
                }
            }
            else {
                if (heap[index].distance<=heap[index*2].distance){
                    break;
                }
                else {
                    swap(index,index*2,heap);
                    index=index*2;
                }
            }
        }
    }
    public static void up(int index,Vertex[]heap) {
        while (index>1){
            if (heap[index].distance<heap[index/2].distance){
                swap(index,index/2,heap);
                index=index/2;
            }
            else break;
        }
    }
    public static void update(int index,int size,Vertex[]heap){
        down(index,size,heap);
        up(index,heap);
    }
}


class QReader {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private StringTokenizer tokenizer = new StringTokenizer("");

    private String innerNextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean hasNext() {
        while (!tokenizer.hasMoreTokens()) {
            String nextLine = innerNextLine();
            if (nextLine == null) {
                return false;
            }
            tokenizer = new StringTokenizer(nextLine);
        }
        return true;
    }

    public String nextLine() {
        tokenizer = new StringTokenizer("");
        return innerNextLine();
    }

    public String next() {
        hasNext();
        return tokenizer.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }
}

class QWriter implements Closeable {
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(Object object) {
        try {
            writer.write(object.toString());
        } catch (IOException e) {
            return;
        }
    }

    public void println(Object object) {
        try {
            writer.write(object.toString());
            writer.write("\n");
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            return;
        }
    }
}
