package DataBase;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageToByteArray {
    public static void insertImage(String pack, List<byte[]> imageData) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db/skin_db.db");

            // Insertar la imagen en la base de datos
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO Skins (PackName, CatSkin, PalomaSkin, PalomaMovida, TreeSkin, PaloSkin, PajareraSkin) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            insertStatement.setString(1, pack);
            insertStatement.setBytes(2, imageData.get(0));
            insertStatement.setBytes(3, imageData.get(1));
            insertStatement.setBytes(4, imageData.get(2));
            insertStatement.setBytes(5, imageData.get(3));
            insertStatement.setBytes(6, imageData.get(4));
            insertStatement.setBytes(7, imageData.get(5));
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
        String pack = "BLACK_DEFAULT";
        List<String> names = new ArrayList<>();
        names.add("Images/a.PNG");
        names.add("Images/b.PNG");
        names.add("Images/c.PNG");
        names.add("Images/d.PNG");
        names.add("Images/e.PNG");
        names.add("Images/f.PNG");

        List<byte[]> images = new ArrayList<>();

        // Leer todas las imágenes y convertirlas a byte arrays
        for (String name : names) {
            try {
                ByteArrayOutputStream baos = getByteArrayOutputStream(name);
                images.add(baos.toByteArray());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Insertar la imagen en la base de datos
        insertImage(pack, images);
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
