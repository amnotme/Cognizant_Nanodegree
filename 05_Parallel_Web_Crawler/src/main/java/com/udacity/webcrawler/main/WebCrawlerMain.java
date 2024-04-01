package com.udacity.webcrawler.main;

import com.google.inject.Guice;
import com.udacity.webcrawler.WebCrawler;
import com.udacity.webcrawler.WebCrawlerModule;
import com.udacity.webcrawler.json.ConfigurationLoader;
import com.udacity.webcrawler.json.CrawlResult;
import com.udacity.webcrawler.json.CrawlResultWriter;
import com.udacity.webcrawler.json.CrawlerConfiguration;
import com.udacity.webcrawler.profiler.Profiler;
import com.udacity.webcrawler.profiler.ProfilerModule;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Path;
import java.util.Objects;

import static java.lang.System.*;

public final class WebCrawlerMain {

  private final CrawlerConfiguration config;

  private WebCrawlerMain(CrawlerConfiguration config) {
    this.config = Objects.requireNonNull(config);
  }

  @Inject
  private WebCrawler crawler;

  @Inject
  private Profiler profiler;

  private void run() throws Exception {
    Guice.createInjector(new WebCrawlerModule(config), new ProfilerModule()).injectMembers(this);

    CrawlResult result = crawler.crawl(config.getStartPages());
    CrawlResultWriter resultWriter = new CrawlResultWriter(result);

    writeCrawlResultsData(resultWriter);
    writeProfilerData();
  }

  private void writeProfilerData() throws IOException {
    if (config.getProfileOutputPath().isEmpty())
      try (Writer writer = new OutputStreamWriter(out)) {
        profiler.writeData(writer);
      }
    else
      profiler.writeData(Path.of(config.getProfileOutputPath()));
  }

  private void writeCrawlResultsData(CrawlResultWriter resultWriter) throws IOException {
    if (config.getResultPath().isEmpty())
      try (Writer writer = new OutputStreamWriter(out)) {
        resultWriter.write(writer);
      }
    else
      resultWriter.write(Path.of(config.getResultPath()));
  }


  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      out.println("Usage: WebCrawlerMain [starting-url]");
      return;
    }

    CrawlerConfiguration config = new ConfigurationLoader(Path.of(args[0])).load();
    new WebCrawlerMain(config).run();
  }
}
