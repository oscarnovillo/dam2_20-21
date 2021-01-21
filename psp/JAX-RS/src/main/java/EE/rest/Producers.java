package EE.rest;

import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class Producers {


    @Produces
    public ModelMapper producesModelMapper()
    {
        return new ModelMapper();
    }

    @Produces
    public Jsonb producesJsonb()
    {
        return JsonbBuilder.create();
    }
}
