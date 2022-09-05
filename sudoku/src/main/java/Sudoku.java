import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// A 2021. október 26-ai emelt szintű informatika érettségi 4. feladata

public class Sudoku {
    private int[][] sheet = new int[9][9];
    private List<Step> steps = new ArrayList<>();

    private Path filename;
    Scanner scanner;

    public Sudoku(Scanner scanner, int[][]... sheet) {
        this.scanner = scanner;
        if (sheet.length > 0) {
            this.sheet = sheet[0];
        }
    }

    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku(new Scanner(System.in));
        Coordinate coordinate = sudoku.feladat1();  //Egy fájl nevének, egy sor és egy oszlop sorszámának (1 és 9 közötti szám) beolvasása.
        sudoku.feladat2();                          //Az első feladatban beolvasott névnek megfelelő fájl tartalmának beolvasása, és a táblázat adatainak eltárolása.
        sudoku.feladat3(coordinate);                //A beolvasott sor és oszlop értékének megfelelő helyen milyen szám van? Melyik résztáblázatban van?
        sudoku.feladat4();                          //A táblázat hány százaléka nincs még kitöltve?
        sudoku.feladat5();                          //A fájlban szereplő lépések lehetségesek-e?
    }

    public Path getFilename() {
        return filename;
    }

    public void setFilename(Path filename) {
        this.filename = filename;
    }

    protected String getSheet() {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < 9; ++row) {
            for (int column = 0; column < 9; ++column) {
                result.append(sheet[row][column]);
                if (column < 8) {
                    result.append(" ");
                }
            }
            if (row < 8) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    public Coordinate feladat1() {
        System.out.print("\n1. feladat\nAdja meg a bemeneti fájl nevét! ");
        filename = Path.of(scanner.nextLine());

        System.out.print("Adja meg egy sor számát! ");
        int row = scanner.nextInt();

        System.out.print("Adja meg egy oszlop számát! ");
        int column = scanner.nextInt();

        return new Coordinate(row, column);
    }

    protected void feladat2() {
        // filename = Path.of( "C:\\temp\\infoerettsegi\\2021 okt emelt\\4_Sudoku\\nehez.txt");
        System.out.println("\n2. feladat\nFájl beolvasása: " + filename.toString());

        try (BufferedReader reader = Files.newBufferedReader(filename)) {
            for (int i = 0; i < 9; ++i) {
                storeOneLine(i, reader.readLine());
            }
            String oneLine;
            while ((oneLine = reader.readLine()) != null) {
                steps.add(new Step(oneLine));
            }
        } catch (IOException e) {
            System.out.println("Nem olvasható ez a file: " + filename.toString());
        }

        displaySheet();
        displaySteps();
    }

    private void storeOneLine(int row, String readLine) {
        String[] parts = readLine.split(" ");
        for (int i = 0; i < 9; ++i) {
            sheet[row][i] = Integer.parseInt(parts[i]);
        }
    }

    private void displaySheet() {
        for (int row = 0; row < 9; ++row) {
            for (int column = 0; column < 9; ++column) {
                System.out.print(sheet[row][column] + " ");
                if (column == 2 || column == 5) {
                    System.out.print("| ");
                }
            }
            System.out.println();
            if (row == 2 || row == 5) {
                System.out.println("------+-------+------");
            }
        }
    }

    private void displaySteps() {
        for (Step step : steps) {
            System.out.println(step);
        }
    }

    private void feladat3(Coordinate coordinate) {
        System.out.println("\n3. feladat");
        int number = sheet[coordinate.getRow() - 1][coordinate.getColumn() - 1];
        System.out.println(number == 0 ? "Az adott helyet még nem töltötték ki." : "Az adott helyen szereplő szám: " + number);
        System.out.printf("A hely a(z) %d résztáblázathoz tartozik.\n", getSubSheetNumber(coordinate));
    }

    public int getSubSheetNumber(Coordinate coordinate) {
        return (coordinate.getColumn() - 1) / 3 + ((coordinate.getRow() - 1) / 3) * 3 + 1;
    }

    public void feladat4() {
        System.out.println("\n4. feladat");
        int empty = 0;
        for (int row = 0; row < 9; ++row) {
            for (int column = 0; column < 9; ++column) {
                if (sheet[row][column] == 0) {
                    ++empty;
                }
            }
        }
        System.out.printf("Az üres helyek aránya: %.1f%%\n", 100 * empty / 81.0);
    }

    private void feladat5() {
        System.out.println("\n5. feladat");
        for (Step step : steps) {
            System.out.println(step);
            if (isItFilled(step) && isItInThisRow(step) && isItInThisColumn(step) && isItInThisSubSheet(step)) {
                System.out.println("A lépés megtehető");
            }
            System.out.println();
        }
    }

    private boolean isItFilled(Step step) {
        if (sheet[step.getCoordinate().getRow() - 1][step.getCoordinate().getColumn() - 1] > 0) {
            System.out.println("A helyet már kitöltötték.");
            return false;
        }
        return true;
    }

    private boolean isItInThisRow(Step step) {
        for (int column = 0; column < 9; ++column) {
            if (sheet[step.getCoordinate().getRow() - 1][column] == step.getNumber()) {
                System.out.println("Az adott sorban már szerepel a szám.");
                return false;
            }
        }
        return true;
    }

    private boolean isItInThisColumn(Step step) {
        for (int row = 0; row < 9; ++row) {
            if (sheet[row][step.getCoordinate().getColumn() - 1] == step.getNumber()) {
                System.out.println("Az adott oszlopban már szerepel a szám.");
                return false;
            }
        }
        return true;
    }

    private boolean isItInThisSubSheet(Step step) {
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                if (sheet[row + (step.getCoordinate().getRow() - 1) / 3 * 3][column + (step.getCoordinate().getColumn() - 1) / 3 * 3] == step.getNumber()) {
                    System.out.println("A résztáblázatban már szerepel a szám.");
                    return false;
                }
            }
        }
        return true;
    }
}