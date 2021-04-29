package caccia.david.inthewind.api;

import java.util.List;

public interface Winder<T,V>
{
    List<T> unwind(V v, int count);

    V wind(List<T> t);
}
