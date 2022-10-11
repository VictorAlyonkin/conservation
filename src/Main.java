import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(50, 25, 10, 100.00);
        GameProgress gameProgress2 = new GameProgress(75, 15, 10, 75.00);
        GameProgress gameProgress3 = new GameProgress(100, 10, 10, 50.00);
        saveGame("C://Games/savegames/gameProgress1.dat", gameProgress1);
        saveGame("C://Games/savegames/gameProgress2.dat", gameProgress2);
        saveGame("C://Games/savegames/gameProgress3.dat", gameProgress3);
        zipFiles("C://Games/savegames/save.zip", "C://Games/savegames");

    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void zipFiles(String zipPath, String filePath) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath)); //"C://Games/savegames/save.zip"
        ) {
            File dir = new File(filePath);
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.getName().endsWith(".zip"))
                        continue;
                    try (FileInputStream fis = new FileInputStream(file.getAbsoluteFile())) {
                        ZipEntry entry = new ZipEntry(file.getName());
                        zos.putNextEntry(entry);
                        byte[] buffer = new byte[fis.available()];
                        fis.read(buffer);
                        zos.write(buffer);
                        zos.closeEntry();
                    }
                    file.delete();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
