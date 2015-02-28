package com.symfony.form;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A value object compatible with the format the JMSSerializer returns when a form is invalid
 */
public class FormErrorModel {
    @SerializedName("children")
    private HashMap<String, FormErrorModel> children;
    @SerializedName("errors")
    private String[] errors;

    public FormErrorModel(HashMap<String, FormErrorModel> children, String[] errors) {
        this.children = children;
        this.errors = errors;
    }

    public HashMap<String, FormErrorModel> getChildren() {
        return children;
    }

    public void setChildren(HashMap<String, FormErrorModel> children) {
        this.children = children;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

    public static class FormErrorModelDeserializer implements JsonDeserializer<FormErrorModel> {
        @Override
        public FormErrorModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            Gson gson = new Gson();

            String[] errors = jsonObject.has("errors")
                    ? gson.fromJson(jsonObject.getAsJsonArray("errors"), String[].class)
                    : new String[0];

            HashMap<String, FormErrorModel> children = new HashMap<String, FormErrorModel>();
            if (jsonObject.has("children")) {
                Set<Map.Entry<String, JsonElement>> s = jsonObject.getAsJsonObject("children").entrySet();

                for (Map.Entry<String, JsonElement> i : s) {
                    if (i.getValue() instanceof JsonObject) {
                        children.put(i.getKey(), gson.fromJson(i.getValue(), FormErrorModel.class));
                    }
                }
            }

            return new FormErrorModel(children, errors);
        }
    }
}
