package anime.app.integrationTests.config.wiremock;

import anime.app.configuration.beans.ObjectMapperConfiguration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;

import java.util.Objects;

/**
 * The request need to be:
 * <pre>
 *     wireMockServer
 *          .stubFor(post(WireMock.urlEqualTo(anilistWireMockURL))
 *              .willReturn(ResponseDefinitionBuilder
 *              .okForJson(responseBody)
 *              .withTransformers(WireMockPageExtension.wireMockPageExtensionName)
 *          )
 *     );
 * </pre>
 */
public class WireMockPageExtension extends ResponseTransformer {
	public static final String wireMockPageExtensionName = "wireMockPageExtension";

	private static WireMockPageExtension instance;

	private WireMockPageExtension() { }

	public static WireMockPageExtension getInstance() {
		if (Objects.isNull(instance)) {
			synchronized (WireMockPageExtension.class) {
				if (Objects.isNull(instance)) {
					instance = new WireMockPageExtension();
				}
			}
		}

		return instance;
	}

	@Override
	public boolean applyGlobally() { return false; }

	@Override
	public String getName() {
		return wireMockPageExtensionName;
	}

	@Override
	public Response transform(Request request, Response response, FileSource files, Parameters parameters) {
		return transformationMethod(request, response, files, parameters);
	}

	public static Response transformationMethod(Request request, Response response, FileSource files, Parameters parameters) {
		try {
			ObjectMapper mapper = ObjectMapperConfiguration.ObjectMapperFactory.getNewObjectMapper();

			JsonNode requestBody = mapper.readTree(request.getBody());

			if (!requestBody.has("query") || requestBody.get("query").asText().isBlank()) {
				throw new AssertionError("Request body does not have a 'query' element");
			}

			if (!requestBody.has("variables") || !requestBody.get("variables").isContainerNode()) {
				throw new AssertionError("Request body does not have a 'variables' element");
			}

			if (!requestBody.get("query").asText().contains("Page")) {
				throw new AssertionError("Request body does not have a 'Page' element");
			}

			return response;
		} catch (Exception e) {
			throw new AssertionError(e.getMessage(), e);
		}
	}
}
