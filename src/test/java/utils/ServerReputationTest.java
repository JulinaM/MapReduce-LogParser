package utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by julinamaharjan@gmail.com on 12/21/16.
 */
public class ServerReputationTest {

    @Test()
    public void test(){
        ServerReputation serverReputation = new ServerReputation();
        serverReputation.checkReputation("www.ianfette.org");
        Assert.assertFalse(serverReputation.isBlackList());

        serverReputation.checkReputation("malware.wicar.org");
        Assert.assertTrue(serverReputation.isBlackList());
    }
}
