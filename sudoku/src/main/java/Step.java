/**
 * <h1>Lépés</h1>
 * Ebben tárolódik milyen koordinátára, milyen számot akar beírni.
 * Sudoku táblázatba alapértelmezetten <b>1-9</b> közötti egész számokat lehet beírni.
 *
 * @param all szöveges formában, szóközökkel tagolva:
 *            a beírandó szám,
 *            a sora,
 *            az oszlopa.
 */
public class Step {
    private int number;
    private Coordinate coordinate;

    Step(String all) {
        String[] parts = all.split(" ");
        number = Integer.parseInt(parts[0]);
        coordinate = new Coordinate(Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]));
    }

    public int getNumber() {
        return number;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        return "A kiválasztott " + coordinate + " a szám: " + number;
    }
}
