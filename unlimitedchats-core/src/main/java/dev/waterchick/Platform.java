package dev.waterchick;

import java.io.File;
import java.util.logging.Logger;

public interface Platform {
    String getPlatformName();
    String getServerVersion();
    File getFolder();
    Logger getLogger();
}
