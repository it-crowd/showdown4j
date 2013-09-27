package pl.itcrowd.showdown4j;

import org.apache.commons.io.IOUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Showdown {

    private transient Context cx;

    private Map<String, String> pluginSources = new HashMap<String, String>();

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

    public void addPlugin(String name, InputStream source) throws IOException
    {
        this.pluginSources.put(name, IOUtils.toString(source));
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
            for (String source : pluginSources.values()) {
                getContext().evaluateString(scope, source, "<cmd>", 1, null);
            }
            final StringBuilder extensions = new StringBuilder();
            for (String name : pluginSources.keySet()) {
                extensions.append("'").append(name).append("',");
            }
            final String extensionsString = extensions.length() > 0 ? extensions.substring(0, extensions.length() - 1) : extensions.toString();
            getContext().evaluateString(scope, "var showdown = new Showdown.converter({extensions:[" + extensionsString + "]});", "<cmd>", 1, null);
        }
        return scope;
    }
}
