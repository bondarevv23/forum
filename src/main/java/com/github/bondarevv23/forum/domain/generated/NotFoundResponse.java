package com.github.bondarevv23.forum.domain.generated;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * NotFoundResponse
 */
@lombok.Builder

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.4.0")
public class NotFoundResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long passedId;

  private String errorMessage;

  public NotFoundResponse passedId(Long passedId) {
    this.passedId = passedId;
    return this;
  }

  /**
   * Get passedId
   * @return passedId
  */
  
  @Schema(name = "passed_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("passed_id")
  public Long getPassedId() {
    return passedId;
  }

  public void setPassedId(Long passedId) {
    this.passedId = passedId;
  }

  public NotFoundResponse errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  /**
   * Get errorMessage
   * @return errorMessage
  */
  
  @Schema(name = "error_message", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("error_message")
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotFoundResponse notFoundResponse = (NotFoundResponse) o;
    return Objects.equals(this.passedId, notFoundResponse.passedId) &&
        Objects.equals(this.errorMessage, notFoundResponse.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(passedId, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotFoundResponse {\n");
    sb.append("    passedId: ").append(toIndentedString(passedId)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

