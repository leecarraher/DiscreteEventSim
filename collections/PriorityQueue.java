package collections;


public class PriorityQueue<E extends Comparable> {
	private transient E[] data;
	int tail = 0;
	
	public PriorityQueue(){
		this.data = (E[])new Comparable[11];
		data[0]=null;
	}

	public PriorityQueue(int size){
        //boo java's ArrayList is allowed to do this w/o warning
		this.data = (E[])new Comparable[size];
		data[0]=null;
	}
	
	private void resize(){
		//possible change resizing methods
		E[] temp = (E[])new Comparable[data.length*2];
		System.arraycopy(data, 0, temp, 0, data.length);
		data = temp;
	}
	
	private int getParent(int childIndex){
		return (childIndex+1)/2;
	}
	
	private int getRight(int parentIndex){
		
		if(parentIndex*2>tail)
		{
			return -1;
		}
		
		return  parentIndex*2+1;
	}
	
	private int getLeft(int parentIndex){
		if(parentIndex*2 >tail)
		{
			return -1;
		}
		
		return parentIndex*2;
	}
	
	private int getLeastChild(int parentIndex)
	{
		if(getLeft(parentIndex)==-1)return -1;
		if(getRight(parentIndex)==-1)return getLeft(parentIndex);
		if(data[getLeft(parentIndex)].compareTo(data[getRight(parentIndex)])>0)return getRight(parentIndex);
		return getLeft(parentIndex);
	}
	
	public void add(E c)
	{
		if(++tail > data.length-1)resize();
		int walker = tail;
		data[tail] = c;
		
		// this is messed up, but works with an offset of 1
		while(data[getParent(walker)].compareTo(data[walker])>0)
		{
			swap(walker,getParent(walker));
			walker = getParent(walker);
		}
	}
	
	public boolean isEmpty()
	{
		return tail < 1;
	}
	
	public E remove()
	{
		data[0]= data[1];//stash it	
		data[1] = data[tail];
		tail--;
		reheapify(1);
		return data[0];
	}
	
	public E peek()
	{
		return data[1];
	}
	
	private void reheapify(int parentIndex)
	{
		int least = getLeastChild(parentIndex);
		if(least == -1)return;
		
		//System.out.println(data[least] + "\n\t" + data[parentIndex*2] + "\n\t" + data[parentIndex*2+1]);
		
		if(data[least].compareTo(data[parentIndex])<1){
			swap(parentIndex,least);
			reheapify(least);
		}
	}
	
	private void swap(int item1, int item2){
		E temp = data[item1];
		data[item1] = data[item2];
		data[item2] = temp;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PriorityQueue<String> h = new PriorityQueue<String>(100);
		for(String s:args){
			h.add(s);
		}

		while(!h.isEmpty()){
			System.out.println(h.peek());
		}
	}

}
