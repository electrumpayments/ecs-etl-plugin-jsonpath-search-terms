package io.electrum.ecs.netl.handler;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import io.electrum.ecs.dao.transfer.TranLegReq;
import io.electrum.ecs.netl.extract.Extract;

import java.util.ArrayList;
import java.util.List;

public class AdditionalSearchTermsOperation extends Operation{

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
        List<String> listOfSearchTerms = new ArrayList<>();
        String searchTermsFromJsonPayLoad = null;
        if(tranLegReq.getJsonPayload() != null) {
            if(path.isDefinite()) {
                searchTermsFromJsonPayLoad = JsonPath.parse(tranLegReq.getJsonPayload()).read(myOperationConfigYml.getQuery());
                tranLegReq.getSearchTerms().add(searchTermsFromJsonPayLoad);
            }else {
                listOfSearchTerms = JsonPath.parse(tranLegReq.getJsonPayload()).read(myOperationConfigYml.getQuery());
                for(String term: listOfSearchTerms) {
                    tranLegReq.getSearchTerms().add(term);
                }
            }
        }

        //System.out.println(searchTermsFromJsonPayLoad);
        //tranLegReq.getSearchTerms().add(myOperationConfigYml.getQuery());;
        return tranLegReq;
    }
}
