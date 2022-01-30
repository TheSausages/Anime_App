package anime.app.configuration.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.info.ServerInfoRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class KeycloakHealthIndicator implements HealthIndicator {
	private final Keycloak keycloak;
	private final ObjectMapper mapper;

	@Autowired
	KeycloakHealthIndicator(Keycloak keycloak, ObjectMapper mapper) {
		this.keycloak = keycloak;
		this.mapper = mapper;
	}

	@Override
	public Health health() {
		try {
			ServerInfoRepresentation serverInfo = keycloak.serverInfo().getInfo();

			ObjectNode memoryNode = mapper.createObjectNode();
			memoryNode.put("total", serverInfo.getMemoryInfo().getTotalFormated());
			memoryNode.put("used", serverInfo.getMemoryInfo().getUsedFormated());
			memoryNode.put("free", serverInfo.getMemoryInfo().getFreeFormated());
			memoryNode.put("free(percents)", serverInfo.getMemoryInfo().getFreePercentage());

			return Health.up()
					.withDetail("uptime", serverInfo.getSystemInfo().getUptime())
					.withDetail("memory", memoryNode)
					.build();
		} catch (Exception e) {
			return Health.down()
					.withDetail("uptime", "No Data")
					.withDetail("memory", "No Data")
					.build();
		}
	}
}
