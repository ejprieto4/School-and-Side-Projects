//Edgar Prieto UCI ID:39185940

public class HashTable {
    
    private int InitialSize = 256;
    
    private Node[] HT;
    
    private int HT_content;

    public int size() {//for Test.java
        return HT_content;
    }
    
    public HashTable() {
	//the hashtable, an array of singly linked lists
        HT = new Node[InitialSize];
    }
    
    public int MakeHash(Object key, int arraysize){
	//pass in arraysize to ensure hash value is not out of index
	//as well as ensure all indexes are available
	//n % 2^i = n & (2^i - 1)
        return Math.abs(key.hashCode()*(key.hashCode())*31)%arraysize;
    }

    public synchronized Object get(Object key) {
        int H = MakeHash(key,HT.length);
        Node tmp = HT[H];//to traverse linked list at index i
        while (tmp != null) {
                if (tmp.key.equals(key)){
            return tmp.val; // key was found
            }else{
            tmp = tmp.nxt;
            }
        }
        return null;  //key was not found
    }

    public synchronized void put(Object key, Object val) {
        int H = MakeHash(key,HT.length);
        Node tmp;
        Node newN;
        tmp = HT[H];
        while (tmp!=null) {
            if (tmp.key.equals(key)){// if key was found exit while
                break;
            }else{
                tmp = tmp.nxt;
            }
        }
        
        if (tmp != null) {
            // Just change the value.
            // since we got the same key
            tmp.val = val;
        } else {
            newN = new Node();
            newN.val = val;
            newN.key = key;
            newN.nxt = HT[H];
            HT[H] = newN;
            HT_content++;

            if (HT_content >= (0.85*HT.length)) {
                HashResize();
            }

        }
    }
    
    public void HashResize() {
        int H;
        Node tmp;
        Node next;
        Node[] newHT = new Node[HT.length*2];
        for (int i = 0; i < HT.length; i++) {
            tmp = HT[i];
            while (tmp != null) {
                H = MakeHash(tmp.key,newHT.length);
                next = tmp.nxt;
                tmp.nxt = newHT[H];
                newHT[H] = tmp;
                tmp = next;
            }
        }
        HT = newHT;
    }
}

class Node {
    private Object key;
    private Object val;
    private Node nxt;

    public Object getKey(){
        return key;
    }

    public Object getVal(){
        return val;
    }

    public Object getNxt(){
        return nxt;
    }
    
    public void setKey(Object k){
        this.key = k;
    }

    public void setVal(Object v){
        this.val = v;
    }
    
    public void setNxt(Node n){
        this.nxt = n;
    }
}
