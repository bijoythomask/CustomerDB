/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.customer.service;

import com.rest.customer.Customer;
import com.rest.customer.DiscountCode;
import com.rest.customer.MicroMarket;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author bijoy
 */
@Stateless
@Path("com.rest.customer.customer")
public class CustomerFacadeREST extends AbstractFacade<Customer> {
    @PersistenceContext(unitName = "CustomerDBPU")
    private EntityManager em;
    
    @EJB
    DiscountCodeFacadeREST discountCodeFacade;
    
    @EJB
    MicroMarketFacadeREST microMarketFacade;
    
    private static Integer custid=1000;

    public CustomerFacadeREST() {
        super(Customer.class);
    }
    
    
    @POST
    @Path("DiscountCode/{id}")
    @Consumes({"application/xml", "application/json"})
    public void create(@PathParam("id") String id, Customer entity ,@QueryParam("zip") String zipCode) {
        DiscountCode discountCode = discountCodeFacade.find(id);
        
        MicroMarket mMarket = microMarketFacade.find(zipCode);
        entity.setCustomerId(custid++);
        entity.setDiscountCode(discountCode);
        entity.setZip(mMarket);
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Customer entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}/DiscountCode/")
    @Produces({"application/xml", "application/json"})
    public Customer find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Path("DiscountCode")
    @Produces({"application/json"})
    public List<Customer> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Customer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
