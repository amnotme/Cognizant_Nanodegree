package com.udacity.webcrawler.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * A static utility class that loads a JSON configuration file.
 */
public final class ConfigurationLoader {

  private final Path path;

  /**
   * Create a {@link ConfigurationLoader} that loads configuration from the given {@link Path}.
   */
  public ConfigurationLoader(Path path) {
    this.path = Objects.requireNonNull(path);
  }

  /**
   * Loads configuration from this {@link ConfigurationLoader}'s path
   *
   * @return the loaded {@link CrawlerConfiguration}.
   */
  public CrawlerConfiguration load() {
    CrawlerConfiguration config = null;
    try(BufferedReader file = Files.newBufferedReader(this.path)) {
      config = read(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return config;
  }

  /**
   * Loads crawler configuration from the given reader.
   *
   * @param reader a Reader pointing to a JSON string that contains crawler configuration.
   * @return a crawler configuration
   */
  public static CrawlerConfiguration read(Reader reader) {
    CrawlerConfiguration config = null;
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
    try {
      config = mapper.readValue(reader, CrawlerConfiguration.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return config;
  }
}
