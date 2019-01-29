package ppdm.core.data;

/*
Purpose	: Implement a list and its node
Author	: Andre Ricardo Herianto
Date		: 16 March 2007
*/

class List {
	
	//instance variables
	private Node head,tail;
	private int numItems;
	
	public List() {
		head=null;
		tail=null;
		numItems=0;
	}
	
	//check whether the list is empty
	public boolean isEmpty() {
		return numItems==0;
	}
	
	//accessors method
	public int length() {return numItems;}
	
	public Node getHead() {return head;}
		
	public Object getObject (int index) {
		Node temp=getNode(index);
		if(temp==null) return null;
		else return temp.getItem();
	}
	
	private Node getNode(int index) {
		if(index<1 || index>numItems) return null;
		else {
			Node temp=head;
			for(int a=1; a<index; a++) {
				temp=temp.getNext();
			}
			return temp;
		}
	}
	
	//get the index of a particular item in the list
	public int getIndex(int partyId) {
		Node temp=head;
		int index=1;
		while(temp!=null) {
			if(Integer.parseInt((String)temp.getItem())==partyId) return index;
			temp=temp.getNext();
			index++;
		}
		return -1;
	}
	
	//method to insert a node
	public void insert(int at, Object o) {
		if(isEmpty()) head=tail=new Node(o);
		else if(at<=1) {
			head=new Node(o,head);
		}
		else if (at>numItems) {
			tail.setNext(new Node(o));
			tail=tail.getNext();
		}
		else {
			Node temp=getNode(at-1);
			temp.setNext(new Node(o,temp.getNext()));
		}
		numItems++;
	}
	
	public void insertLast(Object o) {
		if(isEmpty()) head=tail=new Node(o);
		else {
			tail.setNext(new Node(o));
			tail=tail.getNext();
		}
		numItems++;
	}
	
	//method to delete a node
	public boolean delete(int index) {
		if(index==1) {
			head=head.getNext();
			numItems--;
			return true;
		}
		if(index<1 || index>numItems) return false;
		else {
			Node temp=getNode(index-1);
			temp.setNext(temp.getNext().getNext());
			numItems--;
			return true;
		}
	}
	
	public boolean deleteLast() {
		if(numItems==0) return false;
		if(numItems==1) {
			head=tail=null;
			numItems--;
			return true;
		}
		Node temp=head;
		while(temp.getNext()!=tail) {
			temp=temp.getNext();
		}
		tail=temp;
		tail.setNext(null);
		numItems--;
		return true;
	}
	
	//mutator method
	public boolean setObject (int index, Object o) {
		if(index<1 || index>numItems) return false;
		else {
			Node temp=getNode(index);
			temp.setItem(o);
			return true;
		}
	}
	
	//overrides toString method
	public String toString() {
	
		String output="";
		Node temp=head;
		for(int a=0; a<numItems; a++) {
			output+=temp.getItem().toString();
			//output+="\n";
			output+=" ";
			temp=temp.getNext();
		}
		return output;
	}	
	
	//check wheter particular integer item is in list
	public boolean isInList(int partyId) {
		Node temp=head;
		while(temp!=null) {
			if(Integer.parseInt((String)temp.getItem())==partyId) return true;
			temp=temp.getNext();
		}
		return false;
	}
	
	public double[] toDoubleArray() {
		double [] result= new double[numItems];
		Node temp=head;
		for(int i=0; i<result.length; i++) {
			result[i]=Double.parseDouble((String)temp.getItem());
			temp=temp.getNext();
		}
		return result;
	}
	
	public int[][] toTwoIntegerArray() {
		if(numItems==0) return new int[0][0];
		int[][] result = new int[numItems][((List)head.getItem()).length()];
		for(int i=0; i<result.length; i++) {
			for(int j=0; j<result[i].length; j++) {
				result[i][j]=((Integer)((List)getObject(i+1)).getObject(j+1)).intValue();
			}
		}
		return result;
	}
	
	public int[] toIntegerArray() {
		int[] result = new int [numItems];
		for(int i=0; i<result.length; i++) {
			result[i]=((Integer)getObject(i+1)).intValue();
		}
		return result;
	}
}

class Node {
	//instance variables
	private Object item;
	private Node next;
	
	//constructors
	public Node(Object o) {
		item=o;
	}
	public Node(Object o, Node newNode) {
		item=o;
		next=newNode;
	}
	
	//accessors method
	public Object getItem() {return item;}
	public Node getNext() {return next;}
	
	//mutator method
	public void setItem(Object o) {item=o;}
	public void setNext(Node newNode) {next=newNode;}
		
}

