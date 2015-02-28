package com.symfony.form;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidationErrorModelTest {
    @Test
    public void deserialize() {
        String json =
                "{" +
                    "\"code\":400," +
                    "\"message\":\"Validation Failed\"," +
                    "\"errors\":{" +
                        "\"children\":{" +
                            "\"email\":{" +
                                "\"errors\":[\"This value is already used.\"]" +
                            "}," +
                            "\"plainPassword\":[]" +
                        "}" +
                    "}" +
                "}";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(FormErrorModel.class, new FormErrorModel.FormErrorModelDeserializer());

        Gson gson = gsonBuilder.create();

        ValidationErrorModel validationErrors = gson.fromJson(json, ValidationErrorModel.class);

        assertEquals((Integer) 400, validationErrors.getCode());
        assertEquals("Validation Failed", validationErrors.getMessage());

        FormErrorModel formError = validationErrors.getErrors();

        assertEquals(1, formError.getChildren().size());

        FormErrorModel emailError = formError.getChildren().get("email");

        assertEquals("This value is already used.", emailError.getErrors()[0]);
    }

    @Test
    public void deserializeNested() {
        String json =
                "{" +
                    "\"code\":400," +
                    "\"message\":\"Validation Failed\"," +
                    "\"errors\":{" +
                        "\"children\":{" +
                            "\"email\":{" +
                                "\"errors\":[\"This value is already used.\"]," +
                                "\"children\":{" +
                                    "\"something\":{" +
                                        "\"errors\":[\"Something fishy.\"]" +
                                    "}" +
                                "}" +
                            "}," +
                    "\"plainPassword\":[]" +
                        "}" +
                    "}" +
                "}";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(FormErrorModel.class, new FormErrorModel.FormErrorModelDeserializer());

        Gson gson = gsonBuilder.create();

        ValidationErrorModel validationErrors = gson.fromJson(json, ValidationErrorModel.class);
        FormErrorModel formError = validationErrors.getErrors();
        FormErrorModel emailError = formError.getChildren().get("email");
        FormErrorModel somethingError = emailError.getChildren().get("something");

        assertEquals("Something fishy.", somethingError.getErrors()[0]);
    }
}
