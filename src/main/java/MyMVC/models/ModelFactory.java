package MyMVC.models;

import MyMVC.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelFactory {
    public static <T> ArrayList<T> load(String className, int fieldCount, Deserializer<T> deserializer) {
        String dir = "db";
        String fileName = String.format("%s/%s.txt", dir, className);
        String data = Utility.load(fileName);
        if (data.strip().length() == 0) {
            return new ArrayList<>();
        } else {
            String[] modelsDataRaw = data.split("\n");
            List<String> modelsData = Arrays.asList(modelsDataRaw);

            ArrayList<T> models = new ArrayList<>();
            for (int i = 0; i < modelsData.size(); i = i + fieldCount) {
                List<String> modelData = modelsData.subList(i, i + fieldCount);
                Utility.log("序列化前的数据 <%s>", modelsData);
                T model = deserializer.deserialize(modelData);
                Utility.log("序列化后的数据 <%s>", model);
                models.add(model);
            }
            return models;
        }
    }

    public static <T> void save(String className, ArrayList<T> list, Serializer<T> serializer) {
        StringBuilder content = new StringBuilder();
        for (T m: list) {
            ArrayList<String> lines = serializer.serialize(m);
             for (String line: lines) {
                 content.append(line);
                 content.append("\n");
             }
        }
        String dir = "db";
        String fileName = String.format("%s/%s.txt", dir, className);
        Utility.save(fileName, content.toString());
    }

    public static <T>  T findBy(ArrayList<T> modelList, Equality<T> equality) {
        for (T model: modelList) {
            boolean valid = equality.equal(model);
            if (valid) {
                return model;
            }
        }
        return null;
    }
}
