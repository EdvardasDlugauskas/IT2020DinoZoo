package lt.vu.rest;

import lombok.extern.java.Log;
import lt.vu.decorators.DecoratedClass;
import lt.vu.entities.Enclosure;
import lt.vu.interceptors.LoggingInterceptor;
import lt.vu.persistence.AsyncReady;
import lt.vu.rest.testComponents.ComponentFromAlternatives;
import lt.vu.rest.testComponents.ComponentWithSpecializes;

import javax.ejb.Asynchronous;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.concurrent.*;

@Log
@ApplicationScoped
@Path("/lock")
@Produces(MediaType.APPLICATION_JSON)
@LoggingInterceptor
public class LockController {
    @Inject
    OptimisticLockHelper helper;

    @Inject
    AsyncComponent asyncComponent;


    @Path("/a")
    @GET
    @Transactional
    public Response lockA() {
        final String newName = "TestEnclosure" + ThreadLocalRandom.current().nextInt();
        try {
            helper.ChangeTestEnclosure(newName);
        }
        catch (OptimisticLockException e) {
            try {
                helper.ChangeTestEnclosure(newName);
            }
            catch (OptimisticLockException e2) {
                log.severe("OPTIMISTIC LOCK EXCEPTION, RETRYING");
                helper.ChangeTestEnclosure(newName);
                return Response.status(Response.Status.CONFLICT).build();
            }

        }
        return Response.ok(new Object()).build();
    }

    @Path("/async")
    @GET
    @Transactional
    public Response asyncOperation() throws InterruptedException, ExecutionException, TimeoutException {
        var futureResult = asyncComponent.AsyncMethod();

        var result = futureResult.get(10, TimeUnit.SECONDS);

        return Response.ok(result).build();
    }

    @Inject
    ComponentWithSpecializes componentWithSpecializes;
    @Inject
    ComponentFromAlternatives componentFromAlternatives;

    @Inject
    DecoratedClass decoratedClass;

    @Path("/spec-alt")
    @GET
    public Response getSpecializedAndAlternative() {
        return Response.ok(
                new Object() {
                    public final String specializesName = componentWithSpecializes.getClass().toString();
                    public final String alternativeName = componentFromAlternatives.getClass().toString();
                    public final String decoratedClassName = decoratedClass.getName();
                }
        ).build();
    }
}

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRES_NEW)
class OptimisticLockHelper implements Serializable {
    @Inject
    EntityManager em;

    public void ChangeTestEnclosure(String name) {
        final Enclosure testEnclosure = em.find(Enclosure.class, 2);

        testEnclosure.setName(name);

        try {
            Thread.sleep(3 * 1000);
        } catch(Exception ignored) {}

        em.persist(testEnclosure);
        em.flush();
    }
}

@Transactional
@TransactionScoped
class AsyncComponent implements Serializable {

    @Inject
    @AsyncReady
    EntityManager em;

    @Asynchronous
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Future<Enclosure> AsyncMethod() {
        try {
            Thread.sleep(1000);
        } catch(Exception ignored) {};

        var enclosure = em.find(Enclosure.class, 2);
        return CompletableFuture.completedFuture(enclosure);
    }
}

