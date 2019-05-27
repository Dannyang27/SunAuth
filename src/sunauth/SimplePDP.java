package src.sunauth;

import com.sun.xacml.ParsingException;
import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;

import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;

import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;

import com.sun.xacml.finder.impl.CurrentEnvModule;
import com.sun.xacml.finder.impl.FilePolicyModule;
import com.sun.xacml.finder.impl.SelectorModule;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimplePDP
{
    private PDP pdp = null;

    public SimplePDP(String [] policyFiles){

        FilePolicyModule filePolicyModule = new FilePolicyModule();

        for (int i = 0; i < policyFiles.length; i++) {
            filePolicyModule.addPolicy(policyFiles[i]);
        }

        PolicyFinder policyFinder = new PolicyFinder();
        Set policyModules = new HashSet();
        policyModules.add(filePolicyModule);
        policyFinder.setModules(policyModules);
        
        CurrentEnvModule envAttributeModule = new CurrentEnvModule();
        SelectorModule selectorAttributeModule = new SelectorModule();

        AttributeFinder attributeFinder = new AttributeFinder();
        List attributeModules = new ArrayList();
        attributeModules.add(envAttributeModule);
        attributeModules.add(selectorAttributeModule);
        attributeFinder.setModules(attributeModules);
        
        pdp = new PDP(new PDPConfig(attributeFinder, policyFinder, null));
    }

    public ResponseCtx evaluate(String requestFile) throws IOException, ParsingException {
        RequestCtx request = RequestCtx.getInstance(new FileInputStream(requestFile));
        return pdp.evaluate(request);
    }
}
