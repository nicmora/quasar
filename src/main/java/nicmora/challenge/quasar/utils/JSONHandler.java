package nicmora.challenge.quasar.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nicmora.challenge.quasar.exception.JSONHandlerException;

import java.util.Optional;

public class JSONHandler {

    private final ObjectMapper mapper;

    /*
    	Bill Pugh Singleton Implementation
     */

    public static JSONHandler getInstance(){
        return SingletonHelper.INSTANCE;
    }

    private static class SingletonHelper {
        private static final JSONHandler INSTANCE = new JSONHandler();
    }

    private JSONHandler() {
        this.mapper = new ObjectMapper();
    }

    public <T> String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JSONHandlerException("Could not parse object of type " + object.getClass().getName() + " to json");
        }
    }

}
