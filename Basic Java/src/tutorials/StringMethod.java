package tutorials;

public class StringMethod {

	public static void main(String[] args) {
		String[] words = {"chunk", "funk", "hunk", "fish" };
		
		for(String w : words){
			if(w.startsWith("fu"))
				System.out.println(w + " starts with fu");
		}
		for(String w : words){
			if(w.endsWith("unk"))
				System.out.println(w + " ends with fu");
		}
	}

}
