package io.electrum.ecs.netl.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.electrum.undercoat.configuration.definition.annotation.ConfigurationDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@ConfigurationDefinition(name = "MyOperationConfigYml")
public class MyOperationConfigYml extends OperationConfigYml{

    @JsonProperty
    private List<String> json_payload_paths;

    @JsonProperty
    private List<String> extended_data_paths;
    @JsonProperty
    private List<String> json_http_msg_paths;

    // filters array
    private HashMap<String,String> filtersOfStringType;
    private HashMap<String, Integer> filtersOfIntegerType;

    // filtering string fields
    @JsonProperty
    private int connId;
    @JsonProperty
    private String jsonPayloadClass;
    @JsonProperty
    private String inletCogId;
    @JsonProperty
    private String cogId;
    @JsonProperty
    private String queueId;
    @JsonProperty
    private String serviceType;
    @JsonProperty
    private String serviceMsgType;
    @JsonProperty
    private int operation;
    @JsonProperty
    private int deliveryMethod;

    public int getConnId() {
        return connId;
    }

    public String getJsonPayloadClass() {
        return jsonPayloadClass;
    }

    public String getInletCogId() {
        return inletCogId;
    }

    public String getCogId() {
        return cogId;
    }

    public String getQueueId() {
        return queueId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceMsgType() {
        return serviceMsgType;
    }

    public int getOperation() {
        return operation;
    }

    public int getDeliveryMethod() {
        return deliveryMethod;
    }

    public List<String> getJson_payload_paths() {return json_payload_paths;}

    public List<String> getExtended_data_paths() {
        return extended_data_paths;
    }

    public List<String> getJson_http_msg_paths() {
        return json_http_msg_paths;
    }

    // generating a list of filters with non-Null values or defaults of 0 values
    public HashMap<String,String> generateFiltersOfStringType() {
        filtersOfStringType = new HashMap<>();
        if(jsonPayloadClass != null) filtersOfStringType.put("jsonPayloadClass", jsonPayloadClass);
        else if (inletCogId != null) filtersOfStringType.put("inletCogId", inletCogId);
        else if (cogId != null) filtersOfStringType.put("cogId", cogId);
        else if (queueId != null) filtersOfStringType.put("queueId", queueId);
        else if (serviceType != null) filtersOfStringType.put("serviceType", serviceType);
        else if (serviceMsgType != null) filtersOfStringType.put("serviceMsgType", serviceMsgType);

        return filtersOfStringType;
    }

    public HashMap<String, Integer> generateFiltersOfIntegerType() {
        filtersOfIntegerType = new HashMap<>();
        if(connId != 0) filtersOfIntegerType.put("connId", connId);
        else if(operation != 0) filtersOfIntegerType.put("operation", operation);
        else if(deliveryMethod != 0) filtersOfIntegerType.put("deliveryMethod", deliveryMethod);

        return filtersOfIntegerType;
    }

    @Override
    public boolean equals(Object o) {
        super.equals(o);
        MyOperationConfigYml that = (MyOperationConfigYml) o;
        return Objects.equals(getJson_payload_paths(), that.getJson_payload_paths());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getJson_payload_paths());
    }

    @Override
    public String toString() {
        return "MyOperationConfigYml{" + "searchTerms='" + json_payload_paths.toString() + super.toString();
    }
}
