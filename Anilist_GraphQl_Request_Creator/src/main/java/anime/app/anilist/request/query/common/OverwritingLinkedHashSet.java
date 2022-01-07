package anime.app.anilist.request.query.common;

import lombok.NonNull;

import java.util.Collection;
import java.util.LinkedHashSet;

public class OverwritingLinkedHashSet<E> extends LinkedHashSet<E> {
	@Override
	public boolean add(E e) {
		remove(e);
		return super.add(e);
	}

	@Override
	public boolean addAll(@NonNull Collection<? extends E> c) {
		removeAll(c);
		return super.addAll(c);
	}
}
