package io.fares.junit.mongodb;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class MongoForAllExtension extends AbstractMongoExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

  private static boolean started = false;

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    if (!started) {
      started = true;
      startMongoWhenEnabled(context);
      context.getRoot().getStore(GLOBAL).put(MongoForAllExtension.class.getName(), this);
    }
  }

  @Override
  public void close() throws Throwable {
    shutdownMongo();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static MongoForAllExtension defaultMongo() {
    return new Builder().build();
  }

  public static final class Builder extends AbstractMongoExtensionBuilder<Builder, MongoForAllExtension> {

    private Builder() {
      super(new MongoForAllExtension());
    }

  }

}
