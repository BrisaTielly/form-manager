import entities.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
        List<User> user =  new ArrayList<>();;

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

        user.add( new User(answersFields[0], answersFields[1], Integer.parseInt(answersFields[2]), Double.parseDouble(answersFields[3])));
        System.out.println(user);
        String finalPath = ".\\" + "1- " + user.get(0).getName().toUpperCase().replace(" ", "") + ".txt";

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(finalPath))){
            bw.write(user.get(0).getName() + "\n");
            bw.write(user.get(0).getEmail() + "\n");
            bw.write(user.get(0).getAge() + "\n");
            bw.write(user.get(0).getHeight() + "\n");
        }catch(IOException e){
            System.out.println("Error " + e.getMessage());
        }

        sc.close();
    }
}
