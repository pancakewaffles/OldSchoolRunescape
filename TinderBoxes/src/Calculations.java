

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

public class Calculations {
	
    private final static String BASE_URL = "http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=";
    private final static int MILLION = 1000000;
    private final static int THOUSAND = 1000;
	
	public static int Random(int min, int max){
		Random r = new Random();
		return r.nextInt((max - min) + 1) +min;
	}
	
    public static int getPrice(final int id) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(BASE_URL + id).openStream()))) {
            final String raw = reader.readLine().replace(",", "").replace("\"", "").split("price:")[1].split("}")[0];
            return raw.endsWith("m") || raw.endsWith("k") ? (int) (Double.parseDouble(raw.substring(0, raw.length() - 1))
                    * (raw.endsWith("m") ? MILLION : THOUSAND)) : Integer.parseInt(raw);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


}
