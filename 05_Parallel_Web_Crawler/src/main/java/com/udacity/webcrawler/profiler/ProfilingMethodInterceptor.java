package com.udacity.webcrawler.profiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A method interceptor that checks whether {@link Method}s are annotated with the {@link Profiled}
 * annotation. If they are, the method interceptor records how long the method invocation took.
 */
final class ProfilingMethodInterceptor implements InvocationHandler {

  private final ZonedDateTime startTime;
  private final ProfilingState state;
  private final Object delegate;
  private final Clock clock;

  // TODO: You will need to add more instance fields and constructor arguments to this class.
  ProfilingMethodInterceptor(
    ZonedDateTime startTime,
    ProfilingState state,
    Object delegate,
    Clock clock
  ) {
    this.startTime = startTime;
    this.state = state;
    this.delegate = delegate;
    this.clock = Objects.requireNonNull(clock);
  }

  @Override
  public Object invoke(
    Object proxy,
    Method method,
    Object[] args
  ) throws Throwable {

    Instant time = clock.instant();
    try {
      return method.invoke(delegate, args);
    } catch (InvocationTargetException e) {
      throw e.getTargetException();
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } finally {
      if (!Objects.equals(method.getAnnotation(Profiled.class), null))
        this.state.record(delegate.getClass(), method, Duration.between(time, clock.instant()));
    }
  }
}
