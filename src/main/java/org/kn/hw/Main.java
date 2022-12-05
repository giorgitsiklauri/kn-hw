package org.kn.hw;

import org.kn.hw.restclient.BPIRestClient;
import org.kn.hw.util.InteractiveConsole;
import static java.lang.System.out;
import static org.kn.hw.util.InteractiveConsole.keepOn;

public class Main {

    private static final BPIRestClient client = new BPIRestClient();

    public static void main(String[] args) {

        out.println("Welcome!");
        do {
            String currency = InteractiveConsole.acceptCurrency();
            try {
                out.printf("Current Bitcoin rate is: %s %S\n", client.getCurrentBitcoinRate(currency), currency);
                out.printf("Lowest Bitcoin rate in the last 90 days: %f %S\n", client.historicalLowest(currency), currency);
                out.printf("Highest Bitcoin rate in the last 90 days: %f %S\n\n", client.historicalHighest(currency), currency);
            } catch (NoSuchFieldException e) {
                out.println(e.getMessage());
            } catch (Exception e) {
                out.println("Invalid currency code.\n");
            }
            out.println("Repeat? 1 - yes; 0 - no.");
        } while (keepOn());
        out.println("Good bye!");
    }
}