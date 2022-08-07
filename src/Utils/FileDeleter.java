package Utils;

import java.io.File;

public class FileDeleter {
    /**
     * Delete file.
     */
    public static boolean deleteFile(String filename) {
        File f = new File(filename);
        //delete output file
        return(f.delete());
    }

    /**
     * Delete file with wildcard
     * @param path Path of files
     * @param file_name First part of file
     * @param file_extension Last part of file
     */
    public static void deleteOutputFiles(String path, String file_name, String file_extension) {
        //https://stackoverflow.com/questions/794381/how-to-find-files-that-match-a-wildcard-string-in-java
        String target_file;
        File[] listOfFiles = new File(path).listFiles();
        if(listOfFiles == null) return;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                target_file = listOfFile.getName();
                if (target_file.startsWith(file_name) && target_file.endsWith(file_extension)) {
                    deleteFile(target_file);
                }
            }
        }
    }
}
