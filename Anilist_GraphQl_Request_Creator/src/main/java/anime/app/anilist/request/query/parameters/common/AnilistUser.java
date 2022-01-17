package anime.app.anilist.request.query.parameters.common;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import anime.app.anilist.request.query.parameters.QueryParameterUtils;
import lombok.Getter;

import java.util.Set;

@Getter
public class AnilistUser {
	public static final String USER_TITLE = "user";

	private final String anilistUserString;

	private AnilistUser(String anilistUserString) {
		this.anilistUserString = anilistUserString;
	}

	public String getAnilistUserWithoutFieldName() {
		return this.anilistUserString.substring(USER_TITLE.length() + 1);
	}

	public static AnilistUserBuilder getUserBuilder() {
		return new AnilistUserBuilder();
	}

	@Override
	public String toString() {
		return anilistUserString;
	}

	public static final class AnilistUserBuilder {
		private final Set<ParameterString> user = new OverwritingLinkedHashSet<>();

		public AnilistUserBuilder id() {
			user.add(ParameterString.fromString("id"));
			return this;
		}

		public AnilistUserBuilder name() {
			user.add(ParameterString.fromString("name"));
			return this;
		}

		public AnilistUserBuilder avatar() {
			user.add(ParameterString.fromString(QueryParameterUtils.buildFieldElement("avatar", "large", "medium")));
			return this;
		}

		public AnilistUser build() {
			if (user.isEmpty()) {
				throw new IllegalStateException("User should posses at least 1 parameter!");
			}

			return new AnilistUser(QueryParameterUtils.buildFieldElement(
					USER_TITLE,
					user
			));
		}
	}
}
