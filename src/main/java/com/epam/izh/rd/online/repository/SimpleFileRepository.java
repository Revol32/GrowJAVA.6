package com.epam.izh.rd.online.repository;

import java.io.*;
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
        if (!(dir.isDirectory() || dir.isFile())) {
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
        if (!(dir.isDirectory() || dir.isFile())) {
            dir = new File(getClass().getResource("/" + path).getPath());
        }
        long count = 0;
        if (!dir.isDirectory()) {
            return count;
        } else {
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
    public void copyTXTFiles(String from, String to){
        File toFile = new File(to);
        File fromFile = new File(from);
        if (fromFile.isFile() && from.endsWith(".txt")) {
            try (FileReader reader = new FileReader(from); FileWriter writer = new FileWriter(toFile)) {
                StringBuilder stringBuilder = new StringBuilder();
                while (reader.ready()) {
                    stringBuilder.append((char) reader.read());
                }
                writer.write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        ClassLoader classLoader = this.getClass().getClassLoader();
        String resource = Objects.requireNonNull(classLoader.getResource("")).getPath() + path;
        if (!name.endsWith(".txt")) {
            name += ".txt";
        }
        File file = new File(resource, name);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(new File("src/main/resources", fileName))) {
            while (reader.ready()) {
                stringBuilder.append((char) reader.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
