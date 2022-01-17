package test.java;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import io.electrum.ecs.dao.transfer.TranLegReq;
import io.electrum.ecs.netl.handler.AdditionalSearchTermsOperation;
import io.electrum.ecs.netl.handler.MyOperationConfigYml;
import io.electrum.ecs.netl.handler.OperationConfigYml;
import io.electrum.ecs.netl.handler.Row;
import io.electrum.undercoat.configuration.ConfigurationManager;
import io.electrum.undercoat.configuration.ConfigurationUpdater;
import io.electrum.undercoat.configuration.loader.FileConfigurationLoader;
import io.electrum.undercoat.configuration.provider.YamlConfigurationProvider;
import io.electrum.undercoat.util.FileUtils;
import net.minidev.json.JSONUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static com.jayway.jsonpath.JsonPath.using;

public class ConfigTest {

    @Test
    public void testingConfig() throws Exception {
        System.setProperty("electrum.workdir", FileUtils.getAppWorkingDirPath().resolve("src/test").toString());
        ConfigurationUpdater.update(
                new YamlConfigurationProvider(
                        new FileConfigurationLoader(
                                FileUtils.getAppWorkingDirPath()
                                        .resolve("etc/config.yml"))));
       Optional<OperationConfigYml> clazz = ConfigurationManager.get("WavhudiConfig",  OperationConfigYml.class);

        Assert.assertTrue(clazz.isPresent());
        MyOperationConfigYml config = (MyOperationConfigYml) clazz.get();
        //Assert.assertEquals(config.getQuery(), "bla");

        // Testing if going to  be able  to add the searchTerm from the config
        TranLegReq tranLegReq =  new TranLegReq();
        Row row = new Row(tranLegReq);
        AdditionalSearchTermsOperation  ad = new AdditionalSearchTermsOperation(config,  null);

        // testing the jsonPath
        String json = "{\"searchTerms\": [ {\"one\": [\"Wavhudi\", \"moon\", \"Milk\"]}, {\"two\": \"Cornelius\"}, {\"three\": \"bla\"}]}";

        tranLegReq.setJsonPayload(json); // setting testing data, when pulling from an actual api we won't need this.

        ad.process(row);

        Assert.assertTrue(tranLegReq.getSearchTerms().contains("Wavhudi"));
        Assert.assertTrue(tranLegReq.getSearchTerms().contains("moon"));
        Assert.assertTrue(tranLegReq.getSearchTerms().contains("Milk"));
    }
}
