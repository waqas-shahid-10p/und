import com.scitools.understand.Database;
import com.scitools.understand.Entity;
import com.scitools.understand.Reference;
import com.scitools.understand.Understand;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by OMEN 15 on 5/7/2017.
 */
public class Main {

  public static final String COMMAND = "und create -languages all add \"D:\\Crossover\\Assignments\\CAONB-298\\deadcode-detection\" analyze -all myDb.udb";

  public static void main(String[] args) throws Exception {
    Understand.loadNativeLibrary();
    final Database db = Understand.open("myDb.udb");
    for (String language : db.language()) {
      System.out.println(language);
    }
    Entity[] packages = db.ents("Package ~unused");
    //System.out.println(packages.length);
    for (final Entity pkg : packages) {
      System.out.println("Package"+pkg.kind());
      parseClasses(pkg);
    }
    try {

    } finally {
      db.close();
    }
    /*ProcessBuilder builder = new ProcessBuilder(
        "cmd.exe", "/c", COMMAND);
    builder.redirectErrorStream(true);
    Process p = builder.start();
    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
    String line;
    while (true) {
      line = r.readLine();
      if (line == null) { break; }
      System.out.println(line);
    }*/
  }

  private static void parseClasses(final Entity pkg) {
    for (final Reference clazz : pkg.refs("~unused", "Class ~", true)) {
      System.out.println("class"+clazz.ent().kind());
      //System.out.println(clazz.ent().longname(true));
      Entity file = clazz.file();
     // System.out.println(file.longname(true));
      parseMethods(clazz.ent());
    }
  }

  private static void parseMethods(final Entity clazz) {
    for (final Reference method : clazz.refs("Define", "Method", true)) {
      System.out.println("method"+method.ent().kind());
      Reference[] parameters = method.ent().refs("","Parameter ~Catch ~Type",true);
      //System.out.println(method.ent().longname(true));
      Entity file = method.file();
      //System.out.println(file.longname(true));
      //System.out.println(method.ent().toString());
      //System.out.println(method.ent().type());
    }
  }
}
