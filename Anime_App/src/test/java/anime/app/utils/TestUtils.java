package anime.app.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TestUtils {
    public static byte[] getIcon(String path) throws IOException {
        try (InputStream stream = TestUtils.class.getClassLoader().getResourceAsStream(path)) {
            if (Objects.isNull(stream)) {
                throw new IOException("The default achievement icon stream is null");
            }

            return IOUtils.toByteArray(stream);
        } catch (Exception e) {
            throw new IOException("Could not find the default achievement icon", e);
        }
    }
}
