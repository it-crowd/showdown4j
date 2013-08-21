package pl.itcrowd.showdown4j;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import java.io.IOException;
import java.io.InputStream;

public class Showdown {

    private transient Context cx;

    private transient ScriptableObject scope;

    private String showdownSource;

    public Showdown() throws IOException
    {
        this(Showdown.class.getResourceAsStream("/pl/itcrowd/showdown4j/showdown.js"));
    }

    public Showdown(InputStream showdownSource) throws IOException
    {
        this.showdownSource = IOUtils.toString(showdownSource);
    }

    public String makeHtml(String markdownSource)
    {
        return makeHtml(markdownSource, 1);
    }

    public String makeHtml(String markdownSource, int headerLevelStart)
    {
        ScriptableObject.putProperty(getScope(), "markdownSource", markdownSource);
        final Object result = getContext().evaluateString(getScope(), "showdown.makeHtml(markdownSource," + headerLevelStart + ")", "<cmd>", 1, null);
        return null == result ? null : result.toString();
    }

    @Override
    protected void finalize() throws Throwable
    {
        cx = null;
        scope = null;
        super.finalize();
        Context.exit();
    }

    private Context getContext()
    {
        if (null == cx) {
            cx = Context.enter();
            cx.setLanguageVersion(Context.VERSION_1_7);
        }
        return cx;
    }

    private ScriptableObject getScope()
    {
        if (null == scope) {
            scope = getContext().initStandardObjects();
            getContext().evaluateString(scope, showdownSource, "<cmd>", 1, null);
            getContext().evaluateString(scope, "var showdown = new Showdown.converter();", "<cmd>", 1, null);
        }
        return scope;
    }
}
