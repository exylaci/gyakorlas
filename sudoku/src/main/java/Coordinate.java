/**
 * <h1>Beírandó adat pozíciója</h1>
 * Ebben tárolódik melyik rubrikába kívánja a számot beírni.
 * Sudokunál ez alapértelmezetten <b>1-9</b> közötti egész szám lehet.
 *
 * @param row a sor sorszáma
 * @param column az oszlop sorszáma
 */
public class Coordinate {
    private int row;
    private int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "sor: " + row + " oszlop: " + column;
    }
}
