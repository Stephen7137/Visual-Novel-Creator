package cpp.VNCreator.Model;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javafx.scene.paint.Color;

public class ColorParser implements JsonSerializer<Color>, JsonDeserializer<Color>{

	@Override
	public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
        JsonObject colorSave = json.getAsJsonObject();
        Color color = new Color(colorSave.get("red").getAsDouble(), colorSave.get("green").getAsDouble(), colorSave.get("blue").getAsDouble(), colorSave.get("opacity").getAsDouble());
        return color;
	}

	@Override
	public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject colorSave = new JsonObject();
        colorSave.addProperty("red", src.getRed());
        colorSave.addProperty("green", src.getGreen());
        colorSave.addProperty("blue", src.getBlue());
        colorSave.addProperty("opacity", src.getOpacity());
        return context.serialize(colorSave);
	}
}

