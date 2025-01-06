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
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<Questions> staticQuestions = new ArrayList<>();
        List<User> users = new ArrayList<>();
        Map<Integer, String> finalPaths = new TreeMap<>();

        String path = ".\\formulario.txt";
        
        loadQuestionsFromFile(path, staticQuestions);

        int select = 0;
        while (select != 6) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Cadastrar o usuário");
            System.out.println("2 - Listar todos os usuários cadastrados");
            System.out.println("3 - Cadastrar nova pergunta no formulário");
            System.out.println("4 - Deletar pergunta no formulário");
            System.out.println("5 - Pesquisar usuário por nome, idade ou email");
            System.out.println("6 - Encerrar");
            System.out.print("Escolha uma opção: ");

            try {
                select = Integer.parseInt(sc.nextLine());
                switch (select) {
                    case 1 -> registerUsers(users, staticQuestions, sc, finalPaths);
                    case 2 -> listUsers(users);
                    case 3 -> registerNewQuestion(sc, staticQuestions, path);
                    case 4 -> deleteQuestions(sc, staticQuestions, path);
                    case 5 -> searchUser(users, sc);
                    case 6 -> System.out.println("Encerrando o programa...");
                    default -> System.out.println("Opção inválida! Escolha entre 1 e 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido!");
            }
        }

        sc.close();
    }

    private static void loadQuestionsFromFile(String path, List<Questions> staticQuestions) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                String questionText = line.split("-")[1].trim();
                staticQuestions.add(new Questions(questionText, i++));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    private static void listUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado ainda!");
            return;
        }

        System.out.println("\nUsuários cadastrados:");
        int i = 1;
        for (User user : users) {
            System.out.println(i + "- " + user.getName() + " (" + user.getEmail() + ")");
            i++;
        }
    }

    private static void registerUsers(List<User> users, List<Questions> staticQuestions, Scanner sc, Map<Integer, String> finalPaths) {
        ArrayList<String> answersFields = new ArrayList<>();

        for (Questions question : staticQuestions) {
            String answer;
            do {
                System.out.println(question.getId() + "- " + question.getQuestion());
                answer = sc.nextLine().trim();
                if (answer.isEmpty()) {
                    System.out.println("Por favor, digite uma resposta!");
                }
            } while (answer.isEmpty());

            answersFields.add(answer);
        }

        users.add(new User(answersFields.get(0), answersFields.get(1), answersFields.get(2), answersFields.get(3)));
        System.out.println("Usuário cadastrado com sucesso!");
        createUserFile(users, finalPaths, staticQuestions, answersFields);
    }

    private static void createUserFile(List<User> users, Map<Integer, String> finalPaths, List<Questions> staticQuestions, ArrayList<String> answersFields) {
        int i = users.size();
        String finalPath = ".\\" + i + "- " + users.get(i - 1).getName().toUpperCase().replace(" ", "") + ".txt";
        finalPaths.put(i, finalPath);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(finalPath))) {
            User user = users.get(i - 1);
            bw.write("Nome: " + user.getName() + "\n");
            bw.write("Email: " + user.getEmail() + "\n");
            bw.write("Idade: " + user.getAge() + "\n");
            bw.write("Altura: " + user.getHeight() + "\n");

            for (int j = 4; j < answersFields.size(); j++) {
                bw.write("Resposta " + (j - 3) + ": " + answersFields.get(j) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo: " + e.getMessage());
        }
    }

    private static void registerNewQuestion(Scanner sc, List<Questions> staticQuestions, String path) {
        System.out.println("\nInsira a nova pergunta:");
        String newQuestion = sc.nextLine().trim();

        if (newQuestion.isEmpty()) {
            System.out.println("A pergunta não pode estar vazia!");
            return;
        }

        int newId = staticQuestions.size() + 1;
        staticQuestions.add(new Questions(newQuestion, newId));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.newLine();
            bw.write(newId + "- " + newQuestion);
            System.out.println("Pergunta adicionada com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar pergunta: " + e.getMessage());
        }
    }

    private static void deleteQuestions(Scanner sc, List<Questions> staticQuestions, String path) {
        System.out.println("\nQual o número da pergunta que você gostaria de deletar? ");
        try {
            int option = Integer.parseInt(sc.nextLine());

            if (option <= 4) {
                System.out.println("As questões padrão (1-4) não podem ser deletadas!");
                return;
            }

            if (option > staticQuestions.size()) {
                System.out.println("Número de pergunta inválido!");
                return;
            }

            staticQuestions.remove(option - 1);

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
                for (Questions question : staticQuestions) {
                    bw.write(question.getId() + "- " + question.getQuestion());
                    bw.newLine();
                }
                System.out.println("Pergunta removida com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido!");
        }
    }

    private static void searchUser(List<User> users, Scanner sc) {
        if (users.isEmpty()) {
            System.out.println("Não há usuários cadastrados para buscar!");
            return;
        }

        System.out.println("\nDigite o nome, idade ou email para buscar:");
        String searchTerm = sc.nextLine().toLowerCase();

        boolean found = false;
        for (User user : users) {
            if (user.getName().toLowerCase().contains(searchTerm) ||
                user.getEmail().toLowerCase().contains(searchTerm) ||
                user.getAge().toLowerCase().contains(searchTerm)) {

                System.out.println("\nUsuário encontrado:");
                System.out.println("Nome: " + user.getName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Idade: " + user.getAge());
                System.out.println("Altura: " + user.getHeight());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Nenhum usuário encontrado!");
        }
    }
}
