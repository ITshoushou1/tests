import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class ApplicationAPP {


    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClassPathResource resource =  new ClassPathResource("applicationContext.xml");
        System.out.println(resource.getPath());
        System.out.println(resource.getURL());
        System.out.println(resource.getURI());

    }
}
