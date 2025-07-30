/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.student.service.resources;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import utils.Bill;
import utils.Utils;
import java.util.Date;
/**
 *
 * @author ASUS TUF
 */
@Path("bills")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillResources {
    private final Utils utils = new Utils();
    private final Gson gson = new Gson();

    @POST
    public Response createBill(String billJson) {
        Bill bill = gson.fromJson(billJson, Bill.class);
        // Set bill date if not provided
        if (bill.getBillDate() == null) {
            bill.setBillDate(new Date());
        }
        boolean created = utils.createBill(bill);
        if (created) {
            return Response.status(Response.Status.CREATED)
                    .entity(gson.toJson(bill))
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Bill could not be created\"}")
                    .build();
        }
    }
}
