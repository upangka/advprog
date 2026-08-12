///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
package loader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.BankMarketing;

public class BankMarketingLoader {
    final Path path;

    public BankMarketingLoader(String pathStr){
        this.path = Paths.get(pathStr);
        System.out.println(path.toAbsolutePath());
    }

    public List<BankMarketing> load(){
        var ret = new ArrayList<BankMarketing>();

        try (var ins = Files.newInputStream(this.path);
            var insReader = new InputStreamReader(ins);
            BufferedReader reader = new BufferedReader(insReader);) {
                String line;
                while((line = reader.readLine()) != null){
                    String[] columns = line.split(";");
                    ret.add(new BankMarketing(columns));
                }
               
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}