package io.electrum.ecs.netl.handler;

import io.electrum.ecs.dao.transfer.TranLegReq;
import io.electrum.ecs.netl.dao.Transfer.SwitchTranLegReq;
import io.electrum.ecs.netl.extract.Extract;
import io.electrum.ecs.netl.extract.ITranLegExtractConfig;

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

    protected TranLegReq transform(TranLegReq switchTranLegReq) throws Exception {
        TranLegReq tranLegReq = new TranLegReq();
        tranLegReq.setMsgDesc("Wavhudi");
        return tranLegReq;
    }
}
