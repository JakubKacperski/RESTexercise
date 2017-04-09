package jakub.com.rest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Date;



@Path("/")


public class MyRest {
    @GET
    @Path("getStartingPage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStartingPage() throws SQLException, NamingException {
        String output = "<h1>Hello World!<h1>" +
                "<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p<br>";

        Context ctx = new InitialContext();

        DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/sakila");

        Connection conn = dataSource.getConnection();

        StringBuilder msg = new StringBuilder();

        try (Statement stm = conn.createStatement()) {

            String query = "show tables;";

            ResultSet rs = stm.executeQuery(query);

            // Store and return result of the query

            while (rs.next()) {

                msg.append(rs.getString(1));


            }

        } catch (SQLException e) {

            System.err.println(e.getMessage());

        } finally {

            // Release connection back to the pool

            if (conn != null) {

                conn.close();

            }

            conn = null; // prevent any future access

        }

        return Response.status(200).entity(msg).build();


    }


    @GET
    @Path("getMyName")
    @Produces("text/html")
    public Response getMyName()
    {
        String output = "<marquee>Kuba</marquee>";
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("getJson")
    @Produces(MediaType.APPLICATION_JSON)

    public Response getJson(@MatrixParam("author") String author,
                            @MatrixParam("country") String country)
    {
        Car newCar = new Car();
        newCar.setName("xxx");
        newCar.setLength(5);
        newCar.setWeigth(700);

        return Response.status(200).entity(newCar).header("dd", "dd").build();
    }

    @GET
    @Path("city/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response city(@PathParam("id") Integer id) throws SQLException, NamingException {

        City city = new City();



        Context ctx = new InitialContext();

        DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/sakila");

        Connection conn = dataSource.getConnection();

        StringBuilder msg = new StringBuilder();

        PreparedStatement stm = null;

        String query = "select city from city where city_id = ?";


        try  {

            stm = conn.prepareStatement(query);



            stm.setInt(1, id);


            ResultSet rs = stm.executeQuery();

            // Store and return result of the query

            while (rs.next()) {

                String cityTemp = rs.getString("city");
                city.setName(cityTemp);


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

        return Response.status(200).entity(city).build();


    }


//
//    @PUT
//    @Path("testJson")
//
//    @Consumes(MediaType.APPLICATION_JSON)
//
//    public void postJson()
//    {
//
////        return Response.status(200).entity(fiat.getName()).build();
//    }
//
@POST
@Path("post")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response createTrackInJSON(Car person) {

    String result = "Track saved : " + person;
    return Response.status(201).entity(person).build();

}






}