package uk.ac.ebi.interpro.scan.io;

import org.springframework.core.io.Resource;

import java.util.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Default implementation.
 *
 * @author  Antony Quinn
 * @version $Id$
 */
public abstract class AbstractResourceReader<T> implements ResourceReader<T> {

    @Override public Collection<T> read(Resource resource) throws IOException {
        if (resource == null) {
            throw new NullPointerException("Resource is null");
        }
        if (!resource.exists())  {
            throw new IllegalStateException(resource.getFilename() + " does not exist");
        }
        if (!resource.isReadable())  {
            throw new IllegalStateException(resource.getFilename() + " is not readable");
        }
        final List<T> records = new ArrayList<T>();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            while (reader.ready()) {
                T record = createRecord(reader.readLine());
                if (record != null) {
                    records.add(record);
                }
            }
        }
        finally {
            if (reader != null){
                reader.close();
            }
        }
        return Collections.unmodifiableList(records);
    }
    
    protected abstract T createRecord(String line);

}