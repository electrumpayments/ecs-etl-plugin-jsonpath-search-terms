package test.java;

import io.electrum.ecs.dao.transfer.TranLegReq;
import io.electrum.ecs.netl.handler.AdditionalSearchTermsOperation;
import io.electrum.ecs.netl.handler.Row;
import org.testng.Assert;
import org.testng.annotations.Test;


public class MyTest {

    @Test
    public void testAdditionalSearchTerms() throws Exception {

        AdditionalSearchTermsOperation  ad = new AdditionalSearchTermsOperation(null,  null);

        TranLegReq tranLegReq  = new TranLegReq();
        //TranLegReq tranLegReq1= new TranLegReq(); // This second tranLegReq is just for  reference to check if indeed "Wavhudi" was added to  the search terms
        Row myRow  = new Row(tranLegReq);
        int index = tranLegReq.toString().indexOf("searchTerms");
        String beforeAdditionOfSearchTerms = tranLegReq.toString().substring(0, index);
        // Testing if both tranLegReq contains the  same data before adding additional search terms
        //ssert.assertTrue(tranLegReq1.equals(tranLegReq));
        Assert.assertFalse(ad.process(myRow));
        //System.out.println(tranLegReq);
        String afterAdditionOfSearchTerms = tranLegReq.toString().substring(0, index);
        // Testing if "Wavhudi" was added to  the search terms list.
        // 1. Add “Wavhudi” to the set of search terms for a TranLegReq
        Assert.assertTrue(tranLegReq.getSearchTerms().contains("Wavhudi"));

        //  2. Preserve all data that was in the TranLegReq that was in it before your operation processes it, including
        //  any search terms that were already in the TranLegReq
        Assert.assertEquals(beforeAdditionOfSearchTerms, afterAdditionOfSearchTerms);

    }
}
