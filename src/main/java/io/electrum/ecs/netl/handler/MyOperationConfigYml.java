package io.electrum.ecs.netl.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.electrum.undercoat.configuration.definition.annotation.ConfigurationDefinition;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@ConfigurationDefinition(name = "MyOperationConfigYml")
public class MyOperationConfigYml extends OperationConfigYml{

    @JsonProperty
    @NotNull
    private String searchTerm;

    public String getSearchTerm() {return searchTerm;}

    @Override
    public boolean equals(Object o) {
        super.equals(o);
        MyOperationConfigYml that = (MyOperationConfigYml) o;
        return Objects.equals(getSearchTerm(), that.getSearchTerm());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getSearchTerm());
    }

    @Override
    public String toString() {
        return "MyOperationConfigYml{" + "searchTerms='" + searchTerm +super.toString();
    }
}
