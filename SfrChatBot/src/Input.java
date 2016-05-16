
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Input {

	
	public static String  input(String prompt){
		BufferedReader promptin=new BufferedReader(new InputStreamReader(System.in));
		System.out.print(prompt);
		System.out.flush();
		
		
		try {
			return promptin.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "error: " + e.getMessage();
		}
		
		
	}
}
