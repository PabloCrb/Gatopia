package DataBase;

import java.io.*;
import java.sql.*;

public class ImageToByteArray {
    public static void insertImage(String nombre, byte[] imageData) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/skin_db.db");

            // Insertar la imagen en la base de datos
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Skins (Nombre, Imagen) VALUES (?, ?)");
            insertStatement.setString(1, nombre);
            insertStatement.setBytes(2, imageData);
            insertStatement.executeUpdate();

            System.out.println("Imagen insertada correctamente en la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String name = "Paloma_black_bichota";
        String imageName = "Images/n.png";
        byte[] imageData = null;

        try {
            ByteArrayOutputStream baos = getByteArrayOutputStream(imageName);

            imageData = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Insertar la imagen en la base de datos
        insertImage(name, imageData);
    }

    private static ByteArrayOutputStream getByteArrayOutputStream(String imageName) throws IOException {
        File imageFile = new File(imageName);
        ByteArrayOutputStream baos;
        try (FileInputStream fis = new FileInputStream(imageFile)) {

            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        }
        return baos;
    }
}
