import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name MybatisGen ...
 *
 * @author wangk
 * Created on 2020/3/11 14:57
 */
public class MybatisGen {

    public static void main(String[] args) throws Exception {
        System.out.println("------Begin------");
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("generator.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        if (warnings.isEmpty()) {
            System.out.println("MyBatis文件生成成功！！");
        } else {
            System.err.println(warnings);
        }
        System.out.println("------End------");
    }
}
