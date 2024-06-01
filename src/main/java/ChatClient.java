import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {

        System.out.println("hello chose what you want to do:");
        System.out.println("1.chat");
        System.out.println("2.download");
        Scanner in = new Scanner(System.in);
        int chose = in.nextInt();
        if(chose==1){
            chat();
        } else if (chose==2) {
            download();

        }

    }
    public static void chat() {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to the chat server!");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            out.println(username);

            String userInput;
            while (true) {
                userInput = scanner.nextLine();
                out.println(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static void download() {
            String serverAddress = "127.0.0.1"; // Server IP address
            int serverPort = 12345; // Server port

            // Prompt the user to enter the path to the file they want to send
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the path to the file: ");
            System.out.println("remember to enter the path without the \"\"");
            String filePath = scanner.nextLine();

            try (Socket socket = new Socket(serverAddress, serverPort)) {
                // Create output stream to send file
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                // Send file name
                File file = new File(filePath);
                dataOutputStream.writeUTF(file.getName());

                // Read file content and send it
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("File sent successfully!");
                fileInputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


}
