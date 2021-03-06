import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

public class Main {

    public static void main(String[] args) throws IOException {
        File dir = new File(args[0]);
        Collection<File> files = FileUtils.listFiles(dir, new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().equals("pom.xml");
            }

            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        }, new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                return true;
            }

            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        });
        files.forEach( file-> {
            String line = "mvn -f " + file.getAbsolutePath() + " dependency:tree --no-snapshot-updates";
            try {
                Process process = Runtime.getRuntime().exec(line);
                if(hasLog4j(process, line)) {
                    System.out.println(file.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static boolean hasLog4j(Process process, String command) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (line.contains("FAILURE")) {
                System.err.println(command);
                System.err.println(line);
            }
            if(line.contains("log4j-core")) {
                return true;
            }
        }
        return false;
    }
}
