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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if(canProcessTransaction(myOperationConfigYml, tranLegReq)) {
            for (String query : myOperationConfigYml.getQuery()) {
                JsonPath path = JsonPath.compile(query);
                try {
                    if (tranLegReq.getJsonPayload() != null) {
                        if (path.isDefinite()) {
                            String searchTermsFromJsonPayLoad = JsonPath.parse(tranLegReq.getJsonPayload()).read(query);
                            tranLegReq.getSearchTerms().add(searchTermsFromJsonPayLoad);
                        } else {
                            List<String> listOfSearchTerms = JsonPath.parse(tranLegReq.getJsonPayload()).read(query);
                            for (int i = 0; i < listOfSearchTerms.size(); ++i) {
                                tranLegReq.getSearchTerms().add(listOfSearchTerms.get(i));
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.warn("Could Find the Path `" + myOperationConfigYml.getQuery() + "` Provided", e);
                }
            }
        }

        return tranLegReq;
    }

    protected boolean canProcessTransaction(MyOperationConfigYml myOperationConfigYml, TranLegReq tranLegReq) {

        HashMap<String, String> filtersOfStringType = myOperationConfigYml.generateFiltersOfStringType();
        HashMap<String, Integer> filtersOfIntegerType = myOperationConfigYml.generateFiltersOfIntegerType();

        if(!(filtersOfIntegerType.isEmpty()) || !(filtersOfStringType.isEmpty())) {
            for(Map.Entry<String, String> mapElements : filtersOfStringType.entrySet()) {
                String k = mapElements.getKey();
                String v = mapElements.getValue();
                switch (k) {
                    case "jsonPayloadClass":
                        if (!(v.equals(tranLegReq.getJsonPayloadClass()))) return false;
                        break;
                    case "inletCogId":
                        if (!(v.equals(tranLegReq.getInletCogId()))) return false;
                        break;
                    case "cogId":
                        if (!(v.equals(tranLegReq.getCogId()))) return false;
                        break;
                    case "queueId":
                        if (!(v.equals(tranLegReq.getQueueId()))) return false;
                        break;
                    case "serviceType":
                        if (!(v.equals(tranLegReq.getServiceType()))) return false;
                        break;
                    case "serviceMsgType":
                        if (!(v.equals(tranLegReq.getServiceMsgType()))) return false;
                        break;
                }
            }

            // for the second list
            for(Map.Entry<String, Integer> mapElements : filtersOfIntegerType.entrySet()) {
                String k = mapElements.getKey();
                Integer v = mapElements.getValue();
                switch (k) {
                    case "connId":
                        if (!(v == tranLegReq.getConnId())) return false;
                        break;
                    case "operation":
                        if (!(v == tranLegReq.getOperation())) return false;
                        break;
                    case "deliveryMethod":
                        if (!(v == tranLegReq.getDeliveryMethod())) return false;
                        break;
                }
            }
        }

        return true;
    }
}
