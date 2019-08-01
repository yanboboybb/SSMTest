import com.test.entity.TestSSM;
import com.test.service.TestSSMService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @ClassName: TestT
 * @Author: yanbobo
 * @CreateDate: 2019/8/1 16:34
 */
public class TestT {

    @Test
    public void test1() {

        ApplicationContext alc = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TestSSMService testSSMService = (TestSSMService) alc.getBean("testSSMService");
        System.out.println(testSSMService);
        TestSSM testSSM = new TestSSM();
        testSSM.setName("sss");
        try {
            int rows = testSSMService.insertTestSSM(testSSM);
            System.out.println(rows);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
