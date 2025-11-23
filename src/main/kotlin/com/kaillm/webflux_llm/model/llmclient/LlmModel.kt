package com.kaillm.webflux_llm.model.llmclient

import com.fasterxml.jackson.annotation.JsonValue

enum class LlmModel(
    private val code: String,
    val llmType: LlmType
) {
    GPT_4O("gpt-4o", LlmType.GPT),
    GPT_5_NANO("gpt-5-nano", LlmType.GPT),
    GEMINI_2_0_FLASH("gemini-2.0-flash", LlmType.GEMINI),
    ;

    @get:JsonValue
    val model: String
        get() = this.code
}