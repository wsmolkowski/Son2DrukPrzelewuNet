import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        if (args.length != 4) {
            System.out.println("Wywołaj z 4 parametrami: santander_import.txt outout.csv nadawca adres_nadawcy. Opis docelowego pliku: https://drukprzelewu.net/opis.txt ");
            return;
        }

        CSVParser csvParser = new CSVParserBuilder().withSeparator('|').build();
        try (CSVReader reader = new CSVReaderBuilder(Files.newBufferedReader(Path.of(args[0]), Charset.forName("Windows-1250")))
                .withCSVParser(csvParser)
                .withSkipLines(1)
                .build()) {

            String[] lineInArray;
            FileOutputStream fos = new FileOutputStream(args[1]);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BigDecimal suma = new BigDecimal(BigInteger.ZERO);

            try(PrintWriter out = new PrintWriter( osw )) {
                while ((lineInArray = reader.readNext()) != null) {
                    String konto = lineInArray[2];
                    String odbiorca = lineInArray[3];
                    String adres = lineInArray[4];
                    String kwota = lineInArray[5];
                    suma = suma.add(new BigDecimal(kwota.replace(',','.')));
                    String tytul = lineInArray[7];
                    String output = odbiorca + ";" + adres + ";;" + konto + ";"+args[2]+";" + args[3]+ ";" + ";;" + tytul + ";;;" +kwota+";1;2";
                    out.println(output);
                    System.out.println(output);
                }
                System.out.println("kwota do zapłaty: " + suma.toString());
            } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("brak możliwości otwarcia pliku: " + args[1]);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace(); }
        } catch (FileNotFoundException e) {
            System.out.println("Plik " + args[0] + " nie znaleziony.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            System.out.println("Błąd walidacji pliku csv: " + e.getMessage());
        }
    }
}
