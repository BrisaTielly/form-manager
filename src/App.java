import entities.Questions;
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
        List<Questions> staticQuestions = new ArrayList<>();
        List<User> user =  new ArrayList<>();;
        Map<Integer, String> finalPaths = new TreeMap<>();

        String path = ".\\formulario.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            int i = 1;
            while (line != null) {
                String questionText = line.split("-")[1].trim();  // Remove a numeração e espaços
                staticQuestions.add(new Questions(questionText, i++));
                line = br.readLine();
            }
        } catch (IOException e) {
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
                case 1 -> registerUsers(user, staticQuestions, sc, finalPaths);
                case 2 -> listUsers(user);
                case 3 -> registerNewQuestion(sc, staticQuestions, path);
                case 4 -> deleteQuestions(sc, staticQuestions, path);
                case 5 -> searchUser(user, sc);
                default -> throw new AssertionError();
            }
        }

        sc.close();
    }


    public static void listUsers(List<User> user) {
        int i = 1;
        for(User x: user){
            System.out.println(i + "- " + x.getName());
            i++;
        }
    }

    public static void registerUsers(List<User> user, List<Questions> staticQuestions, Scanner sc,  Map<Integer, String> finalPaths){
        ArrayList<String> answersFields = new ArrayList<>();
        sc.nextLine();
        for(Questions question : staticQuestions){
            if(question.getId() > 4) 
                System.out.println(question.getId() + "- " + question.getQuestion());
            else 
                System.out.println(question.getQuestion());
           // sc.nextLine();
            answersFields.add(sc.nextLine()); //Vai ter um indice aqui que corresponde ao getId da pergunta
        }
        user.add( new User(answersFields.get(0), answersFields.get(1), answersFields.get(2), answersFields.get(3)));
        System.out.println(user);
        createUserFile(user, finalPaths, staticQuestions,  answersFields);
        }  


    public static void createUserFile(List<User> user,  Map<Integer, String> finalPaths, List<Questions> staticQuestions, ArrayList<String> answerFields) {
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

                //Vai percorrer até a quantidade de questões que tiver
                for(int j = 4; j<answerFields.size(); j++){
                    bw.write(answerFields.get(j));
                }
            
            
            }catch(IOException e){
                System.out.println("Error " + e.getMessage());
            }
        }

    public static void registerNewQuestion(Scanner sc, List<Questions> staticQuestions, String path) {
        int i =  staticQuestions.getLast().getId() + 1;
        System.out.println("Insirar a nova pergunta");
        sc.nextLine();
        String newQuestions = sc.nextLine();
        staticQuestions.add( new Questions(newQuestions, i));

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))){
            bw.newLine();
            bw.write(staticQuestions.getLast().getId() + "- " + staticQuestions.getLast().getQuestion());
        }catch(IOException e){
            e.getMessage();
        }
    } 
    
    public static void deleteQuestions(Scanner sc, List<Questions> staticQuestions, String path){
        System.out.println("Qual o número da pergunta que você gostaria de deletar? ");
        int option = sc.nextInt();
        if(option <= 4) {
            System.out.println("As questões padrão não podem ser deletadas");
        }else {
            staticQuestions.remove(option-1);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
                for (int i = 0; i < staticQuestions.size(); i++) {
                    Questions question = staticQuestions.get(i);
                    bw.write(question.getId() + "- " + question.getQuestion() + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error ao atualizar o arquivo: " + e.getMessage());
            }

        }
        }

        public static void searchUser(List<User> users, Scanner sc) {
            sc.nextLine();  // Limpar o buffer do scanner
            System.out.println("Digite o termo de busca (nome, idade ou email):");
            String searchTerm = sc.nextLine().toLowerCase();
        
            List<User> matchedUsers = users.stream()
                    .filter(user -> user.getName().toLowerCase().contains(searchTerm) ||
                                    user.getEmail().toLowerCase().contains(searchTerm))
                    .toList();
        
            if (matchedUsers.isEmpty()) {
                System.out.println("Nenhum usuário encontrado com o termo de busca: " + searchTerm);
            } else {
                matchedUsers.forEach(user -> {
                    System.out.println("Usuário encontrado:");
                    System.out.println("Nome: " + user.getName());
                    System.out.println("Email: " + user.getEmail());
                    System.out.println("Idade: " + user.getAge());
                    System.out.println("Altura: " + user.getHeight());
                });
            }
        }
    }

        
    

