package com.epam.izh.rd.online.repository;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File dir = new File(path);
        if (!(dir.isDirectory()||dir.isFile())) {
            dir = new File(getClass().getResource("/" + path).getPath());
        }
        long count = 0;
        if (!dir.isDirectory()) {
            return count;
        }
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                count += countFilesInDirectory(file.getPath());
            } else {
                count++;
            }
        }
        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File dir = new File(path);
        if (!(dir.isDirectory()||dir.isFile())) {
            dir = new File(getClass().getResource("/" + path).getPath());
        }
        long count = 0;
        if (!dir.isDirectory()){
            return count;
        }else{
            count++;
        }
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                count += countDirsInDirectory(file.getPath());
            }
        }
        return count;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
   /*    File fromFile = new File(from);
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(fromFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String test1 = fromFile.getName();
        String test2 = fromFile.getParent();
        System.out.println();*/


    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        Boolean tester = null;
        String root = new File("").getAbsolutePath();
        File dir = new File(root+"/"+path);
        if (!dir.exists()){
            dir.mkdirs();
        }
        if (!name.endsWith(".txt")){
            name=name+".txt";
        }
        File file = new File(dir+"/"+name);
        if (file.exists()){
            file.delete();
        }
        try {
            tester =  file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tester;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        return null;
    }
}
