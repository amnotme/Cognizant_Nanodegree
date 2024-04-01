package com.udacity.webcrawler;

import com.udacity.webcrawler.json.CrawlResult;
import com.udacity.webcrawler.parser.PageParserFactory;

import javax.inject.Inject;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Pattern;

/**
 * A concrete implementation of {@link WebCrawler} that runs multiple threads on a
 * {@link ForkJoinPool} to fetch and process multiple web pages in parallel.
 */
final class ParallelWebCrawler implements WebCrawler {
  private final Clock clock;
  private final Duration timeout;
  private final int popularWordCount;
  private final ForkJoinPool pool;
  private final PageParserFactory parserFactory;
  private final int maxDepth;
  private final List<Pattern> ignoredUrlList;


  @Inject
  ParallelWebCrawler(
          Clock clock,
          @Timeout Duration timeout,
          @PopularWordCount int popularWordCount,
          @TargetParallelism int threadCount,
          PageParserFactory parserFactory,
          @IgnoredUrls List<Pattern> ignoredUrlList,
          @MaxDepth int maxDepth
  ) {
    this.clock = clock;
    this.timeout = timeout;
    this.popularWordCount = popularWordCount;
    this.pool = new ForkJoinPool(Math.min(threadCount, getMaxParallelism()));
    this.parserFactory = parserFactory;
    this.maxDepth = maxDepth;
    this.ignoredUrlList = ignoredUrlList;
  }

  @Override
  public CrawlResult crawl(List<String> startingUrls) {
    Instant deadline                          = clock.instant().plus(timeout);
    ConcurrentHashMap<String, Integer> counts = new ConcurrentHashMap<>();
    ConcurrentSkipListSet<String> visitedUrls = new ConcurrentSkipListSet<>();
    CrawlResult result = null;

    startingUrls.forEach(url -> pool.invoke(
            new RecursiveCrawler(url,deadline, counts, visitedUrls, maxDepth)
    ));

    return counts.isEmpty()
      ? buildResultWhenCountsAreEmpty(counts, visitedUrls)
      : buildResult(counts, popularWordCount, visitedUrls);
  }
  private static CrawlResult buildResult(ConcurrentHashMap<String, Integer> counts, int popularWordCount, ConcurrentSkipListSet<String> visitedUrls) {
    return new CrawlResult.Builder()
      .setWordCounts(WordCounts.sort(counts, popularWordCount))
      .setUrlsVisited(visitedUrls.size())
      .build();
  }

  private static CrawlResult buildResultWhenCountsAreEmpty(ConcurrentHashMap<String, Integer> counts, ConcurrentSkipListSet<String> visitedUrls) {
    return new CrawlResult.Builder()
      .setWordCounts(counts)
      .setUrlsVisited(visitedUrls.size())
      .build();
  }

  private class RecursiveCrawler extends RecursiveAction {

    private String url;
    private Instant deadline;
    private ConcurrentHashMap<String, Integer> urlCounts;
    private ConcurrentSkipListSet<String> visitedUrls;
    private int maxDepth;
    private RecursiveCrawler(
            String url,
            Instant deadline,
            ConcurrentHashMap<String, Integer> urlCounts,
            ConcurrentSkipListSet<String> visitedUrls,
            int maxDepth
    ) {
      this.url = url;
      this.deadline = deadline;
      this.urlCounts = urlCounts;
      this.visitedUrls = visitedUrls;
      this.maxDepth = maxDepth;
    }
    @Override
    protected void compute() {

      if (isMaxDepth(maxDepth) || isVisited(this.visitedUrls, this.url) || isIgnored(ignoredUrlList))
        return;

      ArrayList<RecursiveCrawler> actions = new ArrayList<>();

      this.visitedUrls.add(this.url);

      computeWordCount();
      computeLinks(actions);
      invokeAll(actions);
    }

    private void computeLinks(ArrayList<RecursiveCrawler> actions) {
      parserFactory
        .get(url)
        .parse()
        .getLinks()
        .forEach(
          link -> actions.add(
            new RecursiveCrawler(link, deadline, urlCounts, visitedUrls, maxDepth - 1)
          )
        );
    }

    private void computeWordCount() {
      parserFactory
        .get(url)
        .parse()
        .getWordCounts()
        .forEach(
          (wordKey, wordValue) -> urlCounts.compute(wordKey,
            (__, urlValue) -> Objects.equals(urlValue, null) ? wordValue : wordValue + urlValue)
      );
    }

    private Boolean isIgnored(List<Pattern> ignoredUrlList) {
      return ignoredUrlList.stream().anyMatch(pattern -> pattern.matcher(this.url).matches());
    }

    private Boolean isVisited(ConcurrentSkipListSet<String> visitedUrls, String url) {
      return visitedUrls.contains(url);
    }

    private Boolean isMaxDepth(int maxDepth) {
      return (maxDepth == 0 || clock.instant().isAfter(this.deadline));
    }
  }

  @Override
  public int getMaxParallelism() {
    return Runtime.getRuntime().availableProcessors();
  }
}
