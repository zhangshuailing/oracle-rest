package nk.gk.wyl.oracle;

import nk.gk.wyl.oracle.api.OracleSelectService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class OracleRestApplicationTests {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private OracleSelectService oracleSelectService;

    @Test
    void contextLoads() {
        try {
            List<String> values = new ArrayList<>();
            /*values.add("1");
            values.add("11");*/
            List<Map<String,Object>> list = oracleSelectService.findListByIns(sqlSessionTemplate,
                    "demo",
                    "id",
                    values,
                    null,
                    null);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
