package me.gensh.core.response.render;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import me.gensh.core.Config;

import java.io.*;

/**
 * Created by gensh on 2016/10/26.
 */
public class DefaultHtmlRenderInstance extends HtmlRender {
    //if we use layout

    public DefaultHtmlRenderInstance(String template, Object data, BufferedOutputStream bos) {
        super(template, data, bos);
    }

    private final static Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

    public static void initConfig() {
          /* ------------------------------------------------------------------------ */
        /* You should do this ONLY ONCE in the whole application life-cycle:        */

        /* Create and adjust the configuration singleton */
        try {
            cfg.setDirectoryForTemplateLoading(new File(Config.BasePath + Config.View.VIEW));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* ------------------------------------------------------------------------ */
        /* You usually do these for MULTIPLE TIMES in the application life-cycle:   */
    }

    @Override
    public void render() {
         /* Merge data-model with template */
        try {
            Template temp = cfg.getTemplate(template);
            Writer out = new OutputStreamWriter(bos);
            temp.process(data, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
