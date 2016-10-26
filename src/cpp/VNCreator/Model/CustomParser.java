package cpp.VNCreator.Model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import cpp.VNCreator.Controller.SaveProject.SaveNode;
import cpp.VNCreator.Controller.SaveProject.SaveOption;
import cpp.VNCreator.Controller.SaveProject.SaveText;
import cpp.VNCreator.Model.NodeType.nodeType;

public class CustomParser implements JsonDeserializer<List<SaveNode>>, JsonSerializer<List<SaveNode>>  {

	private static Map<nodeType, Class> map = new TreeMap<nodeType, Class>();

    static {
        map.put(nodeType.Node, SaveNode.class);
        map.put(nodeType.Text, SaveText.class);
        map.put(nodeType.Option, SaveOption.class);
    }

    @Override
	public List<SaveNode> deserialize(JsonElement json, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

    	System.out.println("deserialize");
        List<SaveNode> list = new ArrayList<SaveNode>();
        JsonArray jarray = json.getAsJsonArray();

        for (JsonElement jelement : jarray) {

            String type = jelement.getAsJsonObject().get("Type").getAsString();
            Class c = map.get(nodeType.valueOf(type));
            if (c == null)
                throw new RuntimeException("Unknow class: " + type);
            list.add(context.deserialize(jelement, c));
        }

        return list;
    }

	@Override
	public JsonElement serialize(List<SaveNode> src, Type typeOfSrc, JsonSerializationContext context) {
		System.out.println("serialize");
		 if (src == null)
	            return null;
	        else {
	            JsonArray jarray = new JsonArray();
	            for (SaveNode node : src) {
	                Class c = map.get(node.type);
	                if (c == null)
	                    throw new RuntimeException("Unknow class: " + node.type);
	                jarray.add(context.serialize(node, c));
	            }
	            return jarray;
	        }
	}
}