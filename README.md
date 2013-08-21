showdown4j
==========

Showdown.js for Java

There are several markdown libraries for Java, but we needed 100% exact output in our backend (Java)
as Showdown.js which we use at the frontend.

This tool simply starts [Rhino](https://github.com/mozilla/rhino) (JavaScript engine for Java),
loads Showdown.js and passes your markdown content to it.

##Use default showdown.js shipped with this library

```
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
```

##Use your own version of showdown.js

```
    @Test
    public void yourOwnShowdownJS() throws Exception
    {
//        Given
        final Showdown showdown = new new Showdown(getClass().getResourceAsStream("/pl/itcrowd/showdown4j/showdown.js"));

//        When
        final String result = showdown.makeHtml("#abc");

//        Then
        assertEquals("<h1 id=\"abc\">abc</h1>", result);
    }
```
