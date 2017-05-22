package com.sky.tools.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

import static android.R.attr.path;

/**
 * File Utils
 * <ul>
 * Read or write file
 * <li>{@link #readFile(String, String)} read file</li>
 * <li>{@link #readFileToList(String, String)} read file to string list</li>
 * <li>{@link #writeFile(String, String, boolean)} write file from String</li>
 * <li>{@link #writeFile(String, String)} write file from String</li>
 * <li>{@link #writeFile(String, List, boolean)} write file from String List</li>
 * <li>{@link #writeFile(String, List)} write file from String List</li>
 * <li>{@link #writeFile(String, InputStream)} write file</li>
 * <li>{@link #writeFile(String, InputStream, boolean)} write file</li>
 * <li>{@link #writeFile(File, InputStream)} write file</li>
 * <li>{@link #writeFile(File, InputStream, boolean)} write file</li>
 * </ul>
 * <ul>
 * Operate file
 * <li>{@link #moveFile(File, File)} or {@link #moveFile(String, String)}</li>
 * <li>{@link #copyFile(String, String)}</li>
 * <li>{@link #getFileExtension(String)}</li>
 * <li>{@link #getFileName(String)}</li>
 * <li>{@link #getFileNameWithoutExtension(String)}</li>
 * <li>{@link #getFileSize(String)}</li>
 * <li>{@link #deleteFile(String)}</li>
 * <li>{@link #isFileExist(String)}</li>
 * <li>{@link #isFolderExist(String)}</li>
 * <li>{@link #makeDirs(String)}</li>
 * </ul>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-5-12
 */
public class FileUtils {

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    public final static String DEFAULT_CHARSET = "utf-8";

    private FileUtils() {
        throw new AssertionError();
    }

    /**
     * 注意读入的数据不再携带换行符
     * */
    public static StringBuilder readFile(File file, String charsetName) {
        StringBuilder fileContent = new StringBuilder();
        if (!file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
            // 移除最后的换行符
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IoUtils.close(reader);
        }
    }

    public static StringBuilder readFile(File file) {
        return readFile(file, DEFAULT_CHARSET);
    }

    /**
     * read file
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist or exception happen, return null, else return content of file
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        return readFile(new File(filePath), charsetName);
    }

    /**
     * 默认为utf-8
     */
    public static StringBuilder readFile(String filePath) {
        return readFile(filePath, DEFAULT_CHARSET);
    }

    /**
     * write file
     *
     * @param destFilePath
     * @param content
     * @param append       is append, if true, write to the end of file, else clear content of file and write into it
     * @return return false if content is empty or exception happen, true otherwise
     */
    public static boolean writeFile(String destFilePath, String content, boolean append) {
        if (TextUtil.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(destFilePath);
            fileWriter = new FileWriter(destFilePath, append);
            fileWriter.write(content);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            IoUtils.close(fileWriter);
        }
    }

    /**
     * write file
     * 将list 以一行一行写进文本中，并添加换行符
     * @param destFilePath
     * @param contentList
     * @param append       is append, if true, write to the end of file, else clear content of file and write into it
     * @return return false if contentList is empty, true otherwise
     */
    public static boolean writeFile(String destFilePath, List<String> contentList, boolean append) {
        if (ListUtils.isEmpty(contentList)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(destFilePath);
            fileWriter = new FileWriter(destFilePath, append);
            int i = 0;
            for (String line : contentList) {
                if (i++ > 0) {
                    fileWriter.write("\r\n");
                }
                fileWriter.write(line);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IoUtils.close(fileWriter);
        }
    }

    /**
     * write file, the string will be written to the begin of the file
     *
     * @param filePath
     * @param content
     * @return
     */
    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, false);
    }

    /**
     * write file, the string list will be written to the begin of the file
     *
     * @param filePath
     * @param contentList
     * @return
     */
    public static boolean writeFile(String filePath, List<String> contentList) {
        return writeFile(filePath, contentList, false);
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param filePath
     * @param stream
     * @return
     * @see FileUtils#writeFile(String, InputStream, boolean)
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);
    }

    /**
     * write file
     *
     * @param filePath the file to be opened for writing.
     * @param stream   the input stream
     * @param append   if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     */
    public static boolean writeFile(String filePath, InputStream stream, boolean append) {
        return writeFile(filePath != null ? new File(filePath) : null, stream, append);
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param file
     * @param stream
     * @return
     * @see FileUtils#writeFile(File, InputStream, boolean)
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /**
     * write file
     *
     * @param file   the file to be opened for writing.
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(File file, InputStream stream, boolean append) {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            IoUtils.close(o);
            IoUtils.close(stream);
        }
    }

    /**
     * move file
     *
     * @param sourceFilePath
     * @param destFilePath
     * @return
     */
    public static boolean moveFile(String sourceFilePath, String destFilePath) {
        if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
            return false;
        }
        return moveFile(new File(sourceFilePath), new File(destFilePath));
    }

    /**
     * move file
     *
     * @param srcFile
     * @param destFile
     */
    public static boolean moveFile(File srcFile, File destFile) {
        boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            if (!copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath())) {
                return false;
            }

            if (!deleteFile(srcFile.getAbsolutePath())) {
                return false;
            }
        }
        return true;
    }

    /**
     * copy file
     *
     * @param sourceFilePath
     * @param destFilePath
     * @return if exception happen or copy fail return false
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return writeFile(destFilePath, inputStream);
    }

    /**
     * read file to string list, a element of list is a line
     * 每读取一行注意不会添加换行符
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static List<String> readFileToList(String filePath, String charsetName) {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<>();
        if (!file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IoUtils.close(reader);
        }
    }

    /**
     * 获取文件名不包含后缀, 不包含后缀
     * <p>
     * <pre>
     *      getFileNameWithoutExtension(null)               =   null
     *      getFileNameWithoutExtension("")                 =   ""
     *      getFileNameWithoutExtension("   ")              =   "   "
     *      getFileNameWithoutExtension("abc")              =   "abc"
     *      getFileNameWithoutExtension("a.mp3")            =   "a"
     *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     *      getFileNameWithoutExtension("c:\\")              =   ""
     *      getFileNameWithoutExtension("c:\\a")             =   "a"
     *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
     *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     *      getFileNameWithoutExtension("/home/admin")      =   "admin"
     *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
     * </pre>
     *
     * @param filePath
     * @return file name from path, not include suffix
     * @see
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtil.isEmpty(filePath)) {
            return filePath;
        }

        int extensionPos = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePos = filePath.lastIndexOf(File.separator);
        if (filePos == -1) {
            return (extensionPos == -1 ? filePath : filePath.substring(0, extensionPos));
        }
        if (extensionPos == -1) {
            return filePath.substring(filePos + 1);
        }
        return (filePos < extensionPos ? filePath.substring(filePos + 1, extensionPos) : filePath.substring(filePos + 1));
    }

    /**
     * get file name from path, 包含后缀名
     * <p>
     * <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("c:\\")              =   ""
     *      getFileName("c:\\a")             =   "a"
     *      getFileName("c:\\a.b")           =   "a.b"
     *      getFileName("c:a.txt\\a")        =   "a"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     *
     * @param filePath
     * @return file name from path, include suffix
     */
    public static String getFileName(String filePath) {
        if (TextUtil.isEmpty(filePath)) {
            return filePath;
        }

        int filePos = filePath.lastIndexOf(File.separator);
        return (filePos == -1) ? filePath : filePath.substring(filePos + 1);
    }

    /**
     * get folder name from path
     * <p>
     * <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("c:\\")              =   "c:"
     *      getFolderName("c:\\a")             =   "c:"
     *      getFolderName("c:\\a.b")           =   "c:"
     *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
     *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     * @param filePath
     * @return
     *
     */
    public static String getFolderName(String filePath) {

        if (TextUtil.isEmpty(filePath)) {
            return filePath;
        }

        int filePos = filePath.lastIndexOf(File.separator);
        return (filePos == -1) ? "" : filePath.substring(0, filePos);
    }

    /**
     * get suffix of file from path
     * <p>
     * <pre>
     *      getFileExtension(null)               =   ""
     *      getFileExtension("")                 =   ""
     *      getFileExtension("   ")              =   "   "
     *      getFileExtension("a.mp3")            =   "mp3"
     *      getFileExtension("a.b.rmvb")         =   "rmvb"
     *      getFileExtension("abc")              =   ""
     *      getFileExtension("c:\\")              =   ""
     *      getFileExtension("c:\\a")             =   ""
     *      getFileExtension("c:\\a.b")           =   "b"
     *      getFileExtension("c:a.txt\\a")        =   ""
     *      getFileExtension("/home/admin")      =   ""
     *      getFileExtension("/home/admin/a.txt/b")  =   ""
     *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (TextUtil.isBlank(filePath)) {
            return filePath;
        }

        int extensionPos = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePos = filePath.lastIndexOf(File.separator);
        if (extensionPos == -1) {
            return "";
        }
        return (filePos >= extensionPos) ? "" : filePath.substring(extensionPos + 1);
    }

    /**
     * Creates the directory named by the trailing filename of this file, including the complete directory path required
     * to create this directory. <br/>
     * <br/>
     * <ul>
     * <strong>Attentions:</strong>
     * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
     * <li>makeDirs("C:\\Users\\Trinea\\") can create Trinea folder</li>
     * </ul>
     *
     * @param filePath
     * @return true if the necessary directories have been created or the target directory already exists, false one of
     * the directories can not be created.
     * <ul>
     * <li>if {@link FileUtils#getFolderName(String)} return null, return false</li>
     * <li>if target directory already exists, return true</li>
     * <li>return {@link File#mkdirs()}</li>
     * </ul>
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtil.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
    }

    /**
     * Indicates if this file represents a file on the underlying file system.
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtil.isBlank(filePath)) {
            return false;
        }

        return isFileExist(new File(filePath));
    }

    public static boolean isFileExist(File file){
        return file != null && file.exists() && file.isFile();
    }

    /**
     * Indicates if this file represents a directory on the underlying file system.
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (TextUtil.isBlank(directoryPath)) {
            return false;
        }

        return isFolderExist(new File(directoryPath));
    }

    public static boolean isFolderExist(File file){
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * delete file or directory
     * <ul>
     * <li>if path is null or empty, return true</li>
     * <li>if path not exist, return true</li>
     * <li>if path exist, delete recursion. return true</li>
     * <ul>
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        if (TextUtil.isBlank(path)) {
            return true;
        }

        return deleteFile(new File(path));
    }

    /**
     * 如果是文件夹删除，只要其中一个文件删除失败就返回false
     * if file = null， return false;
     * 如果既不是文件也不是文件夹，直接返回false
     * */
    public static boolean deleteFile(File file) {
        if (file == null) {
            return true;
        }

        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                if(!file.delete()){
                    return false;
                }
            } else if (f.isDirectory()) {
                deleteFile(f);
            }
        }
        return file.delete();
    }


    public static long getFileSize(File file) {
        if (file == null) {
            return -1;
        }

        return (file.exists() && file.isFile()) ? file.length() : -1;
    }

    /**
     * get file size
     * <ul>
     * <li>if path is null or empty, return -1</li>
     * <li>if path exist and it is a file, return file size, else return -1</li>
     * <ul>
     *
     * @param path
     * @return returns the length of this file in bytes. returns -1 if the file does not exist.
     */
    public static long getFileSize(String path) {
        if (TextUtil.isBlank(path)) {
            return -1;
        }

        return getFileSize(new File(path));
    }
}
