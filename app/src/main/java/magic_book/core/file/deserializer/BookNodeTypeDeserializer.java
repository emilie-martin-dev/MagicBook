package magic_book.core.file.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import magic_book.core.node.BookNodeType;


public class BookNodeTypeDeserializer implements JsonDeserializer<BookNodeType> {

	@Override
	public BookNodeType deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
		for(BookNodeType nodeType : BookNodeType.values()) {					
			if(nodeType.name().equals(je.getAsString())) {
				return nodeType;
			}
		}

		return null;

	}
}
