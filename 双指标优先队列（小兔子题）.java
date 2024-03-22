import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        QReader in=new QReader();
        QWriter out=new QWriter();
        int n=in.nextInt();
        int num=n;
        node head=new node(-1,-1);
        node pointer=head;
        node[]heap=new node[n+1];
        for (int i = 1; i <n+1; i++) {
            node tmp=new node(in.nextInt(),i);
            heap[i]=tmp;
            pointer.next=tmp;
            tmp.pre=pointer;
            pointer=pointer.next;
        }
        node tail=new node(-1,-1);
        pointer.next=tail;
        tail.pre=pointer;
        //生成小顶堆
        int indexRoot=n/2;
        while(indexRoot>0){
            int index=indexRoot;
            while(index*2<=n) {
                //右边存在的情况
                if (index*2+1<=n) {
                    if (heap[index * 2].value < heap[index * 2 + 1].value) {
                        if (heap[index * 2].value < heap[index].value || (heap[index * 2].value == heap[index].value && heap[index * 2].rank < heap[index].rank)) {
                            swap(index, index * 2, heap);
                            index = index * 2;
                        } else {
                            break;
                        }
                    } else if (heap[index * 2].value > heap[index * 2 + 1].value) {
                        if (heap[index * 2 + 1].value < heap[index].value || (heap[index * 2 + 1].value == heap[index].value && heap[index * 2 + 1].rank < heap[index].rank)) {
                            swap(index, index * 2 + 1, heap);
                            index = index * 2 + 1;
                        }
                        else break;
                    } else {
                        if (heap[index * 2].value < heap[index].value) {
                            if (heap[index * 2].rank < heap[index * 2 + 1].rank) {
                                swap(index, index * 2, heap);
                                index = index * 2;
                            } else {
                                swap(index, index * 2 + 1, heap);
                                index = index * 2 + 1;
                            }
                        } else if (heap[index * 2].value == heap[index].value) {
                            if (heap[index * 2].rank < heap[index * 2 + 1].rank && heap[index * 2].rank < heap[index].rank) {
                                swap(index, index * 2, heap);
                                index = index * 2;
                            } else if (heap[index * 2].rank > heap[index * 2 + 1].rank && heap[index * 2 + 1].rank < heap[index].rank) {
                                swap(index, index * 2 + 1, heap);
                                index = index * 2 + 1;
                            } else {
                                break;
                            }
                        }
                    }
                }
                //右边不存在的情况
                else {
                    if (heap[index*2].value<heap[index].value||(heap[index*2].value==heap[index].value&&heap[index*2].rank<heap[index].rank)){
                        swap(index,index*2,heap);
                        index=index*2;
                    }
                    else break;
                }
            }
            indexRoot--;
        }


        for (int i = 0; i <n-1; i++) {
           /* //选出最小值;
            int num2=num;//如果全部一样，最多进行num次操作
            node minRank=null;
            int first=0;
            for (int j = 0; j <num2; j++) {
                if (heap[1].flag){
                    delete_min(num,heap);
                    num--;
                }
                else {
                    if (first==0){
                        minRank=heap[1];
                        delete_min(num,heap);num--;
                        first++;
                    }
                    else{
                        if (heap[1].value!=minRank.value){
                            break;
                        }
                        else{
                            if (heap[1].rank<minRank.rank){
                                insert(minRank,num,heap);num++;
                                minRank=heap[1];
                                delete_min(num,heap);num--;
                            }
                            else{
                                node tmp=heap[1];
                                delete_min(num,heap);num--;
                                insert(tmp,num,heap);num++;
                            }
                        }
                    }
                }
            }
            */
            node minRank=null;
            while(heap[1]!=null) {
                if (heap[1].flag) {
                    delete_min(num, heap);
                    num--;
                }
                else {
                    minRank=heap[1];
                    delete_min(num, heap);
                    num--;
                    break;
                }
            }
            if (minRank.pre.rank!=-1&&minRank.next.rank!=-1) {
                if ((minRank.value ^ minRank.pre.value) + 1 >= (minRank.value ^  minRank.next.value) + 1) {
                    minRank.pre.flag = true;
                    minRank.value = (minRank.value ^ minRank.pre.value) + 1;
                    delete_nodeList(minRank.pre);
                } else {
                    minRank.next.flag = true;
                    minRank.value = (minRank.value ^ minRank.next.value) + 1;
                    delete_nodeList(minRank.next);
                }
            } else if (minRank.pre.rank==-1) {
                minRank.next.flag = true;
                minRank.value = (minRank.value ^ minRank.next.value) + 1;
                delete_nodeList(minRank.next);
            } else {
                minRank.pre.flag = true;
                minRank.value = (minRank.value ^ minRank.pre.value) + 1;
                delete_nodeList(minRank.pre);
            }
           insert(minRank,num,heap);num++;
        }
        for (int i = 1; i <n+1; i++) {
            if (heap[i]==null){
                break;
            }
            else {
               if (!heap[i].flag){
                   out.print(heap[i].value);
               }
            }
        }
        out.close();
    }
    public static void delete_min(int num,node[]heap) {
        swap(1, num, heap);
        heap[num]=null;
        num--;
        int index = 1;
        while(index*2<=num) {
            //右边存在的情况
            if (index*2+1<=num) {
                if (heap[index * 2].value < heap[index * 2 + 1].value) {
                    if (heap[index * 2].value < heap[index].value || (heap[index * 2].value == heap[index].value && heap[index * 2].rank < heap[index].rank)) {
                        swap(index, index * 2, heap);
                        index = index * 2;
                    } else {
                        break;
                    }
                } else if (heap[index * 2].value > heap[index * 2 + 1].value) {
                    if (heap[index * 2 + 1].value < heap[index].value || (heap[index * 2 + 1].value == heap[index].value && heap[index * 2 + 1].rank < heap[index].rank)) {
                        swap(index, index * 2 + 1, heap);
                        index = index * 2 + 1;
                    }
                    else break;
                } else {
                    if (heap[index * 2].value < heap[index].value) {
                        if (heap[index * 2].rank < heap[index * 2 + 1].rank) {
                            swap(index, index * 2, heap);
                            index = index * 2;
                        } else {
                            swap(index, index * 2 + 1, heap);
                            index = index * 2 + 1;
                        }
                    } else if (heap[index * 2].value == heap[index].value) {
                        if (heap[index * 2].rank < heap[index * 2 + 1].rank && heap[index * 2].rank < heap[index].rank) {
                            swap(index, index * 2, heap);
                            index = index * 2;
                        } else if (heap[index * 2].rank > heap[index * 2 + 1].rank && heap[index * 2 + 1].rank < heap[index].rank) {
                            swap(index, index * 2 + 1, heap);
                            index = index * 2 + 1;
                        } else {
                            break;
                        }
                    }
                }
            }
            //右边不存在的情况
            else {
                if (heap[index*2].value<heap[index].value||(heap[index*2].value==heap[index].value&&heap[index*2].rank<heap[index].rank)){
                    swap(index,index*2,heap);
                    index=index*2;
                }
                else break;
            }
        }
    }
    public static  void insert(node in_node,int num,node[]heap){//没问题
        num++;
        heap[num]=in_node;
        int index=num;
        while(index>1&&heap[index/2].value>=heap[index].value){
            if (heap[index/2].value>heap[index].value) {
                swap(index, index / 2, heap);
                index = index / 2;
            }
            else{
                if (heap[index/2].rank<heap[index].rank){
                    swap(index, index / 2, heap);
                    index = index / 2;
                }
                else break;
            }
        }
    }
    public static void swap(int index1,int index2,node[]heap){
        node tmp=heap[index1];
        heap[index1]=heap[index2];
        heap[index2]=tmp;
    }
    public static void delete_nodeList(node tmp){
        tmp.pre.next=tmp.next;
        tmp.next.pre=tmp.pre;
    }


    private static class node{
        int value;
        node next;
        node pre;
        int rank;
        boolean flag;
        public node(int value,int rank){
            this.value=value;
            this.rank=rank;
        }
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
