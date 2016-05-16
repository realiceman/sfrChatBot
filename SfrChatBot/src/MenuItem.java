import java.util.ArrayList;


public class MenuItem {
	
	
	private String value;
	
	
	 public MenuItem() {
		
	}
	 
	 
	 private ArrayList<Keyword> keyword = new ArrayList<Keyword>();


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public ArrayList<Keyword> getKeyword() {
		return keyword;
	}


	public void setKeyword(ArrayList<Keyword> keyword) {
		this.keyword = keyword;
	}
     
	 
	public void addKeyword(Keyword k){
		keyword.add(k);
	}
	
	
	@Override
	public String toString() {
		
		return this.value + "[" + this.keyword +"]";
	}


	public void removeKeyword(Keyword k){
		keyword.remove(k);
	}
	
	
	public int  sizeList(){
		int longueur = keyword.size();
		
		return longueur;
	}
	
}
