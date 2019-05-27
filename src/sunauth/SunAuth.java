package src.sunauth;

import com.sun.xacml.Indenter;
import com.sun.xacml.ctx.ResponseCtx;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class SunAuth {

    private static final String pathToPolicyFile = "src/resources/policy/XACMLPolicy";
    private static final String pathToRequestFile = "src/resources/request/XACMLRequest";
    private static final String pathToSaveFile = "src/resources/outputs/XACML";

    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione la Policy [1,3]");
        int policyNumber = scanner.nextInt();

        if( 0 < policyNumber && policyNumber < 4){
            String [] pathPolicies = new String[1];
            pathPolicies[0] = pathToPolicyFile + policyNumber + ".xml";

            System.out.println("Seleccione el Request deseado [1,5]");
            int requestNumber = scanner.nextInt();

            if( 0 < requestNumber && requestNumber < 6){
                SimplePDP simplePDP = new SimplePDP(pathPolicies);
                String requestFile = pathToRequestFile + requestNumber + ".xml";
                ResponseCtx response = simplePDP.evaluate(requestFile);

                String pathToSave = pathToSaveFile + "Policy" + policyNumber + "Request" + requestNumber + ".xml";
                OutputStream outputStream = new FileOutputStream(pathToSave);
                Utils.printResult(outputStream, new Indenter(), response.getResults());
            }
        }
    }
}
