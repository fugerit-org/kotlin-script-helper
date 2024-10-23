package test.org.fugerit.java.kts.helper;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.kts.helper.EvalKts;
import org.fugerit.java.kts.helper.EvalKtsWithJsonDataModel;
import org.fugerit.java.kts.helper.ScriptException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Slf4j
class TestEvalKts {

    private EvalKts evalKts = new EvalKtsWithJsonDataModel();

    @Test
    void testEvalkts() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(ClassHelper.loadFromDefaultClassLoader( "kts/sample-1.kts" ))) {
            Object result = evalKts.evalKts( reader );
            String xml = result.toString();
            log.info( "xml : \n{}", xml );
            Assertions.assertNotNull( xml );
        }
    }

    @Test
    void testEvalktsWithDataModel() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(ClassHelper.loadFromDefaultClassLoader( "kts/sample-1-with-data-model.kts" ))) {
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put( "docTitle", "My custom title" );
            Object result = evalKts.evalKts( reader, dataModel );
            String xml = result.toString();
            log.info( "xml from data model : \n{}", xml );
            Assertions.assertNotNull( xml );
        }
    }

    @Test
    void testEvalktsWithException() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(ClassHelper.loadFromDefaultClassLoader( "kts/sample-1-with-data-model.kts" ))) {
            Assertions.assertThrows( ScriptException.class, () -> evalKts.evalKtsEx( reader ) ) ;
        }
    }

    @Test
    void testScriptException() {
        Assertions.assertNotNull( new ScriptException() );
        Assertions.assertNotNull( new ScriptException( new IOException() ) );
        Assertions.assertNotNull( new ScriptException( "test 1" ) );
        Assertions.assertNotNull( new ScriptException( "test 2", new ConfigException()) );
    }

}
