package test.java;


import io.electrum.undercoat.api.DummyDaemonContext;
import io.electrum.undercoat.configuration.ConfigurationUpdater;
import io.electrum.undercoat.configuration.loader.FileConfigurationLoader;
import io.electrum.undercoat.configuration.provider.YamlConfigurationProvider;
import org.flywaydb.core.Flyway;

import io.electrum.ecs.etl.EcsEtlDaemon;
import io.electrum.undercoat.util.FileUtils;

import java.text.MessageFormat;

public class RunEtlDaemon {

    private static String passwd = "M0sa1c$$";
    protected static String user = "electrum";

    static {
        System.setProperty("electrum.workdir", FileUtils.getAppWorkingDirPath().resolve("src/test").toString());
        try {
            ConfigurationUpdater.update(
                    new YamlConfigurationProvider(
                            new FileConfigurationLoader(
                                    FileUtils.getAppWorkingDirPath()
                                            .resolve("etc/manualConfig.yml"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void migrateDb(Flyway flyway, Flyway flyway2) {
        flyway.clean();
        flyway.migrate();

        flyway2.clean();
        flyway2.migrate();
    }

    private static void run() throws Throwable{
        EcsEtlDaemon etlDaemon = new EcsEtlDaemon();
        etlDaemon.init(new DummyDaemonContext());
        etlDaemon.start();

        Thread.currentThread().join();
    }

    public static void main(String... args) throws Throwable {
        Flyway ecs = Flyway.configure()
                .locations("classpath:db/migration/ecs-data-schema/MySql")
                .dataSource(
                        MessageFormat.format("jdbc:mysql://{0}:{1}/{2}?useSSL=false", "127.0.0.1", "3309", "ecs"),
                        user,
                        passwd)
                .schemas("ecs")
                .table("schema_version")
                .load();

        Flyway ecs_gateway = Flyway.configure()
                .dataSource(
                        MessageFormat.format("jdbc:mysql://{0}:{1}/{2}?useSSL=false", "127.0.0.1", "3309", "gateway"),
                        user,
                        passwd)
                .schemas("gateway")
                .table("schema_version")
                .locations("classpath:db/migration/mysql")
                .load();
//        migrateDb(ecs, ecs_gateway);
        run();
    }
}

