import entities.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<String> staticQuestions = new ArrayList<>();
        List<User> user =  new ArrayList<>();;
        Map<Integer, String> finalPaths = new TreeMap<>();

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

        int select = 0;
        while (select != 6) { 
            System.out.println("1 - Cadastrar o usuário");
            System.out.println("2 - Listar todos os usuáros cadastrados");
            System.out.println("3 - Cadastrar nova pergunta no formulário");
            System.out.println("4 - Deletar pergunta no formulário");
            System.out.println("5 - Pesquisar usuário por nome, idade ou email");
            System.out.println("6 - Encerrar");
            select = sc.nextInt();
            
            switch (select) {
                case 1:
                    //Chamar a funcao pra cadastrar
                    registerUsers(user, staticQuestions, sc, finalPaths);
                    break;
                case 2:
                    listUsers(user);
                    break;    
                default:
                    throw new AssertionError();
            }
        }

        sc.close();
    }

    public static void listUsers(List<User> user) {
        for(User x: user){
            System.out.println(x.getName());
        }
    }

    public static void registerUsers(List<User> user, List<String> staticQuestions, Scanner sc,  Map<Integer, String> finalPaths){
        String[] answersFields = new String[4];
        int i = 0;
        sc.nextLine();
        for(String question : staticQuestions){
            System.out.println(question);
            answersFields[i++] = sc.nextLine();
        }
        user.add( new User(answersFields[0], answersFields[1], answersFields[2], answersFields[3]));
        System.out.println(user);
        createUserFile(user, finalPaths);
        }  


        public static void createUserFile(List<User> user,  Map<Integer, String> finalPaths) {
            int i = 1;
            while(finalPaths.containsKey(i)){
                i++;
            }
            String finalPath = ".\\" + i + "- " + user.get(i-1).getName().toUpperCase().replace(" ", "") + ".txt";
            finalPaths.put(i, finalPath);
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(finalPath))){
                bw.write(user.get(i-1).getName() + "\n");
                bw.write(user.get(i-1).getEmail() + "\n");
                bw.write(user.get(i-1).getAge() + "\n");
                bw.write(user.get(i-1).getHeight() + "\n");
            }catch(IOException e){
                System.out.println("Error " + e.getMessage());
            }
        }
        
}
