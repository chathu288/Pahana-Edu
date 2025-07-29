/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.student.service.resources;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import utils.Customer;
import utils.Utils;
/**
 *
 * @author ASUS TUF
 */

@Path("customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResources {
    
    @GET
    public Response getCustomers() {
        Utils utils = new Utils();
        return Response.ok(new Gson().toJson(utils.getCustomers())).build();
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") int id) {
        Utils utils = new Utils();
        Customer customer = utils.getCustomerById(id);
        if (customer != null) {
            return Response.ok(new Gson().toJson(customer)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found\"}")
                    .build();
        }
    }

    @POST
    public Response createCustomer(String customerJson) {
        Utils utils = new Utils();
        Customer customer = new Gson().fromJson(customerJson, Customer.class);
        boolean created = utils.createCustomer(customer);
        if (created) {
            return Response.status(Response.Status.CREATED)
                    .entity(new Gson().toJson(customer))
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Customer could not be created\"}")
                    .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response updateCustomer(@PathParam("id") int id, String customerJson) {
        Utils utils = new Utils();
        Customer customer = new Gson().fromJson(customerJson, Customer.class);
        customer.setId(id);
        boolean updated = utils.updateCustomer(customer);
        if (updated) {
            return Response.ok(new Gson().toJson(customer)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found\"}")
                    .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Utils utils = new Utils();
        boolean deleted = utils.deleteCustomer(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Customer not found\"}")
                    .build();
        }
    }
}
