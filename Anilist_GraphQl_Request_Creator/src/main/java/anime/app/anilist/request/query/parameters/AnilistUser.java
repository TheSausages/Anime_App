package anime.app.anilist.request.query.parameters;

import anime.app.anilist.request.query.common.OverwritingLinkedHashSet;
import anime.app.anilist.request.query.common.ParameterString;
import lombok.Getter;

import java.util.Set;

@Getter
public class AnilistUser {
	public static final String userTitle = "user";

	private final String anilistUserString;

	private AnilistUser(String anilistUserString) {
		this.anilistUserString = anilistUserString;
	}

	public String getAnilistUserWithoutFieldName() {
		return this.anilistUserString.substring(userTitle.length() + 1);
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
			user.add(ParameterString.fromString("avatar {\nlarge\nmedium\n}"));
			return this;
		}

		public AnilistUser build() {
			if (user.isEmpty()) {
				throw new IllegalStateException("User should posses at least 1 parameter!");
			}

			return new AnilistUser(QueryParameterUtils.buildQueryFieldElementString(
					userTitle,
					user
			));
		}
	}
}
