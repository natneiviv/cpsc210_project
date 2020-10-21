package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.ArrivalsParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test the ArrivalsParser
 */
public class ArrivalsParserTest {
    @Before
    public void setup() {

    }

    @Test
    public void testArrivalsParserNormal() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivals43.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        ArrivalsParser.parseArrivals(s, data);
        int count = 0;
        for (Arrival a : s) {
            assertTrue(a.getTimeToStopInMins() <= 120);
            count++;
        }
        assertEquals(6, count);
    }

    @Test(expected = ArrivalsDataMissingException.class)
    public void testArrivalsMissingExpectedCountdown() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivalsMissingCountdown.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        ArrivalsParser.parseArrivals(s, data);
    }

    @Test(expected = ArrivalsDataMissingException.class)
    public void testArrivalsMissingDestination() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivalsMissingDest.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        ArrivalsParser.parseArrivals(s, data);
    }


    @Test(expected = ArrivalsDataMissingException.class)
    public void testArrivalsMissingSchedule() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivalsMissingSchedule.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        ArrivalsParser.parseArrivals(s, data);
    }


    @Test(expected = JSONException.class)
    public void testArrivalsIncorrectFormat() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivalsIncorrectFormat.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        ArrivalsParser.parseArrivals(s, data);
    }

    @Test
    public void testAddingCorrectly() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        int count = 0;
        try {
            String data = new FileDataProvider("arrivalsMissingSchedule.json").dataSourceToString();
            ArrivalsParser.parseArrivals(s, data);
        } catch (IOException e) {
            throw new RuntimeException("Can't read the arrivals data");
        } catch (JSONException j) {
            fail("JSON Exception thrown / Should not be thrown.");
        } catch (ArrivalsDataMissingException a) {
            // do nothing
        }
        for (Arrival a : s) {
            count++;

            assertEquals(5, count);
        }
    }

    @Test
    public void testAddingStatus() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        int count = 0;
        try {
            String data = new FileDataProvider("arrivalsMissingSchedule.json").dataSourceToString();
            ArrivalsParser.parseArrivals(s, data);
        } catch (IOException e) {
            throw new RuntimeException("Can't read the arrivals data");
        } catch (JSONException j) {
            fail("JSON Exception thrown / Should not be thrown.");
        } catch (ArrivalsDataMissingException a) {
            // do nothing
        }
        for (Arrival a : s) {
            if (a.getStatus().equals(" "))
                count++;

            assertEquals(3, count);
        }
    }

    @Test
    public void testAddingDest() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        int count = 0;
        try {
            String data = new FileDataProvider("arrivalsMissingCountdown.json").dataSourceToString();
            ArrivalsParser.parseArrivals(s, data);
        } catch (IOException e) {
            throw new RuntimeException("Can't read the arrivals data");
        } catch (JSONException j) {
            fail("JSON Exception thrown / Should not be thrown.");
        } catch (ArrivalsDataMissingException a) {
            // do nothing
        }
        for (Arrival a : s) {
            if (a.getDestination().equals("UBC"))
                count++;
        }
        assertEquals(2, count);
        }
/*
    @Test
    public void testFirstArrival() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        try {
            String data = new FileDataProvider("arrivalsMissingCountdown.json").dataSourceToString();
            ArrivalsParser.parseArrivals(s, data);
        } catch (IOException e) {
            throw new RuntimeException("Can't read the arrivals data");
        } catch (JSONException j) {
            fail("JSON Exception thrown / Should not be thrown.");
        } catch (ArrivalsDataMissingException a) {
            // do nothing
        }
        for (Arrival a : s){

        }
        }
        */
    }
