package io.electrum.ecs.netl.handler;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.PathNotFoundException;
import io.electrum.ecs.dao.transfer.TranLegReq;
import io.electrum.ecs.etl.EcsEtlDaemon;
import io.electrum.ecs.netl.extract.Extract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdditionalSearchTermsOperation extends Operation{

    private static Logger logger = LoggerFactory.getLogger(AdditionalSearchTermsOperation.class);

    public AdditionalSearchTermsOperation(OperationConfig config, Extract extract) {
        super(config, extract);
    }

    @Override
    public boolean canProcess(Row row) {
        return row.getData() instanceof TranLegReq;
    }

    @Override
    public boolean process(Row row) throws Exception {

        if (row.getData() instanceof TranLegReq) {
            row.setData(transform((TranLegReq) row.getData()));
        }
        return false;
    }

    protected TranLegReq transform(TranLegReq tranLegReq) throws Exception {

        MyOperationConfigYml myOperationConfigYml = (MyOperationConfigYml) getConfig();

        Configuration conf = Configuration.builder().options(Option.AS_PATH_LIST).build();
        JsonPath path =  JsonPath.compile(myOperationConfigYml.getQuery());
        try {
            if (tranLegReq.getJsonPayload() != null) {
                if (path.isDefinite()) {
                    String searchTermsFromJsonPayLoad = JsonPath.parse(tranLegReq.getJsonPayload()).read(myOperationConfigYml.getQuery());
                    tranLegReq.getSearchTerms().add(searchTermsFromJsonPayLoad);
                } else {
                    List<String> listOfSearchTerms = JsonPath.parse(tranLegReq.getJsonPayload()).read(myOperationConfigYml.getQuery());
                    for (int i = 0; i < listOfSearchTerms.size(); ++i) {
                        tranLegReq.getSearchTerms().add(listOfSearchTerms.get(i));
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("Could Find the Path `" + myOperationConfigYml.getQuery() + "` Provided", e);
        }

        return tranLegReq;
    }
}
