package io.electrum.ecs.netl.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.electrum.undercoat.configuration.definition.annotation.ConfigurationDefinition;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@ConfigurationDefinition(name = "MyOperationConfigYml")
public class MyOperationConfigYml extends OperationConfigYml{

    @JsonProperty
    @NotNull
    private String query;

    public String getQuery() {return query;}

    @Override
    public boolean equals(Object o) {
        super.equals(o);
        MyOperationConfigYml that = (MyOperationConfigYml) o;
        return Objects.equals(getQuery(), that.getQuery());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getQuery());
    }

    @Override
    public String toString() {
        return "MyOperationConfigYml{" + "searchTerms='" + query +super.toString();
    }
}
