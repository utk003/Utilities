package io.github.utk003.util.data.collection.multi.map.unique;

import io.github.utk003.util.data.collection.multi.map.AbstractMultiMap;
import io.github.utk003.util.misc.annotations.ScheduledForRelease;
import io.github.utk003.util.misc.annotations.RequiresDocumentation;

@ScheduledForRelease(inVersion = "v2.2.0")
@RequiresDocumentation
public abstract class AbstractUniqueMappingMultiMap<K, V> extends AbstractMultiMap<K, V> implements UniqueMappingMultiMap<K, V> {
}
