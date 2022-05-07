import io.javalin.http.sse.SseClient;
import io.methvin.watcher.DirectoryChangeEvent;
import io.methvin.watcher.DirectoryWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Dev {

    private static final Logger log = LoggerFactory.getLogger(Dev.class);

    private static DirectoryWatcher watcher;
    private static SseClient        sseClient;

    // This class is used for dev mode, upon change of .jte or .js file a refresh event is sent to browser. If .java sleep for a sec before sending the event to give time for hot recompilation
    public Dev() {
        try {
            watcher = DirectoryWatcher.builder()
                    .paths(List.of(Path.of("src", "main"), Path.of("target")))
                    .listener(event -> {
                        if (event.eventType() == DirectoryChangeEvent.EventType.MODIFY || event.eventType() == DirectoryChangeEvent.EventType.CREATE) {

                            var file = event.path().toString();

                            if ((file.endsWith(".jte") || file.endsWith(".js")) && sseClient != null) {
                                log.info(event.path().toString() + " changed refreshing");
                                sseClient.sendEvent("refresh", "Template changed, refresh browser event");
                            }

                            if (file.endsWith(".java")) {
                                try {
                                    Thread.sleep(1200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                sseClient.sendEvent("refresh", "Recompile with java hot reload");
                            }

                        }
                    })
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        watcher.watchAsync();
    }

    public void devMode(SseClient client) {
        sseClient = client;
    }

}
