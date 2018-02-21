package com.training;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class MainApp {

    //счётчик mp3 файлов
    public static int numberOfMP3;
    public static String fileExt = "mp3";
    public static ArrayList<Author> authors = new ArrayList<>();

    public static void main(String[] args) {

        //введено достаточное количество параметров? (>=1)
        if (args.length < 1) {
            System.out.println("There must at least 1 parameter be entered: directory path (may be several).");
            return;
        }
        //массив "где будем искать"
        HashSet<String> uniquePaths = new HashSet<>();

        //заносим пути каталогов для поиска в set
        for (int i = 0; i < args.length; i++) {
            uniquePaths.add(args[i]);
        }

        ArrayList<String> paths = new ArrayList<>(uniquePaths);

        Path path = null;

        for (int i = 0; i < paths.size(); ) {
            path = Paths.get(paths.get(i));
            //каталог для поиска указан верно?
            if (Files.notExists(path)) {
                System.out.println("Path " + paths.get(i) + " doesn't exist. It will not be added to the search list.");
                paths.remove(i);
                //это каталог?
            } else if (Files.isDirectory(path)) {
                i++;
                continue;
                //это не каталог и путь к этому "нечто" указан неверно
            } else {
                System.out.println("Only directories parameters must be specified. " +
                        paths.get(i) + " is not a directory");
                paths.remove(i);
            }
        }

        //все пути к каталогам указаны неверно (каталоги не существуют)
        if (paths.size() < 1) {
            System.out.println("There are now existing directories with such paths to find in. Check path's spelling and try again.");
            return;
        }

        for (String a_path : paths) {
            File folder = new File(a_path);
            //Массив файлов/папок каталога
            File[] list = folder.listFiles();
            if (list.length > 0) {
                //ищем файлы с fileExt
                numberOfMP3 = SearcherForMP3.findMP3(list, fileExt, authors);

            }
        }

        if (numberOfMP3 == 0) {
            System.out.println("There are no mp3 files in specified directory(ies).");
            return;
        }

        if (authors.size() > 0) {
            HTMLGenerator.getHTML(authors);
        }
        System.out.println("Search is completed.");
    }

}

