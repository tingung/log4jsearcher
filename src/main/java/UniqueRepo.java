import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public class UniqueRepo {

    static final String GITHUB_TRILOGY_URL = "https://github.com/trilogy-group/";

    public static void main(String[] args) throws IOException {
        // checkout https://github.com/trilogy-group/eng.hub to <eng.hub> and update the variable engHubPath
        String engHubPath = "/home/hutingung/workspace/enghub/eng.hub/";
        // args[0] is the product name
        File c4model = new File(engHubPath + "Products DS/" + args[0] + "/c4model.yaml");
        List<String> inputs = FileUtils.readLines(c4model, StandardCharsets.UTF_8);
        Set<String> repos = new HashSet<>();
        inputs.forEach(line -> {
            if (line.contains(GITHUB_TRILOGY_URL)) {
                try {
                    String repoName = line.substring(line.indexOf(GITHUB_TRILOGY_URL) + GITHUB_TRILOGY_URL.length());
                    if (repoName.contains("/")) {
                        repoName = repoName.substring(0, repoName.indexOf("/"));
                    }
                    repos.add(GITHUB_TRILOGY_URL + repoName);
                } catch (Exception e) {
                    System.err.println(line);
                    e.printStackTrace();
                }
            }
        });
        repos.forEach(repo -> {
            System.out.println(repo);
        });
    }
}
