/**
 * TridicKnih - Program pro roztřídění knih na základě roku vydání.
 *
 * Tento program načítá XML soubor obsahující informace o knihách a roztřídí je
 * do dvou souborů CSV: "knihy_stare.csv" pro knihy vydané před zadaným rokem zlomu
 * a "knihy_nove.csv" pro knihy vydané v zadaném roce zlomu nebo později.
 *
 * Použití:
 * java TridicKnih <cesta_k_knihy.xml> <rok_zlomu>
 *
 * @param args Argumenty příkazové řádky - cesta k XML souboru a rok zlomu.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class TridicKnih {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Pouzijte prosim program takto: java TridicKnih <cesta_k_knihy.xml> <rok_zlomu>");
            System.exit(1);
        }

        String knihyPath = args[0];
        int rokZlomu = Integer.parseInt(args[1]);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(knihyPath));

            Element rootElement= document.getDocumentElement();
            NodeList knihy = rootElement.getElementsByTagName("Kniha");

            List<String> knihyStare = IntStream.range(0, knihy.getLength())
                    .mapToObj(i -> (Element) knihy.item(i))
                    .filter(knihaElement -> Integer.parseInt(knihaElement.getAttribute("Vydano")) < rokZlomu)
                    .map(TridicKnih::knihaToCSV)
                    .toList();

            List<String> knihyNove = IntStream.range(0, knihy.getLength())
                    .mapToObj(i -> (Element) knihy.item(i))
                    .filter(knihaElement -> Integer.parseInt(knihaElement.getAttribute("Vydano")) >= rokZlomu)
                    .map(TridicKnih::knihaToCSV)
                    .toList();

            writeKnihyToCSVFile("knihy_stare.csv", knihyStare);
            writeKnihyToCSVFile("knihy_nove.csv", knihyNove);

            System.out.println("Knihy uspesne roztrideny!");

        } catch (Exception e) {
            System.out.println("Chyba pri trizeni knih");
        }
    }

    /**
     * Metoda převede element kniha do řádku CSV.
     *
     * @param knihaElement Element reprezentující informace o knize.
     * @return Řádek CSV obsahující informace o knize.
     */
    private static String knihaToCSV(Element knihaElement) {
        String isbn = knihaElement.getAttribute("ISBN");
        String vydano = knihaElement.getAttribute("Vydano");
        String nazev = knihaElement.getElementsByTagName("Nazev").item(0).getTextContent();

        Element autorElement = (Element) knihaElement.getElementsByTagName("Autor").item(0);
        String jmeno = autorElement.getAttribute("Jmeno");
        String prijmeni = autorElement.getAttribute("Prijmeni");

        return String.format("%s;%s;%s;%s %s", isbn, vydano, nazev, jmeno, prijmeni);
    }

    /**
     * Metoda zapíše řádky CSV do souboru.
     *
     * @param fileName Název souboru, do kterého se zapíšou data.
     * @param knihyCSV List řádků CSV k zapsání.
     * @throws IOException Chyba při zápisu do souboru.
     */
    private static void writeKnihyToCSVFile(String fileName, List<String> knihyCSV) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)){
            writer.write("ISBN;Vydano;Nazev;Autor\n");
            for (String kniha : knihyCSV) {
                writer.write(kniha + "\n");
            }
        }
    }
}


