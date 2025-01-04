import entities.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<String> staticQuestions = new ArrayList<>();
        String path = ".\\formulario.txt";
        
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();
            while(line != null){
            staticQuestions.add(line);
            line = br.readLine();
            }
            
        }catch(IOException e){
            System.out.println("Error " + e.getMessage());
        }

        String[] answersFields = new String[4];
        int i = 0;
        for(String question : staticQuestions){
            System.out.println(question);
            answersFields[i++] = sc.nextLine();
        }
        User user = new User(answersFields[0], answersFields[1], Integer.parseInt(answersFields[2]), Double.parseDouble(answersFields[3]));
        System.out.println(user);
        sc.close();
    }
}
