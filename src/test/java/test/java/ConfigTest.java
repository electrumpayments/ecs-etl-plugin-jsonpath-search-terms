package test.java;

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
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Optional;

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
        Assert.assertEquals(config.getSearchTerm(), "bla");

        // Testing if going to  be able  to add the searchTerm from the config
        TranLegReq tranLegReq =  new TranLegReq();
        Row row = new Row(tranLegReq);
        AdditionalSearchTermsOperation  ad = new AdditionalSearchTermsOperation(config,  null);
        ad.process(row);
        Assert.assertTrue(tranLegReq.getSearchTerms().contains("bla"));

    }
}
