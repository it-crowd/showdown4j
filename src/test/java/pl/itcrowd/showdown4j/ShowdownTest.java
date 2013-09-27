package pl.itcrowd.showdown4j;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ShowdownTest {

    private Showdown showdown;

    @Before
    public void setUp() throws Exception
    {
        showdown = new Showdown(getClass().getResourceAsStream("/pl/itcrowd/showdown4j/showdown.js"));
    }

    @Test
    public void consecutiveInvocations() throws Exception
    {
//        Given

//        When
        final String resultA = showdown.makeHtml("#abc");
        final String resultB = showdown.makeHtml("##def");

//        Then
        assertEquals("<h1 id=\"abc\">abc</h1>", resultA);
        assertEquals("<h2 id=\"def\">def</h2>", resultB);
    }

    @Test
    public void defaultConstructor() throws Exception
    {
//        Given
        final Showdown showdown = new Showdown();

//        When
        final String result = showdown.makeHtml("#abc");

//        Then
        assertEquals("<h1 id=\"abc\">abc</h1>", result);
    }

    @Test
    public void doubleBackslash() throws Exception
    {
//        Given

//        When
        final String result = showdown.makeHtml("\\\\");

//        Then
        assertEquals("<p>\\</p>", result);
    }

    @Test
    public void doubleQuote() throws Exception
    {
//        Given

//        When
        final String result = showdown.makeHtml("\"");

//        Then
        assertEquals("<p>\"</p>", result);
    }

    @Test
    public void escapedSingleQuote() throws Exception
    {
//        Given

//        When
        final String result = showdown.makeHtml("\\'");

//        Then
        assertEquals("<p>\\'</p>", result);
    }

    @Test
    public void headerLevelStart() throws Exception
    {
//        Given

//        When
        final String result = showdown.makeHtml("#hello", 3);

//        Then
        assertEquals("<h3 id=\"hello\">hello</h3>", result);
    }

    @Test
    public void newLines() throws IOException
    {
//        Given
        final String markdownSource = "#PRE-CONDITIONS\n" + "+ User is authenticated as buyer.\n" + "+ User is in \"buy credits\" page.\n" + "#SCENARIO\n"
            + "+ User enters value into \"Credits quantity to buy\" input, then clicks \"Pay with Paypal\" button.\n" + "#POST-CONDITIONS\n"
            + "+ User is redirected to PayPal service.";

        final String expectedResult = "<h1 id=\"preconditions\">PRE-CONDITIONS</h1>\n" + "\n" + "<ul>\n" + "<li>User is authenticated as buyer.</li>\n"
            + "<li>User is in \"buy credits\" page.</li>\n" + "</ul>\n" + "\n" + "<h1 id=\"scenario\">SCENARIO</h1>\n" + "\n" + "<ul>\n"
            + "<li>User enters value into \"Credits quantity to buy\" input, then clicks \"Pay with Paypal\" button.</li>\n" + "</ul>\n" + "\n"
            + "<h1 id=\"postconditions\">POST-CONDITIONS</h1>\n" + "\n" + "<ul>\n" + "<li>User is redirected to PayPal service.</li>\n" + "</ul>";

//        When
        final String result = showdown.makeHtml(markdownSource);

//        Then
        assertEquals(expectedResult, result);
    }

    @Test
    public void plugin() throws Exception
    {
//        Given
        showdown.addPlugin("image", getClass().getResourceAsStream("/pl/itcrowd/showdown4j/showdown.image.js"));

//        When
        final String result = showdown.makeHtml("((123))");

//        Then
        assertEquals("<p><img src=\"/api/screen/data/123\"/></p>", result);
    }

    @Test
    public void singleBackslash() throws Exception
    {
//        Given

//        When
        final String result = showdown.makeHtml("\\");

//        Then
        assertEquals("<p>\\</p>", result);
    }

    @Test
    public void singleQuote() throws Exception
    {
//        Given

//        When
        final String result = showdown.makeHtml("'");

//        Then
        assertEquals("<p>'</p>", result);
    }
}
