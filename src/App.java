import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        List<String> questions = new ArrayList<>();
        String path = ".\\formulario.txt";
        
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();
            while(line != null){
            System.out.println(line);
            questions.add(line);
            line = br.readLine();
            }
            
        }catch(IOException e){
            System.out.println("Error " + e.getMessage());
        }
        sc.close();
    }
}
