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
 * WritePostRequest
 */
@lombok.Builder

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.4.0")
public class WritePostRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long authorId;

  private String title;

  private String text;

  public WritePostRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public WritePostRequest(Long authorId, String title, String text) {
    this.authorId = authorId;
    this.title = title;
    this.text = text;
  }

  public WritePostRequest authorId(Long authorId) {
    this.authorId = authorId;
    return this;
  }

  /**
   * Get authorId
   * @return authorId
  */
  @NotNull 
  @Schema(name = "author_id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("author_id")
  public Long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }

  public WritePostRequest title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  @NotNull 
  @Schema(name = "title", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public WritePostRequest text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
  */
  @NotNull 
  @Schema(name = "text", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("text")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WritePostRequest writePostRequest = (WritePostRequest) o;
    return Objects.equals(this.authorId, writePostRequest.authorId) &&
        Objects.equals(this.title, writePostRequest.title) &&
        Objects.equals(this.text, writePostRequest.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authorId, title, text);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WritePostRequest {\n");
    sb.append("    authorId: ").append(toIndentedString(authorId)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
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

