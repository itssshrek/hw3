import java.util.ArrayList;
/**
 * This class is an Implementation of HashTable.
 * Constructs a hashTable associated with a pairs of key and value
 * the collision resolution scheme I used for this hash table is #4 CHIAN OF BUCKET : ARRAY OF ARRAY
 * 
 * lec 001
 * Email: qwu227@wisc.edu
 * @author wuqiye
 * Date March 14
 *
 * @param <K> key
 * @param <V> value
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
	/**
	 * inner class
	 * @author wuqiye
	 *
	 */
	private class Pair<K2,V2>{
		// initialize filed
		private K2 key;
		private V2 value;
		/**
		 * constructor that initializes the inner fields
		 * @param key current key 
		 * @param value current value 
		 */
		private Pair(K2 key, V2 value) {
			this.key=key;
			this.value=value;
			
		}
		/**
		 * getter
		 * @return key of the current pair
		 */
		private K2 getkey() {
			return key;
		}
		/**
		 * getter
		 * @return current value
		 */
		private V2 getValue(){
			return value;
		}
	}
	// fields for outer class
	
	private int count;//number of pairs in the table
	private int size;//current capacity of table
	private double loadFactor;//load factor of hash table , 
	private double threshold;
	private ArrayList<ArrayList<Pair<K,V>>> table = new ArrayList<ArrayList<Pair<K,V>>>();//using array to array bucket
	
	/**
	 *  constructor with no parameter 
	 */
	public HashTable() {
		this(11,0.75);	
	}
	
	/**
	 * Constructor with two parameters
	 * initialize field value
	 * @param initialCapacity 
	 * @param loadFactorThreshold a threshold is loadfactor that casues a resize and rehash
	 */
	public HashTable(int initialCapacity, double loadFactorThreshold) {
		this.threshold=loadFactorThreshold;
		this.count=0;
		this.size=initialCapacity;
		this.loadFactor=(double)count/size;
		for(int i=0;i<size;i++)
			table.add(new ArrayList<Pair<K,V>>());//adding empty arraylist to each index position of the hashtable
	}
	@Override
	/**
	 * insert pair with specific key and value into hashTable. 
	 */
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException{
		
		if(key==null)
			throw new IllegalNullKeyException();// throw exception if key is null
		
		int hashing = Math.abs(key.hashCode())%size; // calculate the index by hashing function 
		ArrayList<Pair<K,V>> table2 = table.get(hashing);// find the hashingIndex of insert key 
		for(int i =0; i<table2.size();i++) {
			if(key.equals(table2.get(i).key))// duplicated key is not allowed here 
				throw new DuplicateKeyException();
		}
		 Pair<K,V> newPair = new Pair<K,V>(key,value);// create a new empty hashing pair to hashtable
		 table.get(hashing).add(newPair);// adding the newpair to hashing index that is calculated 
		 count++;// increase the capacity of hashtable
		 loadFactor=(double) count/size; 
		 if(loadFactor>=threshold)
			resize();// resize and hash if the loadFactor threshold is reached 
	}
	/**
	 * helper method
	 * create a new hashTable with bigger capacity 
	 */
	private void resize() {
		size=2*size+1;// expand the size of hashing table to 2 times of itself plus 1
		ArrayList<ArrayList<Pair<K,V>>> temp = new ArrayList<ArrayList<Pair<K,V>>>();
		for(int i =0; i<size;i++) {
			temp.add(new ArrayList<Pair<K,V>>());// adding arraylist to each arraylist by index 
		}
		for(int i=0;i<table.size();i++) {//searching through each bucket of the table 
			for(int j=0;j<table.get(i).size();j++) {
				Pair<K,V> newpair = table.get(i).get(j);
				int hashing2 = Math.abs(newpair.getkey().hashCode())%size;//find the new hashing index                                
					temp.get(hashing2).add(newpair);//insert new pair to the hashing index 
			}
		}
		table= temp;
	}
	@Override
	/**
	 * remove the pair with specific key 
	 * @param key key to be removed
	 */
	public boolean remove(K key) throws IllegalNullKeyException{
		
		if(key==null)
			throw new IllegalNullKeyException();
		
		int index = Math.abs(key.hashCode())%size;// calculate the hashing index by hashing function 
		
		for(int i=0;i<table.get(index).size();i++)
			if(table.get(index).get(i).getkey().equals(key)) {// key is found
				table.get(index).remove(i);// remove the key 
				count--; 
				return true;
			}
		return false;//key is not found 
}
	@Override
	/**
	 * @param key key to be found 
	 * return the value with specific key 
	 * throw illegalNullkeyException if key is null
	 * throw keyNotfoundException if key is not found 
	 */
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException{
		if(key==null)
			throw new IllegalNullKeyException();
		int index = Math.abs(key.hashCode())%size;
		for(int i=0;i<table.get(index).size();i++)
			if(table.get(index).get(i).getkey().equals(key)) {// key is found
				return table.get(index).get(i).getValue();//return the value of the associated key 
			}
		throw new KeyNotFoundException();
	}
	/**
	 * number of pairs in hashTable
	 */
	 public int numKeys() {
		 return count;
		 
	 }
	@Override
	/**
	 * getter to the current loadfactor threshold of hashtable 
	 * O(1)
	 */
    public double getLoadFactorThreshold() {
		return threshold;
    }
	@Override
	/**
	 * getter to get the current loadfactor of hash table
	 * O(1)
	 */
    public double getLoadFactor() {
		loadFactor = (double)count/size;
		return loadFactor;
	}

   
	@Override
	/**
	 * getter to get the capacity of current hashtable
	 * O(1)
	 */
    public int getCapacity() {
    	
		return this.size;
    }
	@Override
	/**
	 * get the number of collision resolution scheme 
	 * O(1)
	 */
    public int getCollisionResolution() {
		return 4; 
    	
    }
		
}
