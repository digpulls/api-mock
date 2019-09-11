package extentions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import testdata.currencytests.CurrencyTestData;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

@Listeners(TestCallback.class)
public abstract class BaseTest {

    private Logger logger = LogManager.getLogger(BaseTest.class);

    /*
        Ideally, this should be a TestNG listener, inserted at runtime
        But just dumping this here for now
    */

    @DataProvider
    public Object[][] getData(Method method) {
        String filePath = method.getAnnotation(JsonSource.class).resource();
        Class<?> type = method.getAnnotation(JsonSource.class).type();
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_COMMENTS, true);

        List<CurrencyTestData> currencyTestData = null;
        try {
            String data = FileUtils.readFileToString(new File("src/test/resources/" + filePath));
            currencyTestData = mapper
                    .readValue(data, mapper.getTypeFactory().constructCollectionType(List.class, type));

        } catch (IOException e) {
            logger.error("Failed to read test data", e);
            e.printStackTrace();
        }
        Object[][] returnValue = new Object[currencyTestData.size()][1];
        int index = 0;
        for (Object[] each : returnValue) {
            each[0] = currencyTestData.get(index++);
        }
        return returnValue;
    }

}
