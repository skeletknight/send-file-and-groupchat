import java.io.*;
import java.net.*;

public class FileServer {
    public static void main(String[] args) {
        downloader();
    }

    public static void downloader(){
        int port = 12345; // Choose a port number
        String saveDirectory = "D:\\socketprogramming\\src\\main\\java\\got"; // Directory to save received files

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            Socket clientSocket = serverSocket.accept();

            // Create input stream to receive file
            InputStream inputStream = clientSocket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            // Read file name
            String fileName = dataInputStream.readUTF();
            System.out.println("Receiving file: " + fileName);

            // Create output stream to save the file
            File file = new File(saveDirectory, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            // Read file content and save it
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = dataInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("File received and saved successfully!");
            fileOutputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



