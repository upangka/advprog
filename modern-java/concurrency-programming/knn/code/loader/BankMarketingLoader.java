///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
package loader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BankMarketingLoader {
    final Path path;

    public BankMarketingLoader(String pathStr){
        this.path = Paths.get(pathStr);
    }

    

    
}