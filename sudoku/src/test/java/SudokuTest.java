import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SudokuTest {

    @Test
    void feladat1Teszt() {
        Scanner scanner = new Scanner("file neve\n5\n7\n");
        Sudoku sudoku = new Sudoku(scanner);
        Coordinate result = sudoku.feladat1();

        assertEquals("file neve", sudoku.getFilename().toString());
        assertEquals(5, result.getRow());
        assertEquals(7, result.getColumn());
    }

    @TempDir
    Path tempDirectory;

    @Test
    void feladat2Teszt() {
        Path path = tempDirectory.resolve("file neve");
        String testData = """
                             1 2 3 4 5 6 7 8 9
                             2 3 4 5 6 7 8 9 8
                             3 4 5 6 7 8 9 8 7
                             4 5 6 7 8 9 8 7 6
                             5 6 7 8 9 8 7 6 5
                             6 7 8 9 8 7 6 5 4
                             7 8 9 0 1 2 3 4 5
                             8 9 8 7 6 5 4 3 2
                             9 8 7 6 5 4 3 2 1""";
        try {
            System.out.println(testData);
            Files.writeString(path, testData, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } catch (Exception e) {
            System.out.println("Rendszerhiba: Nem lehet létrehozni a teszt fájlt! " + e);
        }

        Sudoku sudoku = new Sudoku(null);
        sudoku.setFilename(path);
        sudoku.feladat2();

        assertEquals(path,sudoku.getFilename());
        assertEquals(sudoku.getSheet(),testData);

    }



    @RepeatedTest(value = 20, name = "Get sub-sheet number {currentRepetition}/{totalRepetitions}")
    void getSubSheetNumber(RepetitionInfo repetitionInfo) {
        Sudoku sudoku = new Sudoku(null);

        int round = repetitionInfo.getCurrentRepetition() - 1;
        assertEquals(values[round][2], sudoku.getSubSheetNumber(new Coordinate(values[round][0], values[round][1])));
    }

    private int[][] values = {
            {1, 1, 1},
            {1, 9, 3},
            {3, 3, 1},
            {3, 4, 2},
            {3, 6, 2},
            {3, 7, 3},
            {4, 3, 4},
            {4, 4, 5},
            {4, 6, 5},
            {4, 7, 6},
            {6, 3, 4},
            {6, 4, 5},
            {6, 6, 5},
            {6, 7, 6},
            {7, 3, 7},
            {7, 4, 8},
            {7, 6, 8},
            {7, 7, 9},
            {9, 1, 7},
            {9, 9, 9}
    };

    @Test
    void feladat4Test() {
        int[][] sheetEmpty = new int[9][9];
        int[][] sheetHalf = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        int[][] sheetFull = new int[][]{
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        PrintStream psOld = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psNew = new PrintStream(baos);
        System.setOut(psNew);
        Sudoku sudoku = new Sudoku(null, sheetEmpty);
        sudoku.feladat4();
        sudoku = new Sudoku(null, sheetHalf);
        sudoku.feladat4();
        sudoku = new Sudoku(null, sheetFull);
        sudoku.feladat4();
        System.out.flush();
        System.setOut(psOld);
        Scanner scanner = new Scanner(baos.toString());
        List<String> result = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String oneLine = scanner.nextLine();
            if ("Az üres helyek aránya: ".equals((oneLine + "                       ").substring(0, 23))) {
                result.add(oneLine.substring(23));
            }
        }
        assertEquals("100,0%", result.get(0));
        assertEquals("51,9%", result.get(1));
        assertEquals("0,0%", result.get(2));

    }
}