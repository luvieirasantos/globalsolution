package com.fiap.globalsolution.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fiap.globalsolution.model.Empresa;

import java.io.IOException;

public class EmpresaDeserializer extends JsonDeserializer<Empresa> {

    @Override
    public Empresa deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        Empresa empresa = new Empresa();

        if (node.isTextual()) {
            // Handle case where empresa is sent as a string (e.g., "string" or an ID)
            String value = node.asText();
            if (!"string".equals(value)) {
                try {
                    empresa.setId(Long.parseLong(value));
                } catch (NumberFormatException e) {
                    // If it's not a number, just ignore and return empty empresa
                }
            }
        } else if (node.isObject()) {
            // Handle case where empresa is sent as a proper JSON object
            if (node.has("id")) {
                JsonNode idNode = node.get("id");
                if (!idNode.isNull()) {
                    empresa.setId(idNode.asLong());
                }
            }
            if (node.has("cnpj")) {
                empresa.setCnpj(node.get("cnpj").asText());
            }
            if (node.has("razaoSocial")) {
                empresa.setRazaoSocial(node.get("razaoSocial").asText());
            }
            if (node.has("nomeFantasia")) {
                empresa.setNomeFantasia(node.get("nomeFantasia").asText());
            }
            if (node.has("emailCorporativo")) {
                empresa.setEmailCorporativo(node.get("emailCorporativo").asText());
            }
            if (node.has("telefone")) {
                empresa.setTelefone(node.get("telefone").asText());
            }
            if (node.has("status")) {
                empresa.setStatus(node.get("status").asText());
            }
        }

        return empresa;
    }
}