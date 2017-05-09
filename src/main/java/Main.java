import com.scitools.understand.Database;
import com.scitools.understand.Entity;
import com.scitools.understand.Reference;
import com.scitools.understand.Understand;

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
    Entity[] packages = db.ents("Java Package ~unused ~unresolved ~unknown");
    System.out.println(packages.length);
    for (final Entity pkg : packages) {
      System.out.println("Package" + pkg.kind());
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
    final String pkgName = pkg.longname(true);
    for (final Reference clazz : pkg.refs("~unused", "Class ", true)) {
      System.out.println("class" + clazz.ent().kind());
      final String className = clazz.ent().longname(true);
      final Entity file = clazz.file();
      final String fileName = file.longname(true);
      processInnerClasses(pkgName, clazz);
      processMethods(className, pkgName, file, clazz.ent().refs("Define", "Method", true));
    }
  }

  private static void processInnerClasses(final String pkgName, final Reference clazz) {
    final Reference[] innerClasses = clazz.ent()
        .refs("Define ~unresolved ~unknown", "Class ~TypeVariable", true);
    for (Reference innerClass :
        innerClasses) {
      final String className = innerClass.ent().longname(true);
      final Entity file = clazz.file();
      processMethods(className, pkgName, file, innerClass.ent().refs("Define", "Method", true));
    }
  }

  private static void processMethods(final String className, final String pkgName,
      final Entity file,
      final Reference[] methods) {
    final String fileName = file.longname(true);
    for (final Reference method : methods) {
      final Reference[] endRefs = method.ent().refs("End", "", true);
      if (endRefs == null || endRefs.length == 0) {
        continue;
      }

    }
  }

  private static String getMethodSignature(final Reference method) {
    final Reference[] params = method.ent().refs("", "Parameters ~Catch ~Type", true);
    final String methodName = method.ent().simplename();
    final StringBuilder args = new StringBuilder();
    for (int i = 0; i < params.length; i++) {

    }
    return null;
  }
}
