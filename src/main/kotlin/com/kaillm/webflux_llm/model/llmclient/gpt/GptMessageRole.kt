package com.kaillm.webflux_llm.model.llmclient.gpt

import com.fasterxml.jackson.annotation.JsonValue

enum class GptMessageRole {
    SYSTEM,
    USER,
    ASSISTANT,
    ;

    @JsonValue
    override fun toString(): String {
        return name.lowercase()
    }
}