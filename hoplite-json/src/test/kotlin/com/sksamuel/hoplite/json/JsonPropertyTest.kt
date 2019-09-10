package com.sksamuel.hoplite.json

import com.fasterxml.jackson.annotation.JsonProperty
import com.sksamuel.hoplite.ConfigLoader
import io.kotlintest.assertions.arrow.validation.shouldBeValid
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class JsonPropertyTest : FunSpec({
  test("JsonProperty") {
    data class Test(
      @JsonProperty("finalize_recording_script_path")
      val finalizeRecordingScriptPath: String
    )
    ConfigLoader().loadConfigOrThrow<Test>("/json_property.json")
//    ConfigLoader().loadConfig<Test>("/json_property.json").shouldBeValid {
//      it.a shouldBe Test("one", false, "two")
//    }
  }
})
