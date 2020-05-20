package lt.vu.rest;


import lt.vu.entities.Enclosure;
import lt.vu.persistence.EnclosuresDao;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/enclosures")
@Produces(MediaType.APPLICATION_JSON)
public class EnclosureController {

    @Inject
    EnclosuresDao enclosuresDao;

    @GET
    @Path("/{enclosureId}")
    public Response get(@PathParam("enclosureId") int id) {
        return Response.ok(enclosuresDao.findOne(id)).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Enclosure e) {
        enclosuresDao.persist(e);
        return Response.ok(e).build();
    }

    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Enclosure e) {
        var EnclosureToUpdate = enclosuresDao.findOne(e.getId());

        EnclosureToUpdate.setName(e.getName());

        enclosuresDao.persist(EnclosureToUpdate);
        return Response.ok(e).build();
    }

    @DELETE
    @Transactional
    @Path("/{enclosureId}")
    public Response delete(@PathParam("enclosureId") int id) {
        enclosuresDao.remove(enclosuresDao.findOne(id));
        return Response.ok().build();
    }
}
