import java.io.*;
import java.net.URL;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++)
                        System.out.println("d" + i + j + k + l + " = dx" + i + " + dy" + j + " + dz" + k + " + dw" + l + ";");
                    System.out.println();
                }
    }

    private static void docs() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL("https://docs.oracle.com/javase/8/docs/api/resources/fonts/dejavu.css").openStream()));
        PrintWriter pw = new PrintWriter(new FileWriter("target/site/apidocs/resources/fonts/dejavu.css"));
        String l;
        while ((l = br.readLine()) != null) {
            int ind = l.indexOf("url('");
            if (ind != -1) {
                l = l.substring(ind);
                ind = l.indexOf("'");
                l = l.substring(0, ind);

                InputStream in = new URL("https://docs.oracle.com/javase/8/docs/api/resources/fonts/" + l).openStream();
                OutputStream out = new FileOutputStream("target/site/apidocs/resources/fonts/" + l);

                int amount = 0;
                byte[] data = new byte[8];
                while (amount != -1) {
                    out.write(data, 0, amount);
                    amount = in.read(data);
                }

                in.close();
                out.close();
            }
        }
        br.close();
        pw.close();
    }
}
