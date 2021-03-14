import com.ex.cofig.GeneratorConfig;
import com.ex.generator.CodeGenerator;

import java.io.InputStream;

public class Generator {
    public static void main(String[] args) throws Exception {
        GeneratorConfig config = new GeneratorConfig();
        config.setRootPath("F:\\Java\\springmvc_final\\ssm_xml\\src\\main\\java");
        config.setPackageName("com.ex.ssm");
        InputStream in = CodeGenerator.class.getClassLoader().getResourceAsStream("druid.properties");
        config.buildDatabaseConfig(in);
        config.selectTable("user","account");
        CodeGenerator codeGenerator = new CodeGenerator(config);
        codeGenerator.generate();
    }
}
