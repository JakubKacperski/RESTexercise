package jakub.com.rest;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.Date;

@Path("customers/")
public class CustomerRest {

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") Integer id) throws SQLException, NamingException {


        Customer customer = new Customer();

        Context ctx = new InitialContext();

        DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/sakila");

        Connection conn = dataSource.getConnection();

        StringBuilder msg = new StringBuilder();

        PreparedStatement stm = null;

        String query = "select * from customer2 where Id = ?";


        try {

            stm = conn.prepareStatement(query);


            stm.setInt(1, id);


            ResultSet rs = stm.executeQuery();

            // Store and return result of the query

            if (rs.next()) {

                String customerFirstName = rs.getString("FirstName");
                customer.setFirstName(customerFirstName);

                int customerId = rs.getInt("Id");
                customer.setId(customerId);

                String customerAddress = rs.getString("Address");
                customer.setAddress(customerAddress);


            } else {

                return Response.status(404).build();

            }

        } catch (SQLException e) {

            System.err.println(e.getMessage());

        } finally {

            // Release connection back to the pool

            if (conn != null) {

                conn.close();
                stm.close();


            }

            conn = null; // prevent any future access

        }

        return Response.status(200).entity(customer).build();


    }



    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCustomer(Customer customer) throws SQLException, NamingException  {



        Context ctx = new InitialContext();

        DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/sakila");

        Connection conn = dataSource.getConnection();

        StringBuilder msg = new StringBuilder();

        PreparedStatement stm = null;

        String query = "insert into customer2(FirstName, Address)" +
                "values (?,?);";


        try {

            stm = conn.prepareStatement(query, stm.RETURN_GENERATED_KEYS);


            stm.setString(1, customer.getFirstName());
            stm.setString(2, customer.getAddress());


            stm.executeUpdate();



            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }



            // Store and return result of the query



        } catch (SQLException e) {

            System.err.println(e.getMessage());

        } finally {

            // Release connection back to the pool

            if (conn != null) {

                conn.close();
                stm.close();


            }

            conn = null; // prevent any future access

        }


        return Response.status(201).entity(customer).build();

    }


    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putCustomer(@PathParam("id") Integer id, Customer customer) throws SQLException, NamingException {


        Response.class.get




        Context ctx = new InitialContext();

        DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/sakila");

        Connection conn = dataSource.getConnection();

        StringBuilder msg = new StringBuilder();

        PreparedStatement stm = null;

        String query = "update customer2 set FirstName=?, Address=? where Id=?";


        try {

            stm = conn.prepareStatement(query);


            stm.setInt(3, id);
            stm.setString(2, customer.getAddress());
            stm.setString(1, customer.getFirstName());


           stm.executeUpdate();

            // Store and return result of the query


        } catch (SQLException e) {

            System.err.println(e.getMessage());

        } finally {

            // Release connection back to the pool

            if (conn != null) {

                conn.close();
                stm.close();


            }

            conn = null; // prevent any future access

        }

        customer.setId(id);

        return Response.status(200).entity(customer).build();


    }


    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)

    public Response putCustomer(@PathParam("id") Integer id) throws SQLException, NamingException {




        Context ctx = new InitialContext();

        DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/sakila");

        Connection conn = dataSource.getConnection();

        StringBuilder msg = new StringBuilder();

        PreparedStatement stm = null;

        String query = "delete from customer2 where Id=?;";


        try {

            stm = conn.prepareStatement(query);


            stm.setInt(1, id);



            stm.executeUpdate();

            // Store and return result of the query


        } catch (SQLException e) {

            System.err.println(e.getMessage());

        } finally {

            // Release connection back to the pool

            if (conn != null) {

                conn.close();
                stm.close();


            }

            conn = null; // prevent any future access

        }



        return Response.status(200).build();


    }
}





