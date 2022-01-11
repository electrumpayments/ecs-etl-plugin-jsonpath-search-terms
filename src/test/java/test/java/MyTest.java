package test.java;

import io.electrum.ecs.dao.transfer.TranLegReq;
import io.electrum.ecs.netl.handler.AdditionalSearchTermsOperation;
import io.electrum.ecs.netl.handler.Row;
import org.junit.Assert;
import org.junit.Test;

public class MyTest {

    @Test
    public void testAdditionalSearchTerms() throws Exception {

        AdditionalSearchTermsOperation  ad = new AdditionalSearchTermsOperation(null,  null);

        TranLegReq tranLegReq  = new TranLegReq();
        Row myRow  = new Row(tranLegReq);
        Assert.assertFalse(ad.process(myRow));
    }
}
