package com.udacity.webcrawler.profiler;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

/**
 * Concrete implementation of the {@link Profiler}.
 */
final class ProfilerImpl implements Profiler {

  private final Clock clock;
  private final ProfilingState state = new ProfilingState();
  private final ZonedDateTime startTime;

  @Inject
  ProfilerImpl(Clock clock) {
    this.clock = Objects.requireNonNull(clock);
    this.startTime = ZonedDateTime.now(clock);
  }

  @Override
  public <T> T wrap(Class<T> klass, T delegate) {
    Objects.requireNonNull(klass);
    ArrayList<Method> methods = new ArrayList<>(Arrays.asList(klass.getDeclaredMethods()));

    if (methods.stream().noneMatch(ProfilerImpl::isProfileClass))
      throw new IllegalArgumentException("The wrapped interface " + klass + " does not contain a @Profiled method.");

    ProfilingMethodInterceptor interceptor = new ProfilingMethodInterceptor(startTime, state, delegate, clock);

    Object proxy = Proxy.newProxyInstance(
      ProfilerImpl.class.getClassLoader(),
      new Class[]{klass},
      interceptor
    );

    return (T) proxy;
  }

  private static boolean isProfileClass(Method method) {
    return method.getAnnotation(Profiled.class) != null;
  }

  @Override
  public void writeData(Path path) {
    try(Writer writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
      writeData(writer);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public void writeData(Writer writer) throws IOException {
    writer.write("Run at " + RFC_1123_DATE_TIME.format(startTime));
    writer.write(System.lineSeparator());
    state.write(writer);
    writer.write(System.lineSeparator());
  }
}
